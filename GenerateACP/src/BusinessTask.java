
import java.util.ArrayList;
import java.util.List;

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

	protected Formula preCondition;
	protected Formula postCondition;
	protected Formula postComma;
	protected Formula dom;
	protected Formula instance;
	
	public String service;
	public String artifacts;
	
    public BusinessTask() throws Exception {
        
    }
    public void genInstance(List<Expression> listCond) {
		ArrayList<String> instance = new ArrayList<String>();
		String[] listArtifact = artifacts.split(",");
		for(String artifactName : listArtifact) {
			for(Expression condition : listCond) {
				if(condition.preArtifact.equals(artifactName) || condition.postArtifact.equals(artifactName)) {
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
				this.instance = (Formula) Algorithm.logic.createExpression(instanceStr);
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
}