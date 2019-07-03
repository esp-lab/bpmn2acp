import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Artifact {
	private String name;
	private Collection<ArtifactState> listState = new ArrayList<ArtifactState>();
	private Collection<ArtifactAttribute> listAttribute = new ArrayList<ArtifactAttribute>();

	public String toString() {
		return name;
	}

	public Artifact() {

	}

	public Artifact(String Name, List<UMLAttribute> listUMLAtt) {
		this.name = Name;
		//System.out.println("Class   "  + Name);
		for (UMLAttribute att : listUMLAtt) {
			//System.out.println(att.getName() + "   " + att.getDatatype().getName());
			if (att.getDatatype() != null)
				this.listAttribute.add(new ArtifactAttribute(att.getName(), att.getDatatype().getName()));
			else
				this.listAttribute.add(new ArtifactAttribute(att.getName(), "String"));
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

}
