/**
 * Created by dell on 10/6/2016.
 */

import java.io.FileReader;
import java.util.*;

import  jxl.*;
import java.io.File;
import java.io.IOException;
import java.util.regex.*;
import java.util.regex.Pattern;

import jxl.write.*;
import jxl.write.Number;

import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import sun.nio.cs.UTF_32;

import java.io.*;

import javax.swing.text.html.parser.Parser;

public class Test {

    public static void main(String[] Args){
        //-------------Read data from Json file--------------
       /* String filePath="E:\\Research\\king.json";
        ArrayList<CustomInfo> objs= new ArrayList<>();
        ReadJson a= new ReadJson();
        ArrayList<CustomInfo> customInfos=a.Read(filePath,objs);
        System.out.println(customInfos.get(0).GetThreadId());*/

        //-----------End reading-------------
        //-----------Start Writing data to Excel-------------
      /*  WriteExcel w= new WriteExcel();
        w.Write(objs);
        //-----------End Writing--------------------------
        System.out.println(objs.get(0).GetText());*/
//====================Change json =======================
        File file= new File("E:\\ResearchScience_NKC\\Data\\gmail.json");
        ArrayList<CustomInfo> objs= new ArrayList<>();
        ReadJson a = new ReadJson();
        a.Read("E:\\ResearchScience_NKC\\Data\\gmail.json",objs);
        WriteExcel b= new WriteExcel();
        b.Write(objs);
      /*  String text= objs.get(0).GetText();
        Pattern p= Pattern.compile("MÃ HỒ SƠ");
        Matcher m= p.matcher(text);
        while (m.find()){
            String code= text.substring(m.start(),m.end()+15);
            System.out.println(m.start());
            System.out.println(code);
        }
*/
       /* String test= "Ki () (T) (T) ( TBDSKH ; TBDSKH ) ( kingdf )";
        String pContent= "\\s([a-zA-Z0-9]{6})\\s";
        Pattern p1= Pattern.compile("\\("+(pContent)+"(;"+pContent+")*"+"\\)");
        Matcher m1= p1.matcher(test);
        while (m1.find()){
            System.out.println(m1.start());
        }*/

      //  System.out.println(objs.get(2).CodeTicket());

      /*  for (CustomInfo ci: objs){
         //   ArrayList<String> codeTicket=
            System.out.println("Ma dat cho " + ci.CodeTicket());
            //System.out.println("noi dung "+ ci.GetText());
            System.out.println("Ma ho so " + ci.CodeProfile());
            System.out.println();
        }*/


         //  System.out.println(text);
          //  FileWriter fileWriter= new FileWriter(file);
         //   BufferedWriter bw= new BufferedWriter(fileWriter);
         //   bw.write(text);
         //   bw.close();
            //======================================================



    }
}
