package com.nhlstenden.controllers;

import com.nhlstenden.middelware.MyArrayList;

import java.util.ArrayList;
import java.util.List;

public class BayerMooreStringSearch
{
	static int NO_OF_CHARS = 256;

	//get a max of two integers
	private static int max(int a, int b) {return (a > b) ? a : b; }

	private static void badCharHeuristic(char[] pattern, int size, int badchar[]){
		for (int i = 0; i < NO_OF_CHARS; i ++)
			badchar[i] = -1;

		for (int i = 0; i < size; i ++)
			badchar[pattern[i]] = i;
	}


	public static List<Integer> search(String text, String pattern) {
		int m = pattern.length();
		int n = text.length();
		List<Integer> occurrences = new MyArrayList<>();

		int[] badchar = new int[NO_OF_CHARS];
		badCharHeuristic(pattern.toCharArray(), m, badchar);

		int s = 0; // Shift of the pattern
		while (s <= (n - m)) {
			int j = m - 1;

			while (j >= 0 && pattern.charAt(j) == text.charAt(s + j))
				j--;

			if (j < 0) {
				occurrences.add(s);
				s += (s + m < n) ? m - badchar[text.charAt(Math.min(s + m, n - 1))] : 1;
			} else {
				s += max(1, j - badchar[text.charAt(s + j)]);
			}
		}
		return occurrences;
	}
}
