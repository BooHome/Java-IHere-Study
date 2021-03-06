package com.ihere.lucene.util;

import com.google.gson.Gson;
import com.ihere.lucene.config.LuceneConfig;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengshibo
 * @create 2018-07-18 14:41
 * @desc ${DESCRIPTION}
 **/
public class DocumentUtil {
    private static final Logger logger = LoggerFactory.getLogger(DocumentUtil.class);

    public static Document jsonToDoc(String json) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();
        try {
            map = gson.fromJson(json, Map.class);
        } catch (Exception e) {
            logger.error("数据解析为Map<String,String>失败：{}",json);
            return null;
        }
        Document doc = DocumentUtil.mapToDoc(map);
        return doc;
    }

    public static Document mapToDoc(Map<String, String> map) {
        Document doc = new Document();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals(LuceneConfig.getIDName())) {
                StringField stringField = new StringField(entry.getKey(), entry.getValue(), Field.Store.YES);
                doc.add(stringField);// 将属性以及值存储
            } else {
                TextField textField = new TextField(entry.getKey(), entry.getValue(), Field.Store.YES);
                doc.add(textField);// 将属性以及值存储
            }
        }
        return doc;
    }

    public static List<Document> jsonToDocs(String json) {
        Gson gson = new Gson();
        List<Map<String, String>> maps = new ArrayList<>();
        try {
            maps = gson.fromJson(json, List.class);
        } catch (Exception e) {
            logger.error("数据解析为List<Map<String,String>>失败：{}",json);
            return null;
        }
        List<Document> documents = new ArrayList<>();
        for (Map<String, String> map :
                maps) {
            Document doc = DocumentUtil.mapToDoc(map);
            documents.add(doc);
        }
        return documents;
    }


}
