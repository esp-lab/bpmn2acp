/**
 * Created by dell on 10/6/2016.
 */
import javafx.scene.shape.PathElement;
import org.json.simple.parser.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomInfo {
    String id;
    String threadId;
    ArrayList<String> labelIds;
    String received;
    String date;
    String subject;
    String text;
    String snippet;

    public void setId(String id) {
        this.id = id;
    }
    public void setThreadId(String threadId){
        this.threadId= threadId;
    }
    public void setReceived(String received){
        this.received= received;
    }
    public void setDate(String date){
        this.date= date;
    }
    public void setSubject(String subject){
        this.subject= subject;
    }
    public void setLabelIds(JSONArray labelIds){
       this.labelIds= new ArrayList<>();
        for(int i= 0; i<labelIds.size(); i++){
            this.labelIds.add(labelIds.get(i).toString());
        }
    }
    public void setSnippet(String snippet){
        this.snippet= snippet;
    }
    public void setText(String text){
        this.text= text;
    }

    public void SetInfo(JSONArray jsonArray,int position){
        try{
            JSONObject obj=(JSONObject) jsonArray.get(position);
            setId(obj.get("id").toString());

            setThreadId(obj.get("threadId").toString());
            setSnippet(obj.get("snippet").toString());
            //Headers
            JSONArray headers= (JSONArray) obj.get("headers");
            JSONObject header= (JSONObject) headers.get(0);//just have one reader

            JSONArray labelIds= (JSONArray) obj.get("labelIds");
            setLabelIds(labelIds);

            setDate(header.get("Date").toString());
            setReceived(header.get("Received").toString());
            setSubject(header.get("Subject").toString());
            setText(obj.get("text").toString());



        }
        catch (Exception e){
            e.getStackTrace();
        }
    }
    public void GetInfo(){
        System.out.println("Id: "+ id);
        System.out.println("ThreadId: "+ threadId);
        System.out.println("Date: "+ date);
        System.out.println("Received: "+received);
        System.out.println("Subject: "+subject);
        System.out.println("labelIds length: "+ labelIds);
        System.out.println();
    }
    public String GetId(){
        return id;
    }
    public String GetThreadId(){return threadId;};
    public ArrayList<String> GetLabelIds(){
        return labelIds;
    }
    public String GetReceived(){
        return received;
    }
    public String GetDate(){
        return date;
    }
    public String GetSubject(){
        return subject;
    }
    public String GetText(){return text;}

    //---------------- ReadText--------------------------------------

    /*final String profileCode="\\s[a-zA-Z][0-9]{9}\\s|\\s[0-9][a-zA-Z][0-9]{8}\\s|\\s[0-9]{2}[a-zA-Z][0-9]{7}\\s" +
            "|\\s[0-9]{3}[a-zA-Z][0-9]{6}\\s|\\s[0-9]{4}[a-zA-Z][0-9]{5}\\s|\\s[0-9]{5}[a-zA-Z][0-9]{4}\\s" +
            "|\\s[0-9]{6}[a-zA-Z][0-9]{3}\\s|\\s[0-9]{7}[a-zA-Z][0-9]{2}\\s|\\s[0-9]{8}[a-zA-Z][0-9]\\s";*/
    final String profileCode="[a-zA-Z][0-9]{9}|[0-9][a-zA-Z][0-9]{8}|[0-9]{2}[a-zA-Z][0-9]{7}" +
            "|[0-9]{3}[a-zA-Z][0-9]{6}|[0-9]{4}[a-zA-Z][0-9]{5}|[0-9]{5}[a-zA-Z][0-9]{4}" +
            "|[0-9]{6}[a-zA-Z][0-9]{3}|[0-9]{7}[a-zA-Z][0-9]{2}|[0-9]{8}[a-zA-Z][0-9]";
    final String codeAir="[a-zA-Z0-9]{6}";
    String codeAirAdvance= "\\("+"\\s?"+codeAir+"(\\s?;\\s?"+codeAir+")*"+"\\s?"+"\\)";
    String profileCodeAv= "\\s\\(?"+profileCode+"\\)?"+",?\\s";
    final String codeVietjet="\\s[0-9]{8}\\s";
    final String maDatCho= " (M|m)(Ã|ã) (Đ|đ)(Ặ|ặ)(T|t) (c|C)(H|h)(Ỗ|ỗ)";
 /*   private boolean CheckCodeAir(String codeAir){
        System.out.println(codeAir);
        boolean word=false;
        boolean number= false;
        Pattern keyW=Pattern.compile("a-zA-Z");
        Pattern keyN= Pattern.compile("0-9");
        Matcher matcherW= keyW.matcher(codeAir);
        Matcher matcherN= keyN.matcher(codeAir);
       // System.out.println(codeAir.toCharArray());
        if(matcherN.find()) number= true;
        if(matcherW.find()) word= true;
        if(number==true && word== true) return true;
        else{
            int count=0;
            Pattern keyCapital= Pattern.compile("A-Z");
            Pattern num= Pattern.compile("0-9");

            Matcher matcherKeyCapital= keyCapital.matcher(codeAir);
            //System.out.println(matcherKeyCapital.find());

            while(matcherKeyCapital.find()){
                count+=1;
                if(count==2) break;
            }
            if(count==2|| number) return true;
            else return false;
        }
    }*/
    private boolean checkVietjet(String codeVietjet) {
        Pattern pCodeSupport = Pattern.compile("1900");
        Matcher mCodeSupport = pCodeSupport.matcher(codeVietjet);
        return mCodeSupport.find()==false;

    }
    private boolean checkVietnamAirline(String codeVietnamAirline){
        String upLetter="[A-Z]";
        Pattern pCode= Pattern.compile(upLetter);
        Matcher mCode= pCode.matcher(codeVietnamAirline);
        int count= 0;
        while (mCode.find()){
            count++;
        }
        if(count>2) return true;
        else return false;
    }
    public ArrayList<String> CodeTicket(){

        ArrayList<String> res=new ArrayList<>();
        //ArrayList<Integer> number= new ArrayList<>();
      // Pattern key=Pattern.compile("\\s(M|m)(ã|Ã) (Đ|đ)(Ặ|ặ)(T|t) (C|c)(H|h)(ỗ|Ỗ)\\s|\\s(M|m)(Ã|ã)\\s");
        Pattern pCodeVietnamAirline= Pattern.compile(codeAirAdvance);
        Matcher mCodeVietnamAirline= pCodeVietnamAirline.matcher(text);



        //Ma dat cho
        Pattern pMDC= Pattern.compile(maDatCho);
        Matcher mMDC= pMDC.matcher(text);

        Pattern pCodeVietjet= Pattern.compile(codeVietjet);
        Matcher mCodeVietjet = pCodeVietjet.matcher(text);

        while(mCodeVietnamAirline.find()){
            String code= text.substring(mCodeVietnamAirline.start(),mCodeVietnamAirline.end());
          //  if(res.contains(code)==false  ) {
            Pattern oneCodeVNAir= Pattern.compile(codeAir);
            Matcher mOneCodeVNAir= oneCodeVNAir.matcher(code);
            while(mOneCodeVNAir.find()){
                String one= code.substring(mOneCodeVNAir.start(),mOneCodeVNAir.end());
                if(res.contains(one)==false&& checkVietnamAirline(one)) res.add(one);
            }

           // }
        }
        while(mMDC.find()){
            String temp= text.substring(mMDC.end(),mMDC.end()+16);
            //code VietnamAirline outside ()
            Pattern pCodeVietnamAirline2= Pattern.compile("\\s"+codeAir+"\\s");
            Matcher mCodeVietnamAirline2= pCodeVietnamAirline2.matcher(temp);

            while(mCodeVietnamAirline2.find()){
                String code= temp.substring(mCodeVietnamAirline2.start(),mCodeVietnamAirline2.end());
                if(res.contains(code)== false && checkVietnamAirline(code)) res.add(code);
            }
        }

        while(mCodeVietjet.find()){
            String code= text.substring(mCodeVietjet.start(),mCodeVietjet.end());
            if(res.contains(code)== false && checkVietjet(code)) res.add(code);
        }

       /* number.add(text.length());
        for(int i=0; i<number.size()-1; i++) {
            String temp = text.substring(number.get(i), number.get(i+1));
         //   System.out.println(temp);
            Pattern code1 = Pattern.compile(codeAirAdvance);//gom 6 ky tu
            Matcher mCode1 = code1.matcher(temp);
           // System.out.println(mCode1);
            Pattern code2 = Pattern.compile(codeVietjet);// code Vietjet 8 chu so
            Matcher mCode2 = code2.matcher(temp);
         //   System.out.println(!mCode1.find());
         //   System.out.println(mCode2.find());

            boolean boolCode1= mCode1.find();
            boolean boolCode2= mCode2.find();
            while(boolCode2){
                String code= temp.substring(mCode2.start(),mCode2.end());
                if(!res.contains(code))res.add(code);
                boolCode2= mCode2.find();
            }
            while(boolCode1){
                String code= temp.substring(mCode1.start(),mCode1.end());
                System.out.println(code);
                if(!res.contains(code))res.add(code);
                boolCode1= mCode1.find();
            }
*/

          /*  while(boolCode1 || boolCode2) {
                if (boolCode1 && !boolCode2) {
                    String code = temp.substring(mCode1.start(), mCode1.end());
                    if (CheckCodeAir(code) && res.contains(code) == false)
                        res.add(temp.substring(mCode1.start(), mCode1.end()));
                } else if (!boolCode1 && boolCode2) {
                    String code = temp.substring(mCode2.start(), mCode2.end());
                    if (res.contains(code) == false)
                        res.add(code);
                } else if (!boolCode1 && !boolCode2) {

                } else {
                    // System.out.println(mCode1.start());
                    if (mCode1.start() > mCode2.start()) {
                        String code = temp.substring(mCode2.start(), mCode2.end());
                        if (res.contains(code) == false)
                            res.add(code);
                    } else {
                        String code = temp.substring(mCode1.start(), mCode1.end());
                        if (CheckCodeAir(code) && res.contains(code) == false)
                            res.add(code);
                    }
                }
                boolCode1= mCode1.find();
                boolCode2= mCode2.find();
            }*/
        //}
        return  res;
    }

    public ArrayList<String> CodeProfile(){
        ArrayList<String> res= new ArrayList<>();
        Pattern pCodeProfile=  Pattern.compile(profileCodeAv);
        Matcher mCodeProfile= pCodeProfile.matcher(text);
        while(mCodeProfile.find()){
            String code= text.substring(mCodeProfile.start(),mCodeProfile.end());
            if(res.contains(code)== false ) res.add(code);
        }
        /*ArrayList<Integer> number= new ArrayList<>();
        Pattern key= Pattern.compile("\\s((m|M)(ã|Ã|a))? (H|h)(ồ|Ồ|o) (S|s)(ơ|Ơ|o)\\s|\\s(M|m)(Ã|ã|a)\\s|\\sMHS\\s");
        Matcher matcher= key.matcher(text);
        while(matcher.find()){

            number.add(matcher.end());
        }
        number.add(text.length());
        int n= number.size();
        for(int i=0; i<number.size()-1; i++){

            String temp= text.substring(number.get(i),number.get(i+1));

            Pattern mCode= Pattern.compile(profileCodeAv);


            Matcher matcher1= mCode.matcher(temp);
            while (matcher1.find()){
                String code= temp.substring(matcher1.start(), matcher1.end());
                if(res.contains(code)==false)
                res.add(code);
                //System.out.println(i);
            }
        }*/
        return res;
    }
}
