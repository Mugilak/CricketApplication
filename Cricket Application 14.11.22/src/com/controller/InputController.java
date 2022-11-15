package com.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class InputController {

	public Queue<List<String>> getInput() {
		String path = "C:\\Users\\91638\\eclipse-workspace\\Cricket Application 14.11.22\\src\\com\\model\\input.txt",
				line = "";
		Queue<List<String>> input = new LinkedList<>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(path));
			line = reader.readLine();
			while (line != null) {
				input.offer(Arrays.asList(line.split(",")));
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}

}
