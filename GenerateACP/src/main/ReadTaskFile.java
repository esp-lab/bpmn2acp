package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ReadTaskFile {

	public static boolean IsOperator(char c) {
		boolean isOperator = false;
		if (c == '>' || c == '<' || c == '=')
			isOperator = true;
		return isOperator;
	}

	public static String ParseOperator(char cCurrent, char cNext) {
		String result = "";

		if (cCurrent == '>' && cNext == '=') {
			result = "EqualOrBiggerThan";
		} else if (cCurrent == '<' && cNext == '=') {
			result = "EqualOrSmallerThan";
		} else if (cCurrent == '>') {
			result = "BiggerThan";
		} else if (cCurrent == '<') {
			result = "SmallerThan";
		} else if (cCurrent == '=') {
			result = "Equal";
		}

		return result;
	}

	@SuppressWarnings("deprecation")
	public static String ParseTextToOperatorExpression(String text) {
		StringBuilder result = new StringBuilder();
		int count = text.length();
		boolean isSkipOperator = false;
		for (int i = 0; i < count; i++) {
			char c = text.charAt(i);
			if (Character.isLetter(c) || Character.isDigit(c))
				result.append(c);
			else if (Character.isSpace(c))
				continue;
			else if (c == '.') {
				result.append("At");
			} else {
				if (isSkipOperator) {
					isSkipOperator = false;
					continue;
				} else {
					//System.out.println(text.length());
					result.append(ParseOperator(c,(((i+1)<count)? text.charAt(i + 1):' ')));
					if (i+1<count && IsOperator(text.charAt(i + 1)))
						isSkipOperator = true;
				}
			}
		}
		return result.toString();
	}

	public static String ParseTextToDefinedExpression(String text) {
		int startIdx = 8;
		int endIdx = text.length() - 1;
		StringBuilder result = new StringBuilder();
		for (int i = startIdx; i < endIdx; i++) {
			char c = text.charAt(i);
			if (Character.isLetter(c))
				result.append(c);
			else if (c == '.')
				result.append("At");
		}
		return result.toString();
	}

	public static String ParseTextToUndefinedExpression(String text) {
		int startIdx = 10;
		int endIdx = text.length() - 1;
		StringBuilder result = new StringBuilder();
		result.append('~');
		for (int i = startIdx; i < endIdx; i++) {
			char c = text.charAt(i);
			if (Character.isLetter(c))
				result.append(c);
			else if (c == '.')
				result.append("At");
		}
		return result.toString();
	}

	public static String ParseTextToInstateExpression(String text) {
		int startIdx = 8;
		int endIdx = text.length() - 1;
		StringBuilder result = new StringBuilder();
		for (int i = startIdx; i < endIdx; i++) {
			char c = text.charAt(i);
			if (Character.isLetter(c))
				result.append(c);
			else if (c == ',')
				result.append("Is");
		}
		return result.toString();
	}

	public static void ParsePrecondition(BusinessTask busTask) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(busTask.name + ".txt"));
		try {
			String line = br.readLine();
			boolean beginParse = false;
			while (line != null) {
				if (line.contains("<precondition>")) {
					line = br.readLine();
					beginParse = true;
					continue;
				}
				if (line.contains("</precondition>"))
					break;
				if (beginParse) {
					if (line.contains("undefined(")) {
						busTask.addLiteral2Pre(ParseTextToUndefinedExpression(line));
					} else if (line.contains("defined(")) {
						busTask.addLiteral2Pre(ParseTextToDefinedExpression(line));
						System.out.println(ParseTextToDefinedExpression(line));
					} else if (line.contains("instate(")) {
						busTask.addLiteral2Pre(ParseTextToInstateExpression(line));
						System.out.println(ParseTextToInstateExpression(line));
					} else {
						busTask.addLiteral2Pre(ParseTextToOperatorExpression(line));
						System.out.println(ParseTextToOperatorExpression(line));
					}
				}
				line = br.readLine();
			}
		} finally {

		}
	}

	public static void ParsePostcondition(BusinessTask busTask) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(busTask.name + ".txt"));
		try {
			String line = br.readLine();
			boolean beginParse = false;
			while (line != null) {
				if (line.contains("<postcondition>")) {
					line = br.readLine();
					beginParse = true;
					continue;
				}
				if (line.contains("</postcondition>"))
					break;
				if (beginParse) {
					if (line.contains("undefined(")) {
						busTask.addLiteral2Post(ParseTextToUndefinedExpression(line));
					} else if (line.contains("defined(")) {
						busTask.addLiteral2Post(ParseTextToDefinedExpression(line));
						// System.out.println(ParseTextToDefinedExpression(line));
					} else if (line.contains("instate(")) {
						busTask.addLiteral2Post(ParseTextToInstateExpression(line));
						// System.out.println(ParseTextToInstateExpression(line));
					} else {
						busTask.addLiteral2Post(ParseTextToOperatorExpression(line));
						// System.out.println(ParseTextToOperatorExpression(line));
					}
				}
				line = br.readLine();
			}
		} finally {

		}
	}

	public static void ParseArtifact(BusinessTask busTask, List<Artifact> allArtifact) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(busTask.name + ".txt"));
		try {
			String line = br.readLine();
			boolean beginParse = false;
			while (line != null) {
				if (line.contains("<artifact>")) {
					line = br.readLine();
					beginParse = true;
					continue;
				}
				if (line.contains("</artifact>"))
					break;
				if (beginParse) {
					line = line.replace(" ", "");
					String[] arr = line.split(",");
					busTask.artifacts = new ArrayList<Artifact>();
					for (int i = 0; i < arr.length; i++) {
						for (Artifact a : allArtifact) {
							if (a.getName().equals(arr[i])) {
								busTask.artifacts.add(a);
							}
						}
					}
				}
				line = br.readLine();
			}
		} finally {

		}
	}
	private static Expression ParseConditionToExpression(String s) {
		s = s.replace(" ", "");
		s=s.replace("\"", "");
		StringBuilder preArtifact = new StringBuilder();
		StringBuilder preAttribute = new StringBuilder();
		StringBuilder operation = new StringBuilder();
		StringBuilder postArtifact = new StringBuilder();
		StringBuilder postAttribute = new StringBuilder();
		StringBuilder number = new StringBuilder();
		int idxPreArtifact = 0;
		while(s.charAt(idxPreArtifact) != '.'){
			preArtifact.append(s.charAt(idxPreArtifact));
			idxPreArtifact++;
		}
		
		int idxPreAttribute = idxPreArtifact + 1;
		while(Character.isLetter(s.charAt(idxPreAttribute))){
			preAttribute.append(s.charAt(idxPreAttribute));
			idxPreAttribute++;
		}
		
		int idxOperation = idxPreAttribute;
		//while(IsOperator(s.charAt(idxOperation))) {
			//idxOperation++;
		//}
		operation.append(ParseOperator(s.charAt(idxOperation), s.charAt(idxOperation + 1)));
		
		String remain = "";
		boolean hasNumber = true;
		if(IsOperator(s.charAt(idxOperation + 1))) {
			remain = s.substring(idxOperation + 2);
		} else remain = s.substring(idxOperation + 1);
		if(remain.contains(".")) {
			hasNumber = false;
			String[] temp = remain.split("\\.");
			postArtifact.append(temp[0]);
			postAttribute.append(temp[1]);
		}else {
			number.append(remain);
		}
		if(hasNumber)
			return new Expression(preArtifact.toString(), preAttribute.toString(), operation.toString(), null, null, number.toString());
		else
			return new Expression(preArtifact.toString(), preAttribute.toString(), operation.toString(), postArtifact.toString(), postAttribute.toString(), null);
	}
	public static void ParseDRcondition(List<Expression> drCond) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("drCondition" + ".txt"));
		try {
			String line = br.readLine();
			boolean beginParse = false;
			while (line != null) {
				if (line.contains("<drcondition>")) {
					line = br.readLine();
					beginParse = true;
					continue;
				}
				if (line.contains("</drcondition>"))
					break;
				if (beginParse) {
					drCond.add(ParseConditionToExpression(line));
						// System.out.println(ParseTextToOperatorExpression(line));
					
				}
				line = br.readLine();
			}
		} finally {

		}
	}
	public static void ParseDRconsequence(List<Expression> drCons) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("drConsequence" + ".txt"));
		
		try {
			String line = br.readLine();
			boolean beginParse = false;
			while (line != null) {
				if (line.contains("<drconsequence>")) {
					line = br.readLine();
					beginParse = true;
					continue;
				}
				if (line.contains("</drconsequence>"))
					break;
				if (beginParse) {
					drCons.add(ParseConditionToExpression(line));
						// System.out.println(ParseTextToOperatorExpression(line));
					
				}
				line = br.readLine();
			}
		} finally {

		}
	}
	
	public static void ParsePostCommacondition(BusinessTask busTask) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(busTask.name + ".txt"));
		try {
			String line = br.readLine();
			boolean beginParse = false;
			while (line != null) {
				if (line.contains("<postcondition>")) {
					line = br.readLine();
					beginParse = true;
					continue;
				}
				if (line.contains("</postcondition>"))
					break;
				if (beginParse) {
					if (line.contains("undefined(")) {
						busTask.addLiteral2PostComma(ParseTextToUndefinedExpression(line));
					} else if (line.contains("defined(")) {
						busTask.addLiteral2PostComma(ParseTextToDefinedExpression(line));
						// System.out.println(ParseTextToDefinedExpression(line));
					} else if (line.contains("instate(")) {
						//busTask.addLiteral2Post(ParseTextToInstateExpression(line));
						// System.out.println(ParseTextToInstateExpression(line));
						
					} else {
						busTask.addLiteral2PostComma(ParseTextToOperatorExpression(line));
						// System.out.println(ParseTextToOperatorExpression(line));
					}
				}
				line = br.readLine();
			}
		} finally {

		}
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		char c = '8';
		BusinessTask t = new BusinessTask();
		t.name = "file";
		// ParsePrecondition(t);
		// ParseArtifact(t);
		try {
			PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
			writer.println("The first line");
			writer.println("The second line");
			writer.close();
		} catch (Exception e) {
			// do something
		}
		// for(String s : t.getpre_cond()){
		// System.out.println(s);
		// }
		System.out.print(Character.isDigit(c));
	}

}
