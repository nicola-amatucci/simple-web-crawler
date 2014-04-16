package it.simple.web_crawler.listener;

import org.jsoup.nodes.Document;

public interface PageVisitedListener {
	public void pageVisited(String url, Document document);
}
