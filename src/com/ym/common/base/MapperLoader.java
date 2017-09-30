package com.ym.common.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;

/**
 * spring配置文件自动加载
 * 
 * @author 吕兵峰 2014-2-19 上午11:56:47
 * @version V1.0
 * @modificationHistory 如有逻辑或功能性重大变更记录，请修改一下以下内容
 * @modify by user: {修改人} 2014-2-19
 * @modify by reason:{方法名}:{原因}
 */

/*
 * @Component
 * 
 * @Lazy(false)
 */
public class MapperLoader implements DisposableBean, InitializingBean, ApplicationContextAware {
    private Log logger = LogFactory.getLog(MapperLoader.class);
    private ConfigurableApplicationContext context = null;
    private HashMap<String, String> fileMapping = new HashMap<String, String>();
    private Scanner scanner = null;
    private ScheduledExecutorService service = null;
    private Resource[] resources = null;
    private File[] mapperFiles;
    private Collection<Class<?>> mappers;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.context = (ConfigurableApplicationContext) applicationContext;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            service = Executors.newScheduledThreadPool(1);
            // 获取xml
            SqlSessionFactoryBean sessionFactoryBean = context
                    .getBean(SqlSessionFactoryBean.class);
            Field field = sessionFactoryBean.getClass().getDeclaredField(
                    "mapperLocations");
            field.setAccessible(true);
            resources = (Resource[]) field.get(sessionFactoryBean);
            // 获取mapper的class
            SqlSessionFactory factory = context
                    .getBean(SqlSessionFactory.class);
            Configuration configuration = factory.getConfiguration();
            mappers = configuration.getMapperRegistry().getMappers();
            mapperFiles = new File[mappers.size()];
            Iterator<Class<?>> iterator = mappers.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                Class<?> class1 = (Class<?>) iterator.next();
                URL url = new URL(class1.getResource("")
                        + class1.getSimpleName() + ".class");
                mapperFiles[i++] = new File(url.getFile());
            }
            // 触发文件监听事件
            scanner = new Scanner();
            scanner.scan();
            service.scheduleAtFixedRate(new Task(), 5, 5, TimeUnit.SECONDS);

        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    class Task implements Runnable {
        @Override
        public void run() {
            try {
                if (scanner.isChanged()) {
                    logger.debug("文件改变,重新加载.");
                    System.out.println("");
                    scanner.reload();
                    logger.debug("加载完毕.");
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

    }

    @SuppressWarnings({ "rawtypes" })
    class Scanner {
        public void reload() throws Exception {
            SqlSessionFactory factory = context
                    .getBean(SqlSessionFactory.class);

            Configuration configuration = factory.getConfiguration();
            // 移除加载项
            removeConfig(configuration);
            // 重新扫描加载xml
            if (resources != null) {
                for (int i = 0; i < resources.length; i++) {
                    if (resources[i] == null) {
                        continue;
                    }
                    try {
                        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(
                                resources[i].getInputStream(), configuration,
                                resources[i].toString(),
                                configuration.getSqlFragments());
                        xmlMapperBuilder.parse();
                    } catch (Exception e) {
                        throw new NestedIOException(
                                "Failed to parse mapping resource: '"
                                        + resources[i] + "'", e);
                    } finally {
                        ErrorContext.instance().reset();
                    }
                }
            }
            // 重新扫描加载class
            Field f = Configuration.class.getDeclaredField("mapperRegistry");
            f.setAccessible(true);
            f.set(configuration, new MapperRegistry(configuration));
            Iterator<Class<?>> iterator = mappers.iterator();
            while (iterator.hasNext()) {
                Class<?> class1 = (Class<?>) iterator.next();
                configuration.addMapper(class1);
            }

        }

        private void removeConfig(Configuration configuration) throws Exception {
            Class<?> classConfig = configuration.getClass();
            clearMap(classConfig, configuration, "mappedStatements");
            clearMap(classConfig, configuration, "caches");
            clearMap(classConfig, configuration, "resultMaps");
            clearMap(classConfig, configuration, "parameterMaps");
            clearMap(classConfig, configuration, "keyGenerators");
            clearMap(classConfig, configuration, "sqlFragments");
            clearSet(classConfig, configuration, "loadedResources");

        }

        private void clearMap(Class<?> classConfig,
                Configuration configuration, String fieldName) throws Exception {
            Field field = classConfig.getDeclaredField(fieldName);
            field.setAccessible(true);
            Map mapConfig = (Map) field.get(configuration);
            mapConfig.clear();
        }

        private void clearSet(Class<?> classConfig,
                Configuration configuration, String fieldName) throws Exception {
            Field field = classConfig.getDeclaredField(fieldName);
            field.setAccessible(true);
            Set setConfig = (Set) field.get(configuration);
            setConfig.clear();
        }

        public void scan() throws IOException {
            if (!fileMapping.isEmpty()) {
                return;
            }

            if (resources != null) {
                for (int i = 0; i < resources.length; i++) {
                    String multi_key = getValue(resources[i]);
                    fileMapping.put(resources[i].getFilename(), multi_key);
                }
            }
            if (mapperFiles != null) {
                for (int i = 0; i < mapperFiles.length; i++) {
                    fileMapping.put(mapperFiles[i].getAbsolutePath(),
                            mapperFiles[i].lastModified() + "");
                }
            }

        }

        private String getValue(Resource resource) throws IOException {
            String contentLength = String.valueOf((resource.contentLength()));
            String lastModified = String.valueOf((resource.lastModified()));
            return new StringBuilder(contentLength).append(lastModified)
                    .toString();
        }

        public boolean isChanged() throws IOException {
            boolean isChanged = false;
            if (resources != null) {
                for (int i = 0; i < resources.length; i++) {
                    String name = resources[i].getFilename();
                    String value = fileMapping.get(name);
                    String multi_key = getValue(resources[i]);
                    if (!multi_key.equals(value)) {
                        isChanged = true;
                        fileMapping.put(name, multi_key);
                    }
                }
            }
            if (mapperFiles != null) {
                for (int i = 0; i < mapperFiles.length; i++) {
                    String name = mapperFiles[i].getAbsolutePath();
                    String preValue = fileMapping.get(name);
                    String curValue = mapperFiles[i].lastModified() + "";
                    if (!curValue.equals(preValue)) {
                        isChanged = true;
                        fileMapping.put(name, curValue);
                    }
                }
            }
            return isChanged;
        }
    }

    @Override
    public void destroy() throws Exception {
        if (service != null) {
            service.shutdownNow();
        }
    }

}
