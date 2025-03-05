package com.nhlstenden.controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SharedData
{
	private ArrayList<Object> sharedArray;

	public SharedData(ArrayList<Object> data)
	{
		this.sharedArray = new ArrayList<>();
	}

	public ArrayList<Object> getSharedArray()
	{
		return this.sharedArray;
	}

	public void setSharedArray(ArrayList<Object> sharedArray)
	{
		this.sharedArray = sharedArray;
	}

	public ArrayList<Object> asList() {
		return sharedArray;
	}

	public Queue<Object> asQueue() {
		return new LinkedList<>(sharedArray);
	}
}
