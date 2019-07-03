package main;

import com.sun.xml.internal.bind.v2.model.runtime.RuntimeAttributePropertyInfo;
import com.sun.xml.internal.bind.v2.runtime.Name;

import java.util.ArrayList;
import java.util.List;

public class ArtifactState {
	private String Name;
	private List<String> listCond = new ArrayList<String>();
	private String listOption = "";
	private List<String> listDefined = new ArrayList<String>();
	private List<String> listUndefined = new ArrayList<String>();
	private List<ArtifactAttributeCompare> listAttributeCompare = new ArrayList<ArtifactAttributeCompare>();
	private String condition="";
	//add more attribute
	private Boolean Used = false;

	public String toString() {
		return Name;
	}

	public String getName() {
		return Name;
	}

	public Boolean getUsed() {
		return Used;
	}

	public void setUsed(Boolean used) {
		Used = used;
	}

	public void setName(String name) {
		Name = name;
	}

	public ArtifactState(String name) {
		this.Name = name;

	}

	public void addCond(String cond) {
		this.listCond.add(cond);
	}

	public void clearList() {

		this.listDefined.clear();
		this.listUndefined.clear();
	}

	public void pushListAttCompare(ArtifactAttributeCompare aAC) {
		this.listAttributeCompare.add(aAC);
	}

	public void clearListAttCompare() {
		this.listAttributeCompare.clear();
	}

	public List<ArtifactAttributeCompare> getListAttCompare() {
		return this.listAttributeCompare;
	}

	public List<String> getListDefined() {
		return this.listDefined;
	}

	public List<String> getListUndefined() {
		return this.listUndefined;
	}

	public void removeItemDefinedList(String needToRemove) {
		if (listDefined.indexOf(needToRemove) != -1) {
			this.listDefined.remove(needToRemove);
		}
	}
	public void pushItemDefinedList(String toAdd){
		this.listDefined.add(toAdd);
	}

	public void removeItemUndefinedList(String needToRemove){
		if(listUndefined.indexOf(needToRemove)!=-1){
			this.listUndefined.remove(needToRemove);
		}
	}
	public void pushItemUndefinedList(String toAdd){
		this.listUndefined.add(toAdd);
	}

	public String getCondition(){
		String result= "";
		for(ArtifactAttributeCompare i: listAttributeCompare){
			if(result.equals("")){
				result+=i.getSence();
			}
			else{
				result+=(" /\\ "+ i.getSence());
			}
		}

		for(String i:listDefined){
			if(result.equals("")){
				result+=i;
			}else{
				result+=(" /\\ "+ i);
			}
		}
		for(String i:listUndefined){
			if(result.equals("")){
				result+=i;
			}else{
				result+=(" /\\ "+ i);
			}
		}
		return result;
	}

	public void setCondition(String cond){
		this.condition= cond;
	}

	public String generateCond(String nameOfClass) {
		String result = "instate(" + nameOfClass + "," + this.getName() + ")";
		return result;
	}

}
