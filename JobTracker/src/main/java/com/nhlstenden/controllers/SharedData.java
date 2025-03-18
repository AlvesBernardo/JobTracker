package com.nhlstenden.controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SharedData<T>
{
	private ArrayList<T> sharedArray;

	public SharedData(ArrayList<T> data)
	{
		this.sharedArray = new ArrayList<>();
	}

	public ArrayList<T> getSharedArray()
	{
		return this.sharedArray;
	}

	public void setSharedArray(ArrayList<T> sharedArray)
	{
		this.sharedArray = sharedArray;
	}

	public ArrayList<T> asList() {
		return sharedArray;
	}

	public Queue<T> asQueue() {
		return new LinkedList<>(sharedArray);
	}

	public String getString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		int lenght = sharedArray.size();

		for (int i = 0; i < lenght; i++)
		{
			T elements = sharedArray.get(i);
			sb.append(elements.toString());
			if (i < lenght - 1)
			{
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
