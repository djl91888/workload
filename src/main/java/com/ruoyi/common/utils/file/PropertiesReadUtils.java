package com.ruoyi.common.utils.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class PropertiesReadUtils {
    /**
     * 属性文件类
     */
    private Properties properties;

    /**
     * 路径
     */
    private String strPropertyPath = "/";

    private String strPropertyFile = "config.properties";

    /**
     * 单例
     */
    private final static PropertiesReadUtils CFG_INSTANCE = new PropertiesReadUtils();

    /**
     * 读
     */
    private PropertiesReadUtils() {
        properties = new Properties();
        InputStream is = null;

        try {
            is = getClass().getResourceAsStream(strPropertyPath + strPropertyFile);
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                throw new RuntimeException("ConfigReader read file Exception");
            } catch (RuntimeException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 得单例
     *
     * @return ConfigurationRead
     */
    public static PropertiesReadUtils getInstance() {
        return CFG_INSTANCE;
    }

    /**
     * Key取值
     *
     * @param strKey String
     * @return String
     */
    public String getValue(String strKey) {
        return properties.getProperty(strKey);
    }

    /**
     * 设置
     *
     * @param strPropertyFile String
     */
    public void setPropertyFile(String strPropertyFile) {
        this.strPropertyFile = strPropertyFile;
    }

    /**
     * 根据路径创建properties
     *
     * @param path
     * @return
     */
    public Properties propertiesCreate(String path) {
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = getClass().getResourceAsStream(path);
            properties.load(is);
            return properties;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                throw new RuntimeException("ConfigReader read file Exception");
            } catch (RuntimeException e1) {
                // TODO 自动生成 catch 块
                e1.printStackTrace();
            }
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return properties;
    }

    /**
     * 得到配置文件中所有项目key
     */
    @SuppressWarnings("unchecked")
    public String[] getAllItem(Properties properties) {
        Enumeration en = properties.propertyNames();

        List list = new ArrayList();

        while (en.hasMoreElements()) {
            list.add(en.nextElement());
        }
        return (String[]) list.toArray(new String[list.size()]);

    }

    /**
     * 得到配置文件中所有项目key
     */
    @SuppressWarnings("unchecked")
    public String[] getAllItem() {
        Enumeration en = properties.propertyNames();

        List list = new ArrayList();

        while (en.hasMoreElements()) {
            list.add(en.nextElement());
        }
        return (String[]) list.toArray(new String[list.size()]);

    }

    /**
     * 设置应用上下文路径
     */
    public void setAppPath(String appPath) {
        properties.put("appPath", appPath);
    }

    /**
     * 取应用上下文路径
     *
     * @return String
     */
    public String getAppPath() {
        return (String) properties.get("appPath");
    }


    /**
     * Key赋值
     *
     * @param strKey String
     * @return String
     */
    public Object setValue(String key,String value) {
        return properties.setProperty(key, value);
    }
}
