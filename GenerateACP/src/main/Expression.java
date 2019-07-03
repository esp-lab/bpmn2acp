package main;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

import orbital.logic.imp.Formula;
import orbital.logic.imp.Logic;
import orbital.moon.logic.ClassicalLogic;

public class Expression {
	String preArtifact;
	String preAttribute;
	String operation;
	String postArtifact;
	String postAttribute;
	String number;


	public Expression(String preArtifact, String preAttribute, String operation, String postArtifact,
			String postAttribute, String number) {
		this.preArtifact = preArtifact;
		this.preAttribute = preAttribute;
		this.operation = operation;
		this.postArtifact = postArtifact;
		this.postAttribute = postAttribute;
		this.number = number;
	}

	@Override
	public String toString() {
		String result;
		if (number == null) {
			result = preArtifact + "At" + preAttribute + operation + postArtifact + "At" + postAttribute;
		} else {
			result = preArtifact + "At" + preAttribute + operation + number;
		}
		return result;
	}

	public static String listToString(List<Expression> param) {
		String result = "";
		for (int i = 0; i < param.size(); i++) {
			if (i == 0) {
				result += param.get(i).toString();
			} else {
				result += " & " + param.get(i).toString();
			}
		}
		return result;
	}
	public static ArrayList<Formula> ConvertToDataminingRule(List<Expression> source) throws IllegalArgumentException, ParseException
	{
		Logic logic = new ClassicalLogic();
		ArrayList<Formula> result = new ArrayList<Formula>();
		for(Expression ex : source) {
			String temp = ex.toString() + " => ";
			if (ex.number == null) {
				temp = ex.preArtifact + "At" + ex.preAttribute + " & " + ex.postArtifact + "At" + ex.postAttribute;
				//result = preArtifact +"At" + preAttribute + operation + postArtifact +"At" + postAttribute;
			}
			else {
				temp += ex.preArtifact +"At" + ex.preAttribute;
			}
			try {
				result.add((Formula) logic.createExpression(temp));
			} catch (orbital.logic.sign.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

}
