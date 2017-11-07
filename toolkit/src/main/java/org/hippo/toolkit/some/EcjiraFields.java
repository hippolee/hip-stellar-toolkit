package org.hippo.toolkit.some;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EcjiraFields {

    private static final Logger logger = LoggerFactory.getLogger(EcjiraFields.class);

    private static final String[] paths = new String[]{
            "/Users/litengfei/Desktop/ecjira/1.txt",
            "/Users/litengfei/Desktop/ecjira/2.txt",
            "/Users/litengfei/Desktop/ecjira/3.txt",
            "/Users/litengfei/Desktop/ecjira/4.txt",
            "/Users/litengfei/Desktop/ecjira/5.txt",
            "/Users/litengfei/Desktop/ecjira/6.txt",
            "/Users/litengfei/Desktop/ecjira/7.txt"
    };

    private static final String fieldPath = "/Users/litengfei/Desktop/ecjira/11.txt";

    private static final String regexName = "(?<=<b>)(.*)(?=</b>)";
    private static final String regexField = "(?<=id\\=\"associate_)(\\w*)(?=\")";
    private static final String regexFieldName = "<span class=\"field-name\">";
    private static final Pattern patternName = Pattern.compile(regexName);
    private static final Pattern patternField = Pattern.compile(regexField);
    private static final Pattern patternFieldName = Pattern.compile(regexFieldName);

    private static void readFile(String path) {
        BufferedReader reader = null;
        String line = null;
        int lineNum = 0;
        boolean isSpan = false;
        try {
            reader = new BufferedReader(new FileReader(path));
            logger.info("{}|read begin", path);
            while ((line = reader.readLine()) != null) {
                Matcher matcher = patternName.matcher(line);
                if(matcher.find()) {
                    logger.info("{}|{}|{}", path, lineNum, matcher.group());
                }
                if (isSpan) {
                    logger.info("{}|{}|{}", path, lineNum, line.trim());
                }
                matcher = patternField.matcher(line);
                if(matcher.find()) {
                    logger.info("{}|{}|{}", path, lineNum, matcher.group());
                }
                matcher = patternFieldName.matcher(line);
                if(matcher.find()) {
                    isSpan = true;
                } else {
                    isSpan = false;
                }
                lineNum++;
            }
            logger.info("{}|read over", path);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static void readFields(String path) {
        BufferedReader reader = null;
        String line = null;
        boolean isField = false;
        String fieldName = null;
        Map<String, String> fieldMap = new LinkedHashMap<>();
        try {
            reader = new BufferedReader(new FileReader(path));
            logger.info("{}|read begin", path);
            while ((line = reader.readLine()) != null) {
                if (!isField) {
                    fieldName = line;
                    isField = true;
                } else {
                    fieldMap.put(line, fieldName);
                    fieldName = null;
                    isField = false;
                }
            }
            logger.info("{}|read over", path);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }

        Iterator it = fieldMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
    }

    public static void main(String[] args) {
        readFields(fieldPath);
    }

}
