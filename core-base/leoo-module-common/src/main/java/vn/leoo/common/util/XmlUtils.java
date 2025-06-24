package vn.leoo.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter; 
import java.util.Iterator;


import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlUtils {
	/**
	 * Chuyen doi Document XML sang chuoi XML
	 * @param doc Co kieu org.w3c.dom.Document
	 * @return Chuoi XML
	 */
	public static String convertDocumentToString(Document doc) throws Exception{
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        String output = null;
        try {
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
//            Writer out = new StringWriter();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
//            output = out.toString();
            output = writer.getBuffer().toString(); //writer.getBuffer().toString();//.replaceAll("\n|\r", "");
 
        } catch (TransformerException e) {
            e.printStackTrace();
            throw e;
        }
         
        return output;
    }
 
	/**
	 * Chuyen doi chuoi XML sang document XML
	 * @param xmlStr Co kieu String la chuoi XML
	 * @return org.w3c.dom.Document
	 */
	public static Document convertStringToDocument(String xmlStr, boolean namespaceAware) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        Document doc = null;
        try 
        {   
        	factory.setNamespaceAware(namespaceAware);
            builder = factory.newDocumentBuilder();  
//          doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
            
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlStr));
            doc = builder.parse(is);
        	doc.getDocumentElement().normalize();
            
//            doc = builder.parse(new ByteArrayInputStream(xmlStr.getBytes("UTF8")));
        	
        } catch (Exception e) {  
            e.printStackTrace();  
            throw e;
        } 
        return doc;
    }
	
	/**
	 * Chuyen doi file XML sang document XML
	 * @param fileUrl Co kieu String la duong dan toi file XML
	 * @return org.w3c.dom.Document
	 */
	public static Document convertXmlFileToDocument(String fileUrl, boolean namespaceAware) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        Document doc = null;
        try 
        {  
        	File xmlFile = new File(fileUrl);
        	factory.setNamespaceAware(namespaceAware);
        	builder = factory.newDocumentBuilder();  
            doc = builder.parse(xmlFile); 
            
        } catch (Exception e) {  
            e.printStackTrace(); 
            throw e;
        } 
        return doc;
    }
	
	/**
	 * Chuyen doi file XML sang document XML
	 * @param xmlFile Co kieu java.io.File la file XML
	 * @return org.w3c.dom.Document
	 */
	public static Document convertXmlFileToDocument(File xmlFile, boolean namespaceAware) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        Document doc = null;
        try 
        {  
        	factory.setNamespaceAware(namespaceAware);
        	builder = factory.newDocumentBuilder();  
            doc = builder.parse(xmlFile); 
            
        } catch (Exception e) {  
            e.printStackTrace();  
            throw e;
        } 
        return doc;
    }
	
	/**
	 * Chuyen doi file XML sang document XML
	 * @param in Co kieu java.io.InputStream la file XML
	 * @return org.w3c.dom.Document
	 */
	public static Document convertXmlFileToDocument(InputStream in, boolean namespaceAware) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        Document doc = null;
        try 
        {  
        	factory.setNamespaceAware(namespaceAware);
        	builder = factory.newDocumentBuilder();  
            doc = builder.parse(in); 
            
        } catch (Exception e) {  
            e.printStackTrace();  
            throw e;
        } 
        return doc;
    }
	
	/**
	 * Chuyen doi file XML sang document XML
	 * @param is co kieu org.xml.sax.InputSource la file XML
	 * @return org.w3c.dom.Document
	 */
	public static Document convertXmlFileToDocument(InputSource is, boolean namespaceAware) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        Document doc = null;
        try 
        {  
        	factory.setNamespaceAware(namespaceAware);
        	builder = factory.newDocumentBuilder();  
            doc = builder.parse(is); 
            
        } catch (Exception e) {  
            e.printStackTrace(); 
            throw e;
        } 
        return doc;
    }
    
	/**
	 * Chuyen doi file XML sang chuoi XML
	 * @param fileUrl co kieu String la duong dan toi file XML
	 * @return Chuoi XML
	 */
	public static String convertXmlFileToString(String fileUrl, boolean namespaceAware) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        String output = null;
        try 
        {  
        	File xmlFile = new File(fileUrl);
        	
        	factory.setNamespaceAware(namespaceAware);
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse(xmlFile); 
            
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            output = writer.getBuffer().toString();
            
        } catch (Exception e) {  
            e.printStackTrace();  
            throw e;
        } 
        return output;
    }
	
	/**
	 * Chuyen doi file XML sang chuoi XML
	 * @param xmlFile co kieu java.io.File la file XML
	 * @return Chuoi XML
	 */
	public static String convertXmlFileToString(File xmlFile, boolean namespaceAware) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        String output = null;
        try 
        {  
        	factory.setNamespaceAware(namespaceAware);
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse(xmlFile); 
            
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            output = writer.getBuffer().toString();
            
        } catch (Exception e) {  
            e.printStackTrace();
            throw e;
        } 
        return output;
    }
	
	/**
	 * Chuyen doi file XML sang chuoi XML
	 * @param in co kieu org.xml.sax.InputSource
	 * @return Chuoi XML
	 */
	public static String convertXmlFileToString(InputSource is, boolean namespaceAware) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        String output = null;
        try 
        {  
        	factory.setNamespaceAware(namespaceAware);
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse(is); 
            
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            output = writer.getBuffer().toString();
            
        } catch (Exception e) {  
            e.printStackTrace();
            throw e;
        } 
        return output;
    }
	
	/**
	 * Chuyen doi file XML sang chuoi XML
	 * @param in co kieu java.io.InputStream
	 * @return Chuoi XML
	 */
	public static String convertXmlFileToString(InputStream in, boolean namespaceAware) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        String output = null;
        try 
        {  
        	factory.setNamespaceAware(namespaceAware);
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse(in); 
            
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            output = writer.getBuffer().toString();
            
        } catch (Exception e) {  
            e.printStackTrace(); 
            throw e;
        } 
        return output;
    }
	
	/**
	 * Xoa mot node trong XML
	 * @param doc Co kieu org.w3c.dom.Document la doi tuong XML
	 * @param tagName Co kieu String la ten node can xoa
	 */
	public static void removeElementByTagName(Document doc, String tagName) throws Exception{
		try {
			// retrieve the element 'link'
			Element element = (Element) doc.getElementsByTagName(tagName).item(0);
			// remove the specific node
			element.getParentNode().removeChild(element);
			// Normalize the DOM tree, puts all text nodes in the
			// full depth of the sub-tree underneath this node
			doc.normalize();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Xoa mot node trong XML
	 * @param doc Co kieu org.w3c.dom.Document la doi tuong XML
	 * @param tagName Co kieu String la ten node can xoa
	 */
	public static void removeElementByTagName(Document doc, String tagName, int index) throws Exception{
		try {
			// retrieve the element 'link'
			Element element = (Element) doc.getElementsByTagName(tagName).item(index);
			// remove the specific node
			element.getParentNode().removeChild(element);
			// Normalize the DOM tree, puts all text nodes in the
			// full depth of the sub-tree underneath this node
			doc.normalize();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Lay gia tri cua mot node trong XML
	 * @param doc Co kieu org.w3c.dom.Document la doi tuong XML
	 * @param tagName Co kieu String la ten node can lay gia tri
	 * @return Gia tri cua node can lay co kieu String
	 */
	public static String getTextContent(Document doc, String tagName) throws Exception{
		String val = "";
		try {
			NodeList nodeList = doc.getElementsByTagName(tagName);
			if(nodeList!=null && nodeList.getLength()>0){
				Element element = (Element) nodeList.item(0);
				if(element != null)
					val = element.getTextContent();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
		return val;
	}
	
	/**
	 * Lay gia tri cua mot node trong XML
	 * @param doc Co kieu org.w3c.dom.Document la doi tuong XML
	 * @param tagName Co kieu String la ten node can lay gia tri
	 * @return Gia tri cua node can lay co kieu String
	 */
	public static String getNodeValue(Document doc, String tagName) throws Exception{
		String val = "";
		try {
			NodeList nodeList = doc.getElementsByTagName(tagName);
			if(nodeList!=null && nodeList.getLength()>0){
				Element element = (Element) nodeList.item(0);
				if(element != null)
					val = element.getNodeValue();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
		return val;
	}
	
    public static String getValueByXpath(Document doc, String prefix, String namespaceURI, String sXpath) throws Exception{
    	String val = "";
    	try {
            // Create XPathFactory object
            XPathFactory xpathFactory = XPathFactory.newInstance();

            // Create XPath object
            XPath xpath = xpathFactory.newXPath();
            
            // Create XPathExpression object     
            if(prefix!=null && namespaceURI!=null){
	            NamespaceContext ctx = new NamespaceContext() {
	            	public String getNamespaceURI(String _prefix) {
	                	String ns = _prefix.equals(prefix) ? namespaceURI : null;
	
	                    return ns; 
	                }
	            	
	                @SuppressWarnings({ "rawtypes", "unchecked" })
					public Iterator getPrefixes(String val) {
	                    return null;
	                }
	                
	                public String getPrefix(String uri) {
	                    return null;
	                }
	            };
	            xpath.setNamespaceContext(ctx);
            }
            XPathExpression expr = xpath.compile(sXpath + "/text()"); 
//            XPathExpression expr = xpath.compile("//*[local-name()='OrganId']" + "/text()");
            val = (String) expr.evaluate(doc, XPathConstants.STRING);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return val;
	}
    
    public static String getValueByXpath(Document doc, String prefix, String namespaceURI, String sXpath, int index) throws Exception{
    	String val = "";
    	try {
            // Create XPathFactory object
            XPathFactory xpathFactory = XPathFactory.newInstance();

            // Create XPath object
            XPath xpath = xpathFactory.newXPath();
            
            // Create XPathExpression object     
            if(prefix!=null && namespaceURI!=null){
	            NamespaceContext ctx = new NamespaceContext() {
	            	public String getNamespaceURI(String _prefix) {
	                	String ns = _prefix.equals(prefix) ? namespaceURI : null;
	
	                    return ns; 
	                }
	            	
	                @SuppressWarnings({ "rawtypes", "unchecked" })
					public Iterator getPrefixes(String val) {
	                    return null;
	                }
	                
	                public String getPrefix(String uri) {
	                    return null;
	                }
	            };
	            xpath.setNamespaceContext(ctx);
            }
            XPathExpression expr = xpath.compile(sXpath);
            NodeList nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
            if(nodes!=null && index<nodes.getLength()){
            	Node currentItem = nodes.item(index);
                val = currentItem.getTextContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return val;
	}
    
    public static String getValueByXpath(Document doc, String prefix, String namespaceURI, String parentXpath, String childXpath, int index) throws Exception{    	
    	childXpath = childXpath.substring(1, childXpath.length());
    	String xpath = parentXpath + childXpath;
    	String val = getValueByXpath(doc, prefix, namespaceURI, xpath, index);
        return val;
	}
    
    public static String[] getValuesByXpath(Document doc, String prefix, String namespaceURI, String sXpath) throws Exception{
        String[] vals = null;
        try {
            // Create XPathFactory object
            XPathFactory xpathFactory = XPathFactory.newInstance();

            // Create XPath object
            XPath xpath = xpathFactory.newXPath();
            
            // Create XPathExpression object     
            if(prefix!=null && namespaceURI!=null){
	            NamespaceContext ctx = new NamespaceContext() {
	            	public String getNamespaceURI(String _prefix) {
	                	String ns = _prefix.equals(prefix) ? namespaceURI : null;
	
	                    return ns; 
	                }
	            	
	                @SuppressWarnings({ "rawtypes", "unchecked" })
					public Iterator getPrefixes(String val) {
	                    return null;
	                }
	                
	                public String getPrefix(String uri) {
	                    return null;
	                }
	            };
	            xpath.setNamespaceContext(ctx);
            }
            XPathExpression expr = xpath.compile(sXpath);
            NodeList nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
            if(nodes != null){
            	vals = new String[nodes.getLength()];
	            for (int i=0; i<nodes.getLength(); i++) {
	                Node currentItem = nodes.item(i);
	                vals[i] = currentItem.getTextContent();
	            }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return vals;
	}
	
	/**
	 * Gan gia tri cho mot node trong XML
	 * @param doc Co kieu org.w3c.dom.Document la doi tuong XML
	 * @param tagName Co kieu String la ten node can gan gia tri
	 * @param Gia tri can gan co kieu String
	 */
	public static void setTextContent(Document doc, String tagName, String val) throws Exception{
		try {
			Element element = (Element) doc.getElementsByTagName(tagName).item(0);
			if(element != null)
				element.appendChild(doc.createTextNode(val));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	public static void setValueByXpath(Document doc, String prefix, String namespaceURI, String sXpath, String value) throws Exception{
        try {
            // Create XPathFactory object
            XPathFactory xpathFactory = XPathFactory.newInstance();

            // Create XPath object
            XPath xpath = xpathFactory.newXPath();
            
            // Create XPathExpression object     
            if(prefix!=null && namespaceURI!=null){
	            NamespaceContext ctx = new NamespaceContext() {
	            	public String getNamespaceURI(String _prefix) {
	                	String ns = _prefix.equals(prefix) ? namespaceURI : null;
	
	                    return ns; 
	                }
	            	
	                @SuppressWarnings({ "rawtypes", "unchecked" })
					public Iterator getPrefixes(String val) {
	                    return null;
	                }
	                
	                public String getPrefix(String uri) {
	                    return null;
	                }
	            };
	            xpath.setNamespaceContext(ctx);
            }
            XPathExpression expr = xpath.compile(sXpath);
            NodeList nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
            if(nodes != null){
	            for (int i=0; i<nodes.getLength(); i++) {
	                Node currentItem = nodes.item(i);
	                currentItem.setTextContent(value);
	            }
            }
            /*Element element = (Element)expr.evaluate(doc, XPathConstants.NODE);
            if(element != null){
//				element.appendChild(doc.createTextNode(value));
				element.setTextContent(value);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
	}
	
	/**
	 * Them mot node con trong node cha
	 * @param doc Co kieu org.w3c.dom.Document la doi tuong XML
	 * @param parentTagName Co kieu String la ten node can gan them node con
	 * @param childNodeName Co kieu String la ten node con
	 * @param value Co kieu String la gia tri cua node con
	 */
	public static void appendChildNode(Document doc, String parentTagName, String childNodeName, String value) throws Exception{
		try {
			appendChildNode(doc, parentTagName, childNodeName, value, 0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	public static void appendChildNode(Document doc, String parentTagName, String childNodeName, String value, int index) throws Exception{
		try {
			Element element = (Element) doc.getElementsByTagName(parentTagName).item(index);
			Element newE = doc.createElement(childNodeName);
			newE.appendChild(doc.createTextNode(value));
//			newE.setNodeValue(value);
			element.appendChild(newE);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	public static void appendChildNode(Document docParent, String parentTagName, Document docChild, boolean deep) throws Exception{
		try {
			Element element = (Element) docParent.getElementsByTagName(parentTagName).item(0);
			Node node = docParent.importNode(docChild.getDocumentElement(), deep);
			element.appendChild(node);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	public static void appendChildNodeByXpath(Document docParent, String prefix, String namespaceURI, String sXpath
			, Document docChild, boolean deep) throws Exception{
        try {
            // Create XPathFactory object
            XPathFactory xpathFactory = XPathFactory.newInstance();

            // Create XPath object
            XPath xpath = xpathFactory.newXPath();
            
            // Create XPathExpression object     
            if(prefix!=null && namespaceURI!=null){
	            NamespaceContext ctx = new NamespaceContext() {
	            	public String getNamespaceURI(String _prefix) {
	                	String ns = _prefix.equals(prefix) ? namespaceURI : null;
	
	                    return ns; 
	                }
	            	
	            	
	                @SuppressWarnings({ "rawtypes", "unchecked" })
					public Iterator getPrefixes(String val) {
	                    return null;
	                }
	                
	                public String getPrefix(String uri) {
	                    return null;
	                }
	            };
	            xpath.setNamespaceContext(ctx);
            }
            XPathExpression expr = xpath.compile(sXpath);
            Element element = (Element)expr.evaluate(docParent, XPathConstants.NODE);
            Node node = docParent.importNode(docChild.getDocumentElement(), deep);
			element.appendChild(node);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
	}
	
	public static void saveXmlStringToFile(String xmlStr, String outputFile, boolean namespaceAware) throws Exception{
		OutputStream os = null;
		
		try {
			Document doc = convertStringToDocument(xmlStr, namespaceAware);
			os = new FileOutputStream(outputFile);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			trans.transform(new DOMSource(doc), new StreamResult(os));
		} catch (Exception e) {
			try {
				if(os != null)
				os.close();
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}
			System.out.println(e.getMessage());
			throw e;
		} 
	}
	
	public static void saveDocumentToFile(Document doc, String outputFile) throws Exception{
		OutputStream os = null;
		
		try {
			os = new FileOutputStream(outputFile);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			trans.transform(new DOMSource(doc), new StreamResult(os));
		} catch (Exception e) {
			try {
				if(os != null)
				os.close();
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}
			System.out.println(e.getMessage());
			throw e;
		} 
	}
	
	public static String getValueAttribute(Node node, String attrName) {
		String result = "";
		try {
			if (node == null) return "";
			if (node.hasAttributes()) {
				result = node.getAttributes().getNamedItem(attrName).getNodeValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}
		return result;
	}
	
	public static Node getNodeByPath(Document doc, String path) {
		Node node = null;
		try {
			if (StringUtils.isNull(path)) return null;
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile(path).evaluate(
					doc, XPathConstants.NODESET);
			if (nodeList != null && nodeList.getLength() > 0) {
				node = nodeList.item(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return node;
	}
	
	/**
	 * Lay mot node theo index
	 * */
	public static Node getNodeByPath(Document doc, String path, int index) {
		Node node = null;
		try {
			if (StringUtils.isNull(path)) return null;
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile(path).evaluate(
					doc, XPathConstants.NODESET);
			if (nodeList != null && nodeList.getLength() > 0 && nodeList.getLength() >= index) {
				node = nodeList.item(index);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return node;
	}
	
	public static int getNodeListLength(Document doc, String tagName) throws Exception{
		int len = 0;
		try {
			NodeList element = doc.getElementsByTagName(tagName);
			if(element != null)
				len = element.getLength();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
		return len;
	}
	
	/*
	 * public static void jaxbObjectToXML(Object obj, String outputUrl){ try{
	 * //Create JAXB Context JAXBContext jaxbContext =
	 * JAXBContext.newInstance(obj.getClass());
	 * 
	 * //Create Marshaller Marshaller jaxbMarshaller =
	 * jaxbContext.createMarshaller();
	 * 
	 * //Required formatting?? jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT,
	 * Boolean.TRUE); jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
	 * Boolean.TRUE);
	 * 
	 * //Store XML to File File file = new File(outputUrl);
	 * 
	 * //Writes XML file to file-system jaxbMarshaller.marshal(obj, file); } catch
	 * (JAXBException e){ e.printStackTrace(); } }
	 */
	
//	public static void main(String[] args) throws Exception{
//		try{
//			String url = "d:/Projects/TAS_SVN/DMDC6.0/SourceCode/BackEnd/schedule/configuration/resources/template/ngsp_0070_1.xml";
//			
//			String xml = XmlUtils.convertXmlFileToString(url, false);
//			xml = xml.replace("Cap1", "");
//			System.out.println(xml);
//			//TpsDynHdrVO hdr = new TpsDynHdrVO();
//			//XmlUtils.jaxbObjectToXML(hdr, url);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
}
