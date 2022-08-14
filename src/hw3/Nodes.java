package hw3;

import java.util.*;

public class Nodes {
    public String label1, label2; //if, goTo
    public List<String> destination; //VVarRef
    public List<String> sources; //VOperand

    public Nodes(List<String> des, List<String> sor){destination = des; sources = sor;}

    public void storeLabel1(String str){label1 = str;}

    public void storeLabel2(String str){label2 = str;}

    public String getLabel1(){return label1;}
    public String getLabel2(){return label2;}

    public Boolean label1Empty(){
        if(label1 == null) return true;
        return false;
    }

    public Boolean label2Empty(){
        if(label2 == null) return true;
        return false;
    }
}
