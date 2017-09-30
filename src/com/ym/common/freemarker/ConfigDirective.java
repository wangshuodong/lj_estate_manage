package com.ym.common.freemarker;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ym.iadpush.manage.entity.SysConfig;
import com.ym.iadpush.manage.services.common.IConfigMgr;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ConfigDirective implements TemplateDirectiveModel {

    private static Log log = LogFactory.getLog(ConfigDirective.class);
    private @Autowired IConfigMgr configMgr;

    @Override
    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Object typeObject = params.get("type");
        Object keyObject = params.get("key");
        String type = typeObject == null ? null : typeObject.toString();
        String key = keyObject == null ? null : keyObject.toString();
        try {
            if ("options".equals(type.toLowerCase())) {
                List<SysConfig> cfgs = configMgr.getById(key);
                for (SysConfig cfg : cfgs) {
                    env.getOut().write("<option value=\"" + cfg.getCfgKey() + "\">" + cfg.getCfgVal() + "</option>");
                }
            } else if ("config".equals(type.toLowerCase())) {

            }
        } catch (Exception e) {
            env.getOut().write("");
            log.error("Freemarker翻译异常！");
            e.printStackTrace();
        }
    }

}
