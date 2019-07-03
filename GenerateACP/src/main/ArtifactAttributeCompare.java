package main;

import java.util.ArrayList;
import java.util.List;

public class ArtifactAttributeCompare{
    private String leftHandSide;
    private String rightHandSide;
    private String Operator;

    public String getLeftHandSide(){
        return leftHandSide;
    }

    public void setLeftHandSide(String leftHS){
        this.leftHandSide= leftHS;
    }

    public String getRightHandSide(){
        return rightHandSide;
    
    }

    public void setRightHandSide(String rhs){
        this.rightHandSide= rhs;
    }

    public String getOperator(){
        return Operator;
    }

    public void setOperator(String op){
        this.Operator=op;
    }

    public String getSence(){
        String result= leftHandSide +Operator+ rightHandSide;
        return result;
    }

    // public String get(){
        
    // }

}