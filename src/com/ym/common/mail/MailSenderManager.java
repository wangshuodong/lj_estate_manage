package com.ym.common.mail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.ym.common.utils.MD5Encrypt;

import freemarker.template.Template;

/* @Component */
public class MailSenderManager {

    private Log log = LogFactory.getLog(getClass());
    private @Autowired TaskExecutor pool;
    private @Autowired JavaMailSender mailSender;
    private @Autowired FreeMarkerConfigurer configurer;
    private @Value("${mail.from}") String FROM;
    private @Value("${mail.nick}") String NICK;
    private @Value("${mail.image.path}") String MAIL_IMAGE_PATH;
    private @Value("${mail.template.path}") String EMAIL_TEMPLATE_PATH;
    private String nick;
    private String form;

    /**
     * 设置发件人昵称
     * 
     * @param nick
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick == null ? NICK : nick;
    }

    /**
     * 设置发件人邮箱
     * 
     * @param form
     */
    public void setForm(String form) {
        this.form = form;
    }

    /**
     * 发送电子邮件(HTML)
     * 
     * @param templateFile freemarker模版文件
     * @param model 替换模板中字段的值
     * @param subject 邮件主题
     * @param mailTo 接收方的email地址
     * @param files 附件
     */
    @Deprecated
    public void sendMail(final String templateFile, final Object model, final String subject, final String[] mailTo,
            final String[] files) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                // 这是一个生成Mime邮件简单工具，如果不使用GBK这个，中文会出现乱码
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "GBK");
                // 设置接收方的email地址
                message.setTo(mailTo);
                // 设置抄送地址
                // message.setCc(mailTo);
                // 设置暗抄送地址
                // message.setBcc(mailTo);
                // 设置邮件主题
                message.setSubject(subject);
                // 设置发送方地址
                message.setFrom(form == null ? FROM : form, nick == null ? NICK : nick);
                Template template = configurer.getConfiguration().getTemplate(
                        EMAIL_TEMPLATE_PATH + File.separator + templateFile);
                String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                message.setText(text, true);
                FileSystemResource file;
                for (String f : files) {
                    // 读取附件
                    file = new FileSystemResource(new File(f));
                    // 向email中添加附件
                    message.addAttachment(f, file);
                }
            }
        };
        sendEmail(preparator);// 发送邮件
    }

    /**
     * 发送纯文本邮件
     * 
     * @param subject 邮件主题
     * @param mailTo 接收方email地址
     * @param content 邮件内容
     */
    public void sendTextMail(String subject, String[] mailTo, String content) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(mailTo);
        mail.setFrom(form == null ? FROM : form);
        mail.setSubject(subject);
        mail.setText(content);
        sendEmail(mail);
    }

    /**
     * 发送HTML邮件
     * 
     * @param subject 邮件主题
     * @param mailTo 接收方email地址
     * @param html 邮件内容
     * @param files 附件
     */
    public void sendHtmlMail(final String subject, final String[] mailTo, final String html,
            final Map<String, File> files) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "GBK");
                message.setTo(mailTo);
                message.setSubject(subject);
                message.setFrom(form == null ? FROM : form, nick == null ? NICK : nick);
                message.setText(html, true);
                if (files != null) {
                    for (String key : files.keySet()) {
                        message.addAttachment(MimeUtility.encodeWord(key), files.get(key));
                    }
                }
            }
        };
        sendEmail(preparator);
    }

    /**
     * 根据模版发送邮件
     * 
     * @param template 模版文件名称
     * @param model 替换内容
     * @param subject 邮件主题
     * @param mailTo 接收方Email地址
     * @param files 附件
     */
    public void sendTemplateMail(final String template, final Object model, final String subject,
            final String[] mailTo, final Map<String, File> files) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "GBK");
                message.setTo(mailTo);
                message.setSubject(subject);
                message.setFrom(form == null ? FROM : form, nick == null ? NICK : nick);
                message.setSentDate(new Date());
                Template temp = configurer.getConfiguration().getTemplate(
                        EMAIL_TEMPLATE_PATH + File.separator + template);
                String text = FreeMarkerTemplateUtils.processTemplateIntoString(temp, model);
                message.setText(text, true);
                // 邮件内容处理
                // emailHandler(message, text);
                if (files != null) {
                    for (String key : files.keySet()) {
                        message.addAttachment(MimeUtility.encodeWord(key), files.get(key));
                    }
                }
            }
        };
        sendEmail(preparator);
    }

    /**
     * 发送邮件比较耗时，因此将发送步骤加入线程池
     * 
     * @param mail
     */
    private <T> void sendEmail(final T mail) {
        pool.execute(new Runnable() {
            public void run() {
                if (mail instanceof MimeMessage) {
                    mailSender.send((MimeMessage) mail);
                } else if (mail instanceof MimeMessagePreparator) {
                    mailSender.send((MimeMessagePreparator) mail);
                } else if (mail instanceof SimpleMailMessage) {
                    mailSender.send((SimpleMailMessage) mail);
                } else if (mail instanceof MimeMessage[]) {
                    mailSender.send((MimeMessage[]) mail);
                } else if (mail instanceof MimeMessagePreparator[]) {
                    mailSender.send((MimeMessagePreparator[]) mail);
                } else if (mail instanceof SimpleMailMessage[]) {
                    mailSender.send((SimpleMailMessage[]) mail);
                }
            }
        });
    }


    public void emailHandler(MimeMessageHelper message, String text) {
        try {
            List<File> files = new ArrayList<File>();
            Document doc = Jsoup.parse(text);
            Elements imgs = doc.getElementsByTag("img");
            for (Element img : imgs) {
                String src = img.attr("src");
                if (src != null && src.trim().length() > 0) {
                    File file = getImage(src);
                    if (file != null) {
                        img.attr("src", "cid:" + file.getName());
                        files.add(file);
                    }
                }
            }
            message.setText(doc.toString(), true);
            for (File file : files) {
                message.addInline(file.getName(), file);
            }
        } catch (Exception e) {
            log.error("向邮件中添加图片内容异常！", e);
        }
    }


    private File getImage(String src) {
        File file = null;
        try {
            // 获取图片名称
            int index = src.lastIndexOf('/');
            String fileName = src.substring(index);
            index = fileName.lastIndexOf('.');
            String suffix = fileName.substring(index);
            String newName = MD5Encrypt.MD5(src) + suffix;
            // 当邮件图片临时目录不存在时，自动创建
            if (!(file = new File(MAIL_IMAGE_PATH)).exists()) {
                file.mkdir();
            }
            file = new File(MAIL_IMAGE_PATH + File.separatorChar + newName);
            if (!file.exists()) {
                // 下载图片
                URL url = new URL(src);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);

                // 通过输入流获取图片数据
                InputStream inStream = conn.getInputStream();

                // 得到图片的二进制数据
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                inStream.close();

                // 将图片写入到磁盘
                FileOutputStream fops = new FileOutputStream(file);
                fops.write(outStream.toByteArray());
                fops.flush();
                fops.close();
            }
        } catch (Exception e) {
            log.error("邮件内容包含的图片失败！", e);
        }
        return file;
    }
}
