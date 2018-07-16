package com.chechezhijia.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xml 与 map 的互相转化
 * @author qiaoshiyong@bshf360.com
 * @since 2018/7/2 15:03
 */
@SuppressWarnings("unchecked")
public class XmlUtil {

    // xml ==> map
    public static Map<String, String> xml2Map(HttpServletRequest request) throws IOException, DocumentException {
        HashMap map = new HashMap();
        SAXReader reader = new SAXReader();

        InputStream is = request.getInputStream();
        Document doc = reader.read(is);
        Element rootElement = doc.getRootElement();
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            map.put(element.getName(), element.getText());
        }
        is.close();
        return map;
    }
}
