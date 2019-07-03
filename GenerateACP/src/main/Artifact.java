package main;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;


public class Artifact {
	private String name;
	private Collection<ArtifactState> listState = new ArrayList<ArtifactState>();
	private Collection<ArtifactAttribute> listAttribute = new ArrayList<ArtifactAttribute>();
	private String condStateInstate="";
	public Artifact(String name) {
		this.name = name;
	}
	public Artifact()
	{
		
	}
	public static String[] toArrayString(List<Artifact> list){
		if (list == null){
			return new String[0];
		}
		String[] result = new String[list.size()];
		int i=0;
		for (Artifact ele:list){
			result[i] = ele.name;
			i++;
		}
		return result;
	}
	@Override
	public String toString(){
		return name;
	}
	public Artifact(String Name, List<UMLAttribute> listUMLAtt)
	{
		this.name = Name;
		//System.out.println("Class   "  + Name);
		for(UMLAttribute att : listUMLAtt)
		{
			//System.out.println(att.getName() + "   " + att.getDatatype().getName());
			if(att.getDatatype() != null)
				this.listAttribute.add(new ArtifactAttribute(att.getName(), att.getDatatype().getName()));
			else this.listAttribute.add(new ArtifactAttribute(att.getName(), "String"));
		}
	

	}

	

	
	public Collection<ArtifactState> getListState() {
		return listState;
	}
	public void setListState(Collection<ArtifactState> listState) {
		this.listState = listState;
	}
	public Collection<ArtifactAttribute> getListAttribute() {
		return listAttribute;
	}
	public void setListAttribute(Collection<ArtifactAttribute> listAttribute) {
		this.listAttribute = listAttribute;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void resetAttChoice(){
		for(ArtifactAttribute i: listAttribute){
			i.setDefined(false);
			i.setOptions(false);
			i.setUndefined(false);
		}
	}
	
	public String getcondStateInstate(){
		return this.condStateInstate;
	}
	public void setcondStateInstate(String condStateInstate){
		this.condStateInstate= condStateInstate;
	}
	
}
