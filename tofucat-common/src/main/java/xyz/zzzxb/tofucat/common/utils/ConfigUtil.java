package xyz.zzzxb.tofucat.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 读取 properties 配置文件中的内容，根据key
 * 多个值之间可以互相引用,比如
 * <p>
 *     ip=127.0.0.1
 *     port=80
 *     path=/req
 *     url=${ip}:${port}${path}
 * </p>
 * or
 * <p>
 *     port=80
 *     ip=127.0.0.1
 *     address=${ip}:${port}
 *     path=/req
 *     url=${address}${path}
 * </p>
 *
 * @author Zzzxb
 * @since 2021/5/19 16:29
 */
public class ConfigUtil {

    private static final Properties pro = new Properties();
    private static final String PROPERTIES_FILE_PATH_1 = "config.properties";
    private static final String PROPERTIES_FILE_PATH_2 = "conf" + File.separator + "config.properties";
    private static final String PROPERTIES_FILE_PATH_3 = "config" + File.separator + "config.properties";

    static {
        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ConfigUtil() { }

    private static void init() throws IOException {
        List<String> filePaths = morePath();
        for (String filePath : filePaths) {
            if (existFile(filePath)) {
                external(filePath);
                continue;
            }

            if (null != ConfigUtil.class.getResource( File.separator + filePath)) {
                interior(filePath);
            }
        }
    }

    private static List<String> morePath() {
        final String[] filePaths = {PROPERTIES_FILE_PATH_1, PROPERTIES_FILE_PATH_2, PROPERTIES_FILE_PATH_3};
        LinkedList<String> filePathList = new LinkedList<>(Arrays.asList(filePaths));
        for (String filePath : filePaths) {
            filePathList.add(new File(filePath).getAbsolutePath());
            filePathList.add(".." + File.separator + filePath);
        }
        return filePathList;
    }

    private static void interior(String filePath) throws IOException {
        pro.load(ConfigUtil.class.getResourceAsStream(File.separator + filePath));
    }

    private static void external(String filepath) {
        try (FileInputStream fileInputStream = new FileInputStream(filepath)) {
            pro.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean existFile(String filePath) {
        final File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        return file.isFile();
    }

    public static String getString(String key, String defaultValue) {
        final String value = getValue(key);
        return isNull(value) ? defaultValue : value;
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static Integer getInteger(String key, Integer defaultValue) {
        final String value = getValue(key);
        return isNull(value) ? defaultValue : Integer.parseInt(value);
    }

    public static Integer getInteger(String key) {
        final String value = getValue(key);
        return isNull(value) || equals("", value) ? 0 : Integer.parseInt(getValue(key));
    }

    public static Long getLong(String key, Long defaultValue) {
        final String value = getValue(key);
        return isNull(value) ? defaultValue : Long.parseLong(value);
    }

    public static Long getLong(String key) {
        final String value = getValue(key);
        return isNull(value) || equals("", value) ? 0L : Long.parseLong(getValue(key));
    }

    public static Boolean getBoolean(String key, Boolean defaultValue) {
        final String value = getValue(key);
        if (isNull(value)) {
            return defaultValue;
        }
        return getBoolean(key);
    }

    public static Boolean getBoolean(String key) {
        final String value = getValue(key);
        return isNull(value) || equals("false", value) || equals("1", value);
    }

    private static String getValue(String key) {
        String property = pro.getProperty(key);
        return StringUtils.isBlank(property) ? null : reference(property);
    }

    private static boolean isNull(String value) {
        return null == value;
    }

    private static boolean equals(String target, String value) {
        return target.trim().equals(value.trim());
    }

    private static String reference(String text) {
        Matcher matcher = Pattern.compile("\\$\\{[\\w\\\\.]+}").matcher(text);
        while (matcher.find()) {
            final String key = matcher.group().replace("${", "").replace("}", "");
            String pro = getString(key);
            text = text.replaceAll("\\$\\{" + key + "}", reference(pro));
        }
        return text;
    }
}