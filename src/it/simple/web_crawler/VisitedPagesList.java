package it.simple.web_crawler;

import java.util.ArrayList;

public class VisitedPagesList {
	private ArrayList<String> visitedList = null;
	
	public VisitedPagesList()
	{
		super();
		this.visitedList = new ArrayList<String>();
	}
	
	public boolean isVisited(String url) {
		return this.visitedList.contains(url);
	}
	
	public void addLink(String url) {
		this.visitedList.add(url);
	}
	
	public void clear()
	{
		this.visitedList.clear();
	}

}
