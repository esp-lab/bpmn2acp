package main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Document;

import org.jaxen.expr.Expr;

import com.sun.glass.ui.TouchInputSupport;
import com.sun.swing.internal.plaf.metal.resources.metal_zh_TW;

import javafx.scene.control.Labeled;
import jdk.nashorn.internal.ir.LiteralNode.ArrayLiteralNode.ArrayUnit;

import java.awt.Component;
import java.sql.Date;
import java.util.ArrayList;

import javax.management.remote.SubjectDelegationPermission;
import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

import orbital.signe;
import orbital.logic.imp.*;
import orbital.logic.sign.*;
import orbital.moon.logic.ClassicalLogic;

import orbital.logic.imp.Formula;
import orbital.logic.imp.Logic;

public class Algorithm {
	public static Component mainScreen;
	private static Object lock = new Object();

	public static Formula makeFormulaFromArrayList(ArrayList<String> arrStr) {
		String exp = "";
		boolean firstTime = true;
		for (String element : arrStr) {
			if (firstTime)
				exp += element;
			else
				exp += '&' + element;
			firstTime = false;
		}

		try {
			System.out.println(exp);
			return (Formula) logic.createExpression(exp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void GenDom(List<Expression> listCond, int[] Label, BusinessTask[] tList, int indexTarget)
			throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		String FormulaStr = "";
		ArrayList<String> listArtifact = new ArrayList<String>();
		for (int i : Label) {
			for (Artifact art : tList[i].artifacts) {
				if (!listArtifact.contains(art.getName()))
					listArtifact.add(art.getName());
			}
			if (i == indexTarget)
				break;
		}
		for (Expression exp : listCond) {
			if (exp.number == null && listArtifact.contains(exp.preArtifact)
					&& listArtifact.contains(exp.postArtifact)) {
				result.add(exp.toString());
			} else if (exp.number != null) {
				if (listArtifact.contains(exp.preArtifact))
					result.add(exp.toString());
			}
		}
		tList[indexTarget].dom_ = result;
		boolean first = true;
		for (String s : result) {
			if (first) {
				FormulaStr += s;
				first = false;
			} else
				FormulaStr += " & " + s;
		}
		System.out.println(FormulaStr);
		tList[indexTarget].dom = (Formula) new ClassicalLogic().createExpression(FormulaStr);
	}

	public static boolean inconsistant(Formula f1, Formula f2, Formula[] f2Collection) {
		Formula fCombination = f1;
		Formula[] ax = {};
		for (Formula formula : f2Collection) {
			if (fCombination != null) {
				if (formula != null) {
					fCombination.and(formula);
				}
			} else {
				if (formula != null) {
					fCombination = formula;
				}
			}

		}
		return logic.inference().infer(ax, fCombination.not());
	}

	public static boolean entail(Formula[] fCollection, Formula f0) throws Exception {
		try {
			boolean deduce = logic.inference().infer(fCollection, f0);
			// System.out.println("A entail B? " + deduce);
			return deduce;
		} catch (Exception e) {
			return false;
		}
	}

	public static Formula acc(Formula f1, ArrayList<String> arrf1, Formula f2, Formula[] f2Collection)
			throws Exception {
		ArrayList<String> arr = arrf1;
		if (inconsistant(f1, f2, f2Collection)) {
			Formula strFormula;
			for (String str : arrf1) {
				strFormula = (Formula) logic.createExpression(str);
				if (inconsistant(strFormula, f2, f2Collection)) {
					arr.remove(str);
				}
			}
		}
		// f1 = makeFormulaFromArrayList(arr);

		if (f1 != null) {
			return f2.and(f1);
		} else {
			return f2;
		}
	}

	static public Logic logic = new ClassicalLogic();

	static public String elimination(List<Expression> listCond, List<Expression> listConsqnt, int[] Label,
			BusinessTask[] tList) throws Exception {
		// for (int i = 0; i < Label.length; i++) {
		// Label[i] = Label[i] - 1;
		// }
		String result = "";
		long startedTime = System.currentTimeMillis();

		Formula Cond = (listCond.size() != 0) ? (Formula) logic.createExpression(Expression.listToString(listCond))
				: null;
		Formula Consqnt = (listConsqnt.size() != 0)
				? (Formula) logic.createExpression(Expression.listToString(listConsqnt)) : null;
		List<Formula> drConsqnt = Expression.ConvertToDataminingRule(listConsqnt);
		drConsqnt.add(Consqnt);
		Formula[] fConsqntArray = drConsqnt.toArray(new Formula[drConsqnt.size()]);
		List<Formula> drCond = Expression.ConvertToDataminingRule(listCond);
		Formula[] drCondArray = drCond.toArray(new Formula[drCond.size()]);

		int[] lstTarget = new int[tList.length];
		int[] iReduce = new int[tList.length];
		ArrayList lstRemain = new ArrayList();
		int index = 0;
		Formula facc;
		int[] lstExcluded = new int[tList.length];
		boolean flag = true;
		BusinessTask[] tRemain = new BusinessTask[tList.length];

		for (int i : Label) {
			Formula[] preCondArray = new Formula[drCondArray.length + 1];
			for (int j = 0; j < preCondArray.length; j++) {
				if (j == 0) {
					preCondArray[j] = tList[i].getPreCondition();
				} else {
					preCondArray[j] = drCondArray[j - 1];
				}
			}
			facc = acc(tList[i].getInstance(), tList[i].getinstance_(), tList[i].getPreCondition(), preCondArray);
			// facc = acc(new Formula[]{tList[i].getInstance(), f3, f4, f5},
			// tList[i].getInstance(), tList[i].getPreCondition());
			if (entail(new Formula[] { facc }, Cond) && entail(fConsqntArray, tList[i].getPostComma())) {
				lstTarget[index] = i;
				lstRemain.add(i);
				index++;
			}
		}
		if (lstTarget[0] > 0) {
			for (int i : lstTarget) {
				if (i > 0) {
					index = 1;
					for (int k : lstTarget) {
						if ((k > 0) && (k != i)) {
							lstExcluded[index] = k;
							index++;
						}
					}
					index = 1;
					for (int j : lstExcluded) {
						if (j > 0) {
							if (entail(new Formula[] { tList[i].getPostCondition() }, tList[j].getPostCondition())) {
								flag = true;
								for (int m : iReduce) {
									if ((m > 0) && (j == m)) {
										flag = false;
									}
								}
								if (flag == true) {
									Eliminate(j, tList, Label);
									System.out.println(tList[j].name + " task is eliminated.");
									iReduce[index] = j;
									lstRemain.remove(j);
									index++;
								}
							}
						}
					}

				}
			}
			System.out.println(
					"These following tasks are remained but some tasks will be reduced a part of, based on the user choices.");
			for (int i = 0; i < lstRemain.size(); i++) {
				index = (Integer) lstRemain.get(i);
				tRemain[i] = tList[index];
				result += tList[index].name + ", ";
			}
			// System.out.println("In this case, we will reduce a part of
			// Service Booked Vehilces task.");
			ReduceTask(tRemain, tList, Label);
		}
		long processingTime = System.currentTimeMillis() - startedTime;
		System.out.println("Processing time is: " + processingTime + " milliseconds.");
		return result;
	}

	public static void ReduceTask(BusinessTask[] tRemain, BusinessTask[] tList, int[] label) throws Exception {
		JOptionPane.showMessageDialog(mainScreen, "Some tasks need to be Reduced.", "Warning",
				JOptionPane.WARNING_MESSAGE);
//		final ReduceTaskChooser input = new ReduceTaskChooser(tRemain);
//		input.start();
		ReduceTaskDialog input = new ReduceTaskDialog(tRemain,(JFrame) mainScreen, true);
		input.setVisible(true);
		

	}

	public static void Eliminate(int iEliminated, BusinessTask[] tList, int[] label) throws Exception {
		BusinessTask eTask = tList[iEliminated];
		JOptionPane.showMessageDialog(mainScreen, "Task " + eTask.name + " was Eliminated.", "Warning",
				JOptionPane.WARNING_MESSAGE);
	}

	public static String reordering(List<Expression> listCond, List<Expression> listConsqnt, int[] Label,
			BusinessTask[] tList,List<Formula> br) throws Exception {
		String result = "";
		// System.out.println("Started time: " + System.currentTimeMillis());
		// for (int i = 0; i < Label.length; i++) {
		// Label[i] = Label[i] - 1;
		// }
		long startedTime = System.currentTimeMillis();

		Formula Cond = (Formula) logic.createExpression(Expression.listToString(listCond));
		Formula Consqnt = (Formula) logic.createExpression(Expression.listToString(listConsqnt));
		//List<Formula> br = new ArrayList<>();
		//ContractAtPostRentAssessmentEqualAvoided => ContractAtApprovalEqualDenied"
//		for (int i =0;i<brStr.length;i++){
//			br.add((Formula) logic
//					.createExpression(brStr[i]));
//		}
		List<Formula> drConsqnt = Expression.ConvertToDataminingRule(listConsqnt);
		List<Formula> collection = new ArrayList<Formula>();
		collection.addAll(br);
		collection.addAll(drConsqnt);
		collection.addAll(GenDrConditionFromBusinessRule(br));
		collection.add(Consqnt);
		Formula[] fCollection = collection.toArray(new Formula[br.size() + drConsqnt.size() + 1]);

		List<Formula> drCond = Expression.ConvertToDataminingRule(listCond);

		Formula facc1, facc2;
		BusinessTask tTarget = new BusinessTask();
		int index = 0;
		for (int i : Label) {
			if (entail(fCollection, tList[i].getPostComma())) {
				tTarget = tList[i];
				index = i;
				break;
			}
		}
		GenDom(listCond, Label, tList, index);
		if (index > 0) {
			int k = 0;
			while (Label[k] != index) {
				if (k == 0){
					k++;
					continue;
				}
				int i = Label[k];
				facc1 = acc((tList[Label[k - 1]]).getPostCondition(), tList[Label[k - 1]].getpost_cond(), tList[i].getPostCondition(),
						(new Formula[] { tList[i].getPostCondition() }));
//				facc1 = acc((tList[i -1]).getPostCondition(), tList[i - 1].getpost_cond(), tList[i].getPostCondition(),
//						(new Formula[] { tList[i].getPostCondition() }));
				Formula[] fCond = drCond.toArray(new Formula[drCond.size() + 1]);
				fCond[drCond.size()] = facc1;
				facc2 = acc(tTarget.getDom(), tTarget.getdom_(), facc1, fCond);
				if (entail(new Formula[] { facc2 }, Cond)) {
					result += tTarget.name;
					result += "-" + tList[i].name;
					DecomposeAndReorderTask(tTarget);
					break;
				}
				k++;
			}
		}
		long processingTime = System.currentTimeMillis() - startedTime;
		System.out.println("result:" + result);
		System.out.println("Processing time is: " + processingTime + " milliseconds.");
		return result;
	}

	public static void DecomposeAndReorderTask(BusinessTask task) {

	}

	public static List<Formula> GenDrConditionFromBusinessRule(List<Formula> br) {
		List<Formula> dr = new ArrayList<>();
		String[] operatorList = { "EqualOrBiggerThan", "EqualOrSmallerThan", "BiggerThan", "SmallerThan", "Equal" };
		List<String> attrList = new ArrayList<>();
		for (Formula i : br) {
			String[] tmp = i.toString().split(" -> ");
			if (tmp.length == 2) {
				attrList.add(tmp[0]);
				attrList.add(tmp[1]);
			}
		}
		for (String k : attrList) {
			for (int i = 0; i < operatorList.length; i++) {
				String[] Attrs = k.split(operatorList[i]);
				if (Attrs.length == 2) {
					try {
						dr.add((Formula) Algorithm.logic.createExpression(k + " => " + Attrs[0]));
						if (Attrs[1].contains("At")) {
							dr.add((Formula) Algorithm.logic.createExpression(k + " => " + Attrs[1]));
						}
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}

			}
		}
		return dr;
	}

}
