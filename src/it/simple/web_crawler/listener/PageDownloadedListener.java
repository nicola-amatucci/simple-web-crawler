package it.simple.web_crawler.listener;

public interface PageDownloadedListener {
	public void newPageDownloaded(String url, String path);
}
