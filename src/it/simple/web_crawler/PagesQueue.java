package it.simple.web_crawler;

import java.util.ArrayList;

public class PagesQueue {
	private ArrayList<String> pagesList = null;
	
	public PagesQueue()
	{
		super();
		this.pagesList = new ArrayList<String>();
	}
	
	public boolean isEmpty()
	{
		return this.pagesList.isEmpty();
	}
	
	public void addPage(String url) {		
		this.pagesList.add(url);		
	}
	
	public String getNextPage() {
		return this.pagesList.remove(0);
	}
	
	public void clear()
	{
		this.pagesList.clear();
	}
}
