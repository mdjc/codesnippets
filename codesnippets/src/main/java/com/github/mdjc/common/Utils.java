package com.github.mdjc.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
	public static String emptyWhenNull(String s) {
		return s == null ? "" : s;
	}

	public static String readFile(String filePath) throws IOException, FileNotFoundException {
		StringBuilder script = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath));) {
			String line;

			while ((line = br.readLine()) != null) {
				script.append(line);
			}
		}

		return script.toString();
	}
}
