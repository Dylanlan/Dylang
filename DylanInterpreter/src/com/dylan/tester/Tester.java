package com.dylan.tester;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Tester {
	public static final String interpreter = "java -cp antlr-3.3-complete.jar;bin/ com.dylan.interpreter.Main";
	public static final boolean debug = false;
	public static String singleTestName = "";
	
	public static void main(String[] args) {
		long time = System.currentTimeMillis();

		try {
			if (singleTestName.length() > 0) {
				int result = Tester.compare(singleTestName);
				if (result == 0) {
					System.out.print(singleTestName + " - Ok\n");
				}
				else {
					System.out.print(singleTestName + " - Failed\n");
				}
			}
			else {
				Tester.testAll();
			}
		}
		catch(Exception e) {
			System.out.println("There was a problem running the tests: " + e.getMessage());
		}

		time = System.currentTimeMillis() - time;
		System.out.println("\nTesting duration: " + time + "ms");
	}

	private static void testAll() {
		List <String> testNames = new ArrayList<String>();
		List <Integer> testResults = new ArrayList<Integer>();
		int numFailed = 0;

		try {
			File folder = new File("Tests");
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					String fileName = listOfFiles[i].getName();
					if (fileName.contains(".dyl")) {
						testNames.add(fileName);
					}
				}
			}
		}
		catch (Exception e) {
			System.out.println("Error getting all tests: " + e.getMessage());
		}

		for(int i = 0; i < testNames.size(); i++){
			String testName = testNames.get(i);
			System.out.print(testName + ":");
			int result = Tester.compare(testName);
			if (result == 0) {
				System.out.print(" - Ok\n");
			}
			else {
				System.out.print(" - Failed\n");
			}
			testResults.add(result);
		}

		//System.out.println("Failed tests:\n\n");
		for(int i = 0; i < testNames.size(); i++){
			if(testResults.get(i) != 0){
				//System.out.println(tests.get(i) + " - Failed");
				numFailed++;
			}
		}
		
		System.out.println("\n-----Results-----");
		int total = testNames.size();
		int passed = total - numFailed;
		System.out.println("Passed: " + passed + " / " + total);
		System.out.println("Failed: " + numFailed + " / " + total);
	}

	private static int compare(String testName) {
		if (debug) System.out.println("\n\nAbout to run test: " + testName);
		
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(Tester.interpreter + " Tests/" + testName);		}
		catch (IOException e) {
			System.out.println("IO Exception trying to run interpreter: " + e.getMessage());
		}

		String fileName = "Tests/" + testName.replace(".dyl", "") + ".exp";

		File output_file = new File(fileName);
		if (!output_file.isFile()) {
			if (debug) System.out.println("Couldn't find expected output file: " + fileName);
			p.destroy();
			return p.exitValue();
		}
		else if (debug) System.out.println("Found expected output file: " + fileName);

		String output_line = null;
		String correct_line = null;
		BufferedReader interp_output = new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader correct_output = null;
		
		try {
			correct_output = new BufferedReader(new FileReader(output_file));
		}
		catch (IOException e) {
			System.out.println("IO Exception reading output file: " + e.getMessage());
		}
		
		boolean correct = true;
		try {
			while (((output_line = interp_output.readLine()) != null) |
					((correct_line = correct_output.readLine()) != null)) {
				
				if (output_line == null) {
					if (debug) System.out.println("Interpreter is missing a line: " + correct_line);
					correct = false;
					break;
				}
								
				if (correct_line == null) {
					if (debug) System.out.println("Interpreter has extra line: " + output_line);
					correct = false;
					break;
				}
								
				if (!output_line.equals(correct_line)) {
					if (debug) System.out.println("Different lines:\nInterpreter: |" + output_line + "|\nCorrect: |" + correct_line + "|");
					correct = false;
					break;
				}
			}
		}
		catch (IOException e) {
			System.out.println("IO Exception comparing output: " + e.getMessage());
		}

		try {
			correct_output.close();
			interp_output.close();
		}
		catch (Exception e) {
			System.out.println("Exception closing buffered reader: " + e.getMessage());
		}

		p.destroy();
		return correct ? 0 : -1;
	}
}
