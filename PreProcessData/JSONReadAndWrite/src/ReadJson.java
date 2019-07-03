/**
 * Created by dell on 10/7/2016.
 */
import org.json.simple.parser.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.*;
import java.util.ArrayList;

public class ReadJson {
    private void fixJson(String filePath){
        File file= new File(filePath);
        try {
            boolean flag=false;
            boolean flagFirst=true;
            FileReader fileReader = new FileReader(file);
            BufferedReader bf = new BufferedReader(fileReader);
            String line;
            String text="[";
            //  int a= 0;
            while ((line=bf.readLine())!= null){
                if(line.contains("[")&& flagFirst==true) {
                    flag=true;
                    break;
                }
                flagFirst=false;
                if(line.equals("    \"headers\": {")){
                    line= "    \"headers\": [{";
                }
                if (line.equals("    },")){
                    line="    }],";
                }
                else if(line.equals("    }")){
                    line="    }],"+"\n"+"    \"text\":\"\"";
                }
                text= text.concat(line);

                text= text+"\n";

            }
            text= text.concat("]");
            //  System.out.println(text);
            if(flag==false) {
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write(text);
                bw.close();
            }
            //======================================================

        }
        catch (Exception e){
            System.out.println("Do again");
        }
    }
    public ArrayList<CustomInfo> Read(String filePath, ArrayList<CustomInfo> objs){
        fixJson(filePath);
        try {
            JSONParser parser = new JSONParser();
          //  FileReader f= new FileReader(filePath) ;
            Object json = parser.parse(new FileReader(filePath));
            JSONArray array= (JSONArray)json;

            for(int i=0; i< array.size(); i++){
                CustomInfo temp= new CustomInfo();
                temp.SetInfo(array,i);
                objs.add(temp);

               // temp.GetInfo();
            }


        }
        catch (Exception e){
                e.getStackTrace();
        }
        return objs;
    }
}
