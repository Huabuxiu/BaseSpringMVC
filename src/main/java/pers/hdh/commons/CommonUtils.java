package pers.hdh.commons;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * @program: BaseSpringMVC
 * @description: 工具类
 * @author: Huabuxiu
 * @create: 2019-03-12 21:09
 **/
public class CommonUtils {
    /**
     * 解析dh-springmvc的配置文件，获取扫描的基本包名
     * @param contextConfigLocation
     * @return
     * @throws Exception
     */

    public static String getBasePackName(String contextConfigLocation)throws Exception
    {

        //读取Xml文件
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        InputStream in = CommonUtils.class.getClassLoader().getResourceAsStream(contextConfigLocation);

        Document doc = builder.parse(in);


        //开始解析
        Element root = doc.getDocumentElement();

//        bean
//        System.out.println(root.getTagName());

        NodeList childNodes = root.getChildNodes();

        for (int i= 0; i < childNodes.getLength();i++){
            Node child = childNodes.item(i);
            if (child instanceof Element){
                Element element = (Element) child;
//                System.out.println(element.getTagName());
                String attribute = element.getAttribute("base-package");
//                System.out.println(attribute);
                if (attribute != null || "".equals(attribute.trim()))
                {
                    return attribute.trim();
                }
            }
        }
        return null;
    }


}
