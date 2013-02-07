package mediafire;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AlexaSEO {

	public static void main(String[] args) {

		AlexaSEO obj = new AlexaSEO();
		System.out.println("Ranking : " + obj.getAlexaRanking("alexa.com"));

	}

	public int getAlexaRanking(String domain) {

		int result = 0;

		String url = "http://data.alexa.com/data?cli=10&url=" + domain;

		try {

			URLConnection conn = new URL(url).openConnection();
			InputStream is = conn.getInputStream();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null)
				System.out.println(inputLine);
			in.close();

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = dBuilder.parse(is);

			Element element = doc.getDocumentElement();

			NodeList nodeList = element.getElementsByTagName("POPULARITY");
			if (nodeList.getLength() > 0) {
				Element elementAttribute = (Element) nodeList.item(0);
				String ranking = elementAttribute.getAttribute("TEXT");
				if (!"".equals(ranking)) {
					result = Integer.valueOf(ranking);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return result;
	}
}