package com.bsoft.common.utils;

import com.bsoft.common.ws.listener.MarshallerListener;
import com.bsoft.common.ws.model.basic.AnesthesiaRecord;
import com.bsoft.common.ws.model.basic.MedicalRecordDiagnosisInfo;
import com.bsoft.common.ws.model.request.reception.ReceptionResult;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * xml/Bean转换工具类
 */
public class XmlBeanUtil {

    /**
     * JavaBean转xml(简化版，针对数据xml，没有文件头，没有namespace)
     * @param object JavaBean
     * @return xml
     */
    public static String convertToXmlS(Object object) {
        String result = null;
        try {
            JAXBContext context =JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true); // 清除<?xml ……>头标签
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化输出xml
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setListener(new MarshallerListener());
            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);
            result = writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * JavaBean转xml
     * @param object JavaBean
     * @return xml
     */
    public static String convertToXml(Object object) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
            marshaller.setListener(new MarshallerListener());

            StringWriter out = new StringWriter();
            OutputFormat format = new OutputFormat();
            format.setIndent(true);
            format.setNewlines(true);
            format.setNewLineAfterDeclaration(false);
            XMLWriter writer = new XMLWriter(out, format);

            XMLFilterImpl filter = new XMLFilterImpl() {
                private boolean ignoreNamespace = false;
                private String rootNamespace = null;
                private boolean isRootElement = true;

                @Override
                public void startDocument() throws SAXException {
                    super.startDocument();
                }

                @Override
                public void startPrefixMapping(String prefix, String uri) throws SAXException {
                    if (this.rootNamespace != null) uri = this.rootNamespace;
                    if (!this.ignoreNamespace) super.startPrefixMapping("", uri);
                }

                @Override
                public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
                    if (this.ignoreNamespace) uri = "";
                    if (this.isRootElement) this.isRootElement = false;
                    else if (!uri.equals("") && !localName.contains("xmlns")) localName = localName + " xmlns=\"" + uri + "\"";
                    if (qName.contains("ns2:")) qName = qName.substring(4);
                    super.startElement(uri, localName, qName, atts);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.contains("ns2:")) qName = qName.substring(4);
                    if (this.ignoreNamespace) uri = "";
                    super.endElement(uri, localName, qName);
                }
            };

            filter.setContentHandler(writer);
            marshaller.marshal(object, filter);
            result = out.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * xml转JavaBean
     * @param xml xml
     * @param cla JavaBean类型
     * @param <T> 输入的JavaBean类型
     * @return JavaBean
     */
    public static <T> T converyToJavaBean(String xml, Class<T> cla) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(cla);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            XMLReader reader = XMLReaderFactory.createXMLReader();
            XMLFilterImpl filter = new XMLFilterImpl() {
                private boolean ignoreNamespace = false;

                @Override
                public void startDocument() throws SAXException {
                    super.startDocument();
                }

                @Override
                public void startPrefixMapping(String prefix, String uri) throws SAXException {
                    uri = "";
                    if (!this.ignoreNamespace) super.startPrefixMapping("", uri);
                }

                @Override
                public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
                    uri = "";
                    if (this.ignoreNamespace) uri = "";
                    super.startElement(uri, localName, qName, atts);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (this.ignoreNamespace) uri = "";
                    super.endElement(uri, localName, qName);
                }
            };

            filter.setParent(reader);
            InputSource inputSource = new InputSource(new StringReader(xml));
            SAXSource saxSource = new SAXSource(filter, inputSource);
            t = (T) unmarshaller.unmarshal(saxSource);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return t;
    }


    public void res() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<messages xmlns=\"http://www.neusoft.com/hit/rhin\">\n" +
                "  <heartbeat>0</heartbeat>\n" +
                "  <switchset>\n" +
                "    <authority>\n" +
                "      <authoritytype>0</authoritytype>\n" +
                "      <username></username>\n" +
                "      <userpwd></userpwd>\n" +
                "      <license></license>\n" +
                "    </authority>\n" +
                "    <visitor>\n" +
                "      <sourceorgan></sourceorgan>\n" +
                "      <sourcedomain></sourcedomain>\n" +
                "    </visitor>\n" +
                "    <serviceinf>\n" +
                "      <servicecode></servicecode>\n" +
                "    </serviceinf>\n" +
                "    <provider>\n" +
                "      <targetorgan></targetorgan>\n" +
                "      <targetdomain></targetdomain>\n" +
                "    </provider>\n" +
                "    <route></route>\n" +
                "    <process></process>\n" +
                "  </switchset>\n" +
                "  <business>\n" +
                "    <standardcode></standardcode>\n" +
                "    <requestset>\n" +
                "      <reqpaging>0</reqpaging>\n" +
                "      <reqpageindex>-1</reqpageindex>\n" +
                "      <reqpageset>0</reqpageset>\n" +
                "    </requestset>\n" +
                "    <datacompress>0</datacompress>\n" +
                "    <daqtaskid></daqtaskid>\n" +
                "    <businessdata>\n" +
                "      <result>\n" +
                "        <taskid></taskid>\n" +
                "        <domaincode></domaincode>\n" +
                "        <domainname></domainname>\n" +
                "        <resourcecode></resourcecode>\n" +
                "        <resourcename></resourcename>\n" +
                "        <uploadtime></uploadtime>\n" +
                "        <resultcode>0</resultcode>\n" +
                "        <resultdesc></resultdesc>\n" +
                "        <tables>\n" +
                "          <tableinfo>\n" +
                "            <tablename></tablename>\n" +
                "            <tabledesc></tabledesc>\n" +
                "            <setcode></setcode>\n" +
                "            <setname></setname>\n" +
                "            <recordcount>0</recordcount>\n" +
                "            <errorrecordcount>0</errorrecordcount>\n" +
                "            <rulecount>0</rulecount>\n" +
                "            <errorcount>0</errorcount>\n" +
                "          </tableinfo>\n" +
                "        </tables>\n" +
                "        <report></report>\n" +
                "      </result>\n" +
                "    </businessdata>\n" +
                "  </business>\n" +
                "</messages>\n";
        System.out.println(XmlBeanUtil.converyToJavaBean(xml, ReceptionResult.class));
    }

    public void req() {
        Integer i = new MedicalRecordDiagnosisInfo().getAgeYear();
        System.out.println(
                convertToXmlS(new AnesthesiaRecord())
        );
    }

}
