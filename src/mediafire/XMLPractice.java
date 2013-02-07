package mediafire;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLPractice {
	public static void main(String argv[]) throws Throwable {

		// test1();

		File xml = new File("get_ses.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xml);

		doc.getDocumentElement().normalize();

		Element e = doc.getDocumentElement();

		NodeList nlist = e.getElementsByTagName("action");

		System.out.println(nlist.item(0).getNodeName());

		System.out.println(nlist.item(0).getChildNodes().item(0)
				.getTextContent());

	}

}
