package com.nhlstenden.controllers;

import com.nhlstenden.middelware.MyArrayList;
import java.util.List;

public class BayerMooreStringSearch {
    private static final int NO_OF_CHARS = 256;

    private static int max(int a, int b) {
        return Math.max(a, b);
    }

    private static void badCharHeuristic(char[] pattern, int size, int[] badchar) {
        for (int i = 0; i < NO_OF_CHARS; i++) {
            badchar[i] = -1;
        }
        for (int i = 0; i < size; i++) {
            badchar[pattern[i]] = i;
        }
    }

    public static List<Integer> search(String text, String pattern) {
        List<Integer> occurrences = new MyArrayList<>();
        int m = pattern.length();
        int n = text.length();

        if (m == 0 || n == 0 || m > n) return occurrences;

        int[] badchar = new int[NO_OF_CHARS];
        badCharHeuristic(pattern.toCharArray(), m, badchar);

        int s = 0;
        while (s <= (n - m)) {
            int j = m - 1;
            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j)) {
                j--;
            }

            if (j < 0) {
                occurrences.add(s);
                s += (s + m < n) ? m - badchar[text.charAt(s + m)] : 1;
            } else {
                s += max(1, j - badchar[text.charAt(s + j)]);
            }
        }
        return occurrences;
    }
}
