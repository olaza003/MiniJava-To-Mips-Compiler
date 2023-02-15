package hw2.prints;
import java.io.*;
import java.util.*;

public class VariableGen {
    private static final String variablestr = "t.";
    private int variableCount = 0;

    private static final String nullStr = "null";
    private int nullCount = 1;

    private int if_else = 1;

    private int if_end = 1;

    private int while_top = 1;

    private int while_end = 1;

    public VariableGen(){}
    
    public void resetCount(){variableCount = 0; nullCount = 1; if_else = 1; if_end = 1;}

    //incrementors
    public void incNull(){nullCount++;}
    public void incIfelse(){if_else++;}
    public void incIfend(){if_end++;}
    public void incWhileTop(){while_top++;}
    public void incWhileEnd(){while_end++;}

    public void incVariableCount(){variableCount++;}
    public void decVariableCount(){
        if(variableCount != 0){variableCount--;}
    }

    //accessors
    public String getNull(){//null#
        return nullStr + nullCount;
    }

    public String getElse(){
        return "if" + if_else + "_else";
    }

    public String getEnd(){
        return "if" + if_end + "_end";
    }

    public String getWhileTop(){
        return "while" + while_top + "_top";
    }

    public String getWhileEnd(){
        return "while" + while_end + "_end";
    }

    public String getVar(){ //t.#
        return variablestr + Integer.toString(variableCount);
    }

    public String getVarMinus(){ //t.#
        return variablestr + Integer.toString(variableCount-1);
    }
}
