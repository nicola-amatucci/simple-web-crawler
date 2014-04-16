package it.simple.web_crawler;


import it.simple.web_crawler.interfaces.IUrlFilter;
import it.simple.web_crawler.listener.PageDownloadedListener;
import it.simple.web_crawler.listener.PageVisitedListener;
import it.simple.web_crawler.util.JSoupWrapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
 
public class WebCrawler {
  
	VisitedPagesList mVisitedPagesList = null;
	PagesQueue mPagesQueue = null;
	String baseUrl = null;
	String savePath = null;
	
	PageDownloadedListener mPageDownloadedListener = null;
	PageVisitedListener mPageVisitedListener = null;

	JSoupWrapper mJSoupWrapper = null;
	
	ArrayList<IUrlFilter> filters = null;
	
	public WebCrawler()
	{
		super();
		this.mVisitedPagesList = new VisitedPagesList();
		this.mPagesQueue = new PagesQueue();
		this.mJSoupWrapper = new JSoupWrapper();
		this.filters = new ArrayList<IUrlFilter>();
	}
	
	public WebCrawler(String baseLink) {
		this();
		this.baseUrl = baseLink;
		this.savePath = null;
	}
	
	public WebCrawler(String baseLink, String savePath) {
		this();
		this.baseUrl = baseLink;
		this.savePath = savePath;		
	}

	public void doWork() {
		this.mPagesQueue.clear();
		this.mVisitedPagesList.clear();
		this.mPagesQueue.addPage(baseUrl);
		this.processPages();
	}
	
	private void processPages() {
		
		do
		{
			String url = this.mPagesQueue.getNextPage();
			
			if (this.mVisitedPagesList.isVisited(url) == false)
			{
				if (this.filter(url))
				{
					this.mVisitedPagesList.addLink(url);
					continue;
				}
				
				try
				{
					Document doc = this.mJSoupWrapper.get(url);
					this.mVisitedPagesList.addLink(url);
					this.notifyPageVisitedListener(url, doc);
					
					//System.out.println(url);
					
					if (this.savePath != null)
					{
						String path = (this.savePath + System.currentTimeMillis() + ".xml");
						this.persistPage( doc, path );
						this.notifyPageDownloadedListener(url, path);
					}
					
					Elements links = doc.select("a[href]");
					for(Element link: links) {
						if(link.attr("href").startsWith(this.baseUrl)) {
							this.mPagesQueue.addPage( link.attr("abs:href") );
						}
					}
					
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		} while (this.mPagesQueue.isEmpty() == false);
	}
	
	private void persistPage(Document document, String path)
	{
		try
		{		
			StringBuilder xmlContent = new StringBuilder();        
	        xmlContent.append(document.body().html());
	
	        FileWriter fileWriter = new FileWriter(path);
	        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	        bufferedWriter.write(xmlContent.toString());
	        bufferedWriter.close();	        
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setPageVisitedListener(PageVisitedListener p)
	{
		this.mPageVisitedListener = p;
	}
	
	public void setPageDownloadedListener(PageDownloadedListener p)
	{
		this.mPageDownloadedListener = p;
	}
	
	public void unsetPageVisitedListener()
	{
		this.mPageVisitedListener = null;
	}
	
	public void unsetPageDownloadedListener()
	{
		this.mPageDownloadedListener = null;
	}
	
	public void notifyPageDownloadedListener(String url, String path)
	{
		if (this.mPageDownloadedListener != null)
			this.mPageDownloadedListener.newPageDownloaded(url, path);
	}
	
	public void notifyPageVisitedListener(String url, Document document)
	{
		if (this.mPageVisitedListener != null)
			this.mPageVisitedListener.pageVisited(url, document);
	}
	
	public void addFilter(IUrlFilter filter)
	{
		this.filters.add(filter);
	}
	
	public void clearFilters()
	{
		this.filters.clear();
	}
	
	public boolean filter(String url)
	{
		for(IUrlFilter f : this.filters)
			if (f.filter(url))
				return true;
		
		return false;
	}
}
