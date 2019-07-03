import sun.text.normalizer.UTF16;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dell on 10/16/2016.
 */
public class ReadText {

   final String profileCode="\\b[a-zA-Z][0-9]{9}\\b|\\b[0-9][a-zA-Z][0-9]{8}\\b|\\b[0-9]{2}[a-zA-Z][0-9]{7}\\b" +
           "|\\b[0-9]{3}[a-zA-Z][0-9]{6}\\b|\\b[0-9]{4}[a-zA-Z][0-9]{5}\\b|\\b[0-9]{5}[a-zA-Z][0-9]{4}\\b" +
           "|\\b[0-9]{6}[a-zA-Z][0-9]{3}\\b|\\b[0-9]{7}[a-zA-Z][0-9]{2}\\b|\\b[0-9]{8}[a-zA-Z][0-9]\\b";
    final String codeAir="\\b[a-zA-Z0-9]{6}\\b";
    final String codeVietjet="\\b[0-9]{8}\\b";
    private boolean CheckCodeAir(String codeAir){
        boolean word=false;
        boolean number= false;
        Pattern keyW=Pattern.compile("a-zA-Z");
        Pattern keyN= Pattern.compile("0-9");
        Matcher matcherW= keyW.matcher(codeAir);
        Matcher matcherN= keyN.matcher(codeAir);
        if(matcherN.find()) number= true;
        if(matcherW.find()) word= true;
        if(number==true && word== true) return true;
        else return false;
    }
    public ArrayList<String> CodeTicket(String text){

        ArrayList<String> res=new ArrayList<>();
        ArrayList<Integer> number= new ArrayList<>();
        Pattern key=Pattern.compile("\\b(M|m)(ã|Ã) (Đ|đ)(Ặ|ặ)(T|t) (C|c)(H|h)(ỗ|Ỗ)\\b|\\b(M|m)(Ã|ã)\\b");
        Matcher m= key.matcher(text);
        while (m.find()){
            number.add(m.start());

        }
        number.add(text.length());
        for(int i=0; i<number.size()-1; i++) {
            String temp = text.substring(number.get(i), number.get(i+1));
            Pattern code1 = Pattern.compile(codeAir);
            Matcher mCode1 = code1.matcher(temp);

            Pattern code2 = Pattern.compile(codeVietjet);
            Matcher mCode2 = code2.matcher(temp);
            if (mCode1.find() && !mCode2.find()) {
                if(CheckCodeAir(temp.substring(mCode1.start(),mCode1.end())))
                res.add(temp.substring(mCode1.start(), mCode1.end()));
            } else if (!mCode1.find() && mCode2.find()) {
                res.add(temp.substring(mCode2.start(), mCode2.end()));
            } else {
                if (mCode1.start() > mCode2.start()) {
                    res.add(temp.substring(mCode2.start(), mCode2.end()));
                } else {
                    if(CheckCodeAir(temp.substring(mCode1.start(),mCode1.end())))
                    res.add(temp.substring(mCode1.start(), mCode1.end()));
                }
            }
        }
        return  res;
    }
    public ArrayList<String> CodeProfile(String text){
        ArrayList<String> res= new ArrayList<>();
        ArrayList<Integer> number= new ArrayList<>();
        Pattern key= Pattern.compile("\\b(m|M)(ã|Ã) (H|h)(ồ|Ồ) (S|s)(ơ|Ơ)\\b|\\b(M|m)(Ã|ã)\\b");
        Matcher matcher= key.matcher(text);
        while(matcher.find()){

           number.add(matcher.end());
        }
        number.add(text.length());
        int n= number.size();
        for(int i=0; i<number.size()-1; i++){

            String temp= text.substring(number.get(i),number.get(i+1));

            Pattern mCode= Pattern.compile(profileCode);
            Matcher matcher1= mCode.matcher(temp);
            if (matcher1.find()){
                res.add(temp.substring(matcher1.start(), matcher1.end()));
                //System.out.println(i);
            }
        }
        return res;
    }
}
