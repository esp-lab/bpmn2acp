package main;

import java.awt.print.Printable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.classfile.Attribute;

import orbital.logic.imp.*;
import orbital.logic.sign.ParseException;
import orbital.logic.sign.Symbol;

public class BusinessTask{
	public String name;
	public String toString(){
		return name;
	}
	protected ArrayList<String> pre_cond = new ArrayList<String>();
	protected ArrayList<String> post_cond = new ArrayList<String>();
	protected ArrayList<String> post_comma = new ArrayList<String>();
	protected ArrayList<String> dom_ = new ArrayList<String>();
	protected ArrayList<String> instance_ = new ArrayList<String>();

	private Formula preCondition;
	private Formula postCondition;
	private Formula postComma;
	public void setDom(Formula dom) {
		this.dom = dom;
	}
	protected Formula dom;
	private Formula instance;
	
	public String service;
	public ArrayList<Artifact> artifacts;
	public void reducePostCond(int post) throws IllegalArgumentException, ParseException{
		String tmp = post_cond.get(post);
		post_cond.remove(post);
		for (int i=0;i<post_comma.size();i++){
			if (tmp.equals(post_comma.get(i))){
				post_comma.remove(i);
				break;
			}
		}
		genFormula();
	}
    public BusinessTask() throws Exception {
        
    }
    public void genFormula() throws IllegalArgumentException, ParseException{
    	String preString = "";
    	String postString ="";
    	String commaString ="";
    	for (String pre:pre_cond){
    		if (pre_cond.get(0).equals(pre)){
    			preString+=pre;
    		}else{
    			preString+=" & "+pre;
    		}
    	}
    	for (String post:post_cond){
    		if (post_cond.get(0).equals(post)){
    			postString+=post;
    		}else{
    			postString+=" & "+post;
    		}
    	}
    	for (String comma:post_comma){
    		if (post_comma.get(0).equals(comma)){
    			commaString+=comma;
    		}else{
    			commaString+=" & "+comma;
    		}
    	}
    	
    	this.setPreCondition((preString.equals(""))?null:(Formula) Algorithm.logic.createExpression(preString));
    	this.setPostCondition((postString.equals(""))?null:(Formula) Algorithm.logic.createExpression(postString));
    	this.setPostComma((commaString.equals(""))?null:(Formula) Algorithm.logic.createExpression(commaString));
    }
    public void genInstance(List<Expression> listCond) {
		ArrayList<String> instance = new ArrayList<String>();
		String[] listArtifact = Artifact.toArrayString(this.artifacts);
		for(String artifactName : listArtifact) {
			for(Expression condition : listCond) {
				if(condition.preArtifact.equals(artifactName) || (condition.postArtifact !=null && condition.postArtifact.equals(artifactName))) {
					instance.add(condition.toString());
				}
			}
		}
		instance_ = instance;
		String instanceStr = "";
		for (int i =0 ; i <instance.size();i++){
			if (i == 0){
				instanceStr += instance.get(i);
			}
			else {
				instanceStr += " & " + instance.get(i);
			}
		}
		try {
			if (!instanceStr.equals("")){
				this.setInstance((Formula) Algorithm.logic.createExpression(instanceStr));
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    static public Formula makeOrbitalFormula(Logic lo, ArrayList<String> arrStr) {
    	String exp = "";
    	boolean firstTime = true;
    	for (String element: arrStr) {
    		if (firstTime)
    			exp += element;
    		else	
    			exp += '&' + element;
    		firstTime = false;
    	}
  
    	try {
			return (Formula) lo.createExpression(exp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    public void generateInputFile(List<Artifact> la){
    	try {
			PrintWriter file = new PrintWriter(this.name+".txt","UTF-8");
			for (Artifact a:la){
				file.println(a.getName());
				//System.out.println(a.getName());
				for (ArtifactAttribute an: a.getListAttribute()){
					file.println(an.Name);
				}
			}
			
			// will modify the condition in here
			file.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    public void removeLiteral2Post(String lit) {
    	this.post_cond.remove(lit);
    }

    public void removeLiteral2Pre(String lit) {
    	this.pre_cond.remove(lit);
    }
    
    public void removeLiteral2PostComma(String lit) {
    	this.post_comma.remove(lit);
    }

    public void removeLiteral2Dom(String lit) {
    	this.dom_.remove(lit);
    }
    
    public void removeLiteral2Instance(String lit) {
    	this.instance_.remove(lit);
    }

    public void addLiteral2Post(String lit) {
    	this.post_cond.add(lit);
    }

    public void addLiteral2Pre(String lit) {
    	this.pre_cond.add(lit);
    }
    
    public void addLiteral2PostComma(String lit) {
    	this.post_comma.add(lit);
    }

    public void addLiteral2Dom(String lit) {
    	this.dom_.add(lit);
    }
    
    public void addLiteral2Instance(String lit) {
    	this.instance_.add(lit);
    }

    public Formula getFormula4Pre(Logic lo){
    	return makeOrbitalFormula(lo, this.pre_cond);
    }

    public Formula getFormula4Post(Logic lo){
    	return makeOrbitalFormula(lo, this.post_cond);
    }
    
    public Formula getFormula4PostComma(Logic lo){
    	return makeOrbitalFormula(lo, this.post_comma);
    }
    
    public Formula getFormula4Dom(Logic lo){
    	return makeOrbitalFormula(lo, this.dom_);
    }
    
    public Formula getFormula4Instance(Logic lo){
    	return makeOrbitalFormula(lo, this.instance_);
    }

    public Formula getPreCondition(){
    	return this.preCondition;
    }
    
    public Formula getPostCondition(){
    	return this.postCondition;
    }
    
    public Formula getPostComma(){
    	return this.postComma;
    }
    
    public Formula getInstance(){
    	return this.instance;
    }
    
    public Formula getDom(){
    	return this.dom;
    }
    
    public ArrayList<String> getpre_cond(){
    	return this.pre_cond;
    }
    
    public ArrayList<String> getpost_cond(){
    	return this.post_cond;
    }
    
    public ArrayList<String> getpost_comma(){
    	return this.post_comma;
    }
    
    public ArrayList<String> getinstance_(){
    	return this.instance_;
    }
    
    public ArrayList<String> getdom_(){
    	return this.dom_;
    }
	public void setPreCondition(Formula preCondition) {
		this.preCondition = preCondition;
	}
	public void setPostCondition(Formula postCondition) {
		this.postCondition = postCondition;
	}
	public void setPostComma(Formula postComma) {
		this.postComma = postComma;
	}
	public void setInstance(Formula instance) {
		this.instance = instance;
	}
}