package main;

import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociation;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAssociationEnd;
import gov.nih.nci.ncicb.xmiinout.domain.UMLAttribute;
import gov.nih.nci.ncicb.xmiinout.domain.UMLClass;
import gov.nih.nci.ncicb.xmiinout.domain.UMLDependency;
import gov.nih.nci.ncicb.xmiinout.domain.UMLDependencyEnd;
import gov.nih.nci.ncicb.xmiinout.domain.UMLGeneralization;
import gov.nih.nci.ncicb.xmiinout.domain.UMLInterface;
import gov.nih.nci.ncicb.xmiinout.domain.UMLModel;
import gov.nih.nci.ncicb.xmiinout.domain.UMLOperation;
import gov.nih.nci.ncicb.xmiinout.domain.UMLPackage;
import gov.nih.nci.ncicb.xmiinout.domain.UMLTaggedValue;
import gov.nih.nci.ncicb.xmiinout.handler.HandlerEnum;
import gov.nih.nci.ncicb.xmiinout.handler.XmiException;
import gov.nih.nci.ncicb.xmiinout.handler.XmiHandlerFactory;
import gov.nih.nci.ncicb.xmiinout.handler.XmiInOutHandler;
import gov.nih.nci.ncicb.xmiinout.util.ModelUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;


public class ArtifactRepository {
	private static Collection<Artifact> listArtifact;

	public static Collection<Artifact> getListArtifact(String fileName) throws XmiException, IOException {
		listArtifact = new ArrayList<Artifact>();
		String handlerEnumType = "EADefault";
		XmiInOutHandler	handler = XmiHandlerFactory.getXmiHandler(HandlerEnum.getHandlerEnumType(handlerEnumType));
		handler.load(fileName);
		UMLModel model = handler.getModel();		
		List<UMLPackage> listPack = model.getPackages();
		for(UMLPackage umlPackage : listPack)
		{
			insertClassFromPackage(umlPackage);
		}
		return listArtifact;
	}
	
	public static void insertClassFromPackage(UMLPackage umlPackage)
	{
		for(UMLClass umlClass : umlPackage.getClasses())
		{
			insertArtifact(new Artifact(umlClass.getName(), umlClass.getAttributes()), umlPackage);
		}
		for(UMLPackage _UmlPackage : umlPackage.getPackages())
		{
			insertClassFromPackage(_UmlPackage);
		}
	}
	
	/*public static void main(String[] args) throws XmiException, IOException {
		String filename = "D:\\ComputerScience\\ScienceResearch\\xmihandler-master\\test\\testdata\\sdk.xmi";
		getListArtifact(filename);
		for(Artifact a : listArtifact)
		{
			System.out.println(a.getName());
		}
	}*/
	
	private static boolean isUpperCase(String s)
	{
		for (int i=0; i < s.length(); i++)
		{
			if (s.charAt(i) == '_' || Character.isDigit(s.charAt(i)))
			{
				continue;
			}
			if (!Character.isUpperCase(s.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}
	
	private static void insertArtifact(Artifact artifact, UMLPackage UmlPackage)
	{
		if(!UmlPackage.getName().equals("lang") && !isUpperCase(artifact.getName()))
		{
			listArtifact.add(artifact);
		}
		
	}
}
