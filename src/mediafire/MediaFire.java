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

/**
 * Simple console for mediafire.
 * 
 * @author russ
 * 
 */

public class MediaFire {

	public final boolean JSON;
	public final String SES;
	public final String EMAIL = "russjohnson09@gmail.com";
	public final String API = "https://www.mediafire.com/api/";
	public final String RSES = "user/get_session_token.php?";
	public final String APPID = "12472";
	public final String APIKEY = "xiq460s1qhm74m56yzj3a53a53d4m870cmwsnnf5";

	public MediaFire(boolean JSON) {
		this.JSON = JSON;
		if (JSON) {
			SES = null;
		} else {
			SES = getSessionToken();
		}
	}

	private String getSessionToken() {

		Scanner scan = new Scanner(System.in);
		String pass = scan.nextLine();
		scan.close();

		String sig = getSignature(EMAIL + pass + APPID + APIKEY);

		URL request;
		try {
			request = new URL(API + "user/get_session_token.php?email=" + EMAIL
					+ "&password=" + pass + "&application_id=" + APPID
					+ "&signature=" + sig);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}

		InputStream is = processRequest(request);

		return xmlProcess("session_token", is);

	}

	private String xmlProcess(String string, InputStream is) {
		DocumentBuilder dBuilder;
		try {
			dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			Element e = doc.getDocumentElement();
			NodeList nodeList = e.getElementsByTagName(string);
			if (nodeList != null) {
				return nodeList.item(0).getChildNodes().item(0)
						.getTextContent();
			} else {
				return null;
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private InputStream processRequest(URL request) {
		URLConnection con;
		try {
			con = request.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		InputStream is;
		try {
			is = con.getInputStream();
			return is;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getSignature(String str) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

		md.update(str.getBytes());

		byte byteData[] = md.digest();

		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}

		return hexString.toString();
	}

	public static void main(String[] args) throws Throwable {

		MediaFire m = new MediaFire(false);
		System.out.println(m.SES);

	}

}
