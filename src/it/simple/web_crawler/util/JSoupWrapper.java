package it.simple.web_crawler.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JSoupWrapper {
	private Map<String, String> cookies = new HashMap<String, String>();

	private String lang = null;
	
	public JSoupWrapper()
	{
		super();
		lang = "it-IT"; //en-US
	}
	
	public JSoupWrapper(String lang)
	{
		super();
		this.lang = lang; //en-US
	}
	
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Document get(String url) throws IOException {
	    Connection connection = Jsoup
	    		.connect(url)
	    		.ignoreContentType(true)
	    		.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; "+lang+"; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6");
	    for (Entry<String, String> cookie : cookies.entrySet()) {
	        connection.cookie(cookie.getKey(), cookie.getValue());
	    }
	    Response response = connection.execute();
	    cookies.putAll(response.cookies());
	    return response.parse();
	}
}
