package com.ruan.project.Other.Utils;

import com.ruan.project.Moudle.Version;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author coolszy
 * @date 2012-4-26
 * @blog http://blog.92coding.com
 */
public class ParseXmlService {
    public Version parseXml(InputStream inStream, Version version) {
//		HashMap<String, String> hashMap = new HashMap<String, String>();
        try {
            // 实例化一个文档构建器工厂
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 通过文档构建器工厂获取一个文档构建器
            DocumentBuilder builder = null;

            builder = factory.newDocumentBuilder();

            // 通过文档通过文档构建器构建一个文档实例
            Document document = builder.parse(inStream);
            //获取XML文件根节点
            Element root = document.getDocumentElement();
            //获得所有子节点
            NodeList childNodes = root.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                //遍历子节点
                Node childNode = (Node) childNodes.item(j);
                if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element childElement = (Element) childNode;
                    //版本号
                    if ("version".equals(childElement.getNodeName())) {
                        version.setVersion(childElement.getFirstChild().getNodeValue());
//                    hashMap.put("version", childElement.getFirstChild().getNodeValue());
                    }
                    //软件名称
                    else if (("apkdownload".equals(childElement.getNodeName()))) {
                        version.setApkdownload(childElement.getFirstChild().getNodeValue());
//                    hashMap.put("name", childElement.getFirstChild().getNodeValue());
                    }
                    //下载地址
                    else if (("md5".equals(childElement.getNodeName()))) {
                        version.setMd5(childElement.getFirstChild().getNodeValue());
//                    hashMap.put("appurl", childElement.getFirstChild().getNodeValue());
                    }
                    //服务器更新地址
//                else if (("serverurl".equals(childElement.getNodeName()))) {
//                    hashMap.put("serverurl", childElement.getFirstChild().getNodeValue());
//                }
//                //xml更新地址
//                else if (("xmlurl".equals(childElement.getNodeName()))) {
//                    hashMap.put("xmlurl", childElement.getFirstChild().getNodeValue());
//                }
//                //图片更新地址
//                else if (("imgurl".equals(childElement.getNodeName()))) {
//                    hashMap.put("imgurl", childElement.getFirstChild().getNodeValue());
//                }
                }
            }
            return version;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
