package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;


public class StateDiagramReader {

	private static String openModelTag = "<UML:SimpleState"; 
	private static String closeModelTag = "</UML:ModelElement.taggedValue>"; 
	
	private static String getNameStateFromTag(String tag) {
		String result = "";
		String[] arrayAttribute = tag.split(" ");
        for(String s : arrayAttribute) {
        	if(s.contains("name=\"")) {
        		String[] values = s.split("=");
        		result = values[1].replace("\"", "");
        	}
        }
		return result;
	}
	
	public static Collection<ArtifactState> getListState(String filePath) throws IOException {
		Collection<ArtifactState> listState = new ArrayList<ArtifactState>();
		boolean openModelTagFound = false;
		boolean closeModelTagFound = false;
		String input = filePath;
        FileInputStream fis = new FileInputStream(new File(input));
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        boolean found = false; 
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line != null && !line.isEmpty()) {
                if(line.contains(openModelTag)) {
                	String nameState = getNameStateFromTag(line);
                	listState.add(new ArtifactState(nameState));
                }
                
            }
        }
        return listState;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String filePath = "D:\\ComputerScience\\ScienceResearch\\xmihandler-master\\test\\testdata\\NCKH2.xmi";
		Collection<ArtifactState> listState = getListState(filePath);
		for(ArtifactState a : listState) {
			System.out.println(a.getName());
		}
	}

}
