package com.nhlstenden.controllers;

import com.nhlstenden.middelware.MyArrayList;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.Queue;

public class SharedData<T>
{
	private MyArrayList<T> sharedArray;

	public SharedData(MyArrayList<T> data)
	{
		this.sharedArray = data;
	}

	public MyArrayList<T> getSharedArray()
	{
		return this.sharedArray;
	}

	public void setSharedArray(MyArrayList<T> sharedArray)
	{
		this.sharedArray = sharedArray;
	}

	public MyArrayList<T> asList()
	{
		return sharedArray;
	}

	public Queue<T> asQueue()
	{
		return new LinkedList<>(sharedArray);
	}

	public int getSize()
	{
		return this.sharedArray.size();

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
// TODO: do we use java convention or class convention