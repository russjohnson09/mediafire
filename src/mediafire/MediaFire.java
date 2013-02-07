package mediafire;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//http://www.mkyong.com/java/how-to-get-alexa-ranking-in-java/
/**
 * ssha http://www.mkyong.com/java/java-sha-hashing-example/
 * 
 * @author russ
 * 
 */

public class MediaFire {

	private boolean json;

	public MediaFire(boolean json) {
		this.json = json;
	}

	public static void main(String[] args) throws Throwable {

		MediaFire m = new MediaFire(true);

		if (m.json) {

		} else {
			m.getValidation();
		}

	}

	private void getValidation() throws Throwable {
		URL request;

		String s1 = "https://www.mediafire.com/api/user/get_session_token.php?";
		String email = "russjohnson09@gmail.com";
		Scanner scan = new Scanner(System.in);
		String password = scan.nextLine();
		scan.close();
		String appid = "12472";

		String apikey = "xiq460s1qhm74m56yzj3a53a53d4m870cmwsnnf5";

		String sig = email + password + appid + apikey;

		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(sig.getBytes());

		byte byteData[] = md.digest();

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}

		sig = hexString.toString();

		request = new URL(s1 + "email=" + email + "&password=" + password
				+ "&application_id=" + appid + "&signature=" + sig);

		URLConnection con = request.openConnection();

		/*
		 * BufferedReader in = new BufferedReader(new InputStreamReader(
		 * con.getInputStream()));
		 * 
		 * String inputLine; while ((inputLine = in.readLine()) != null)
		 * System.out.println(inputLine); in.close();
		 */

		InputStream is = con.getInputStream();

		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document doc = dBuilder.parse(is);

		Element element = doc.getDocumentElement();

		NodeList nodeList = element.getElementsByTagName("response");

		System.out.println(nodeList);

		System.out.println(nodeList.item(0));
		//
		// System.out.println(nodeList.item(0));

	}
}
