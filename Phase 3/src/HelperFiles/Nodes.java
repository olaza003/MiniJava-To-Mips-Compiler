package HelperFiles;

import java.util.*;

public class Nodes {
    private String ifLabel, gotoLabel; //if, goTo
    public List<String> destination; //VVarRef
    public List<String> sources; //VOperand

    public boolean calle = false;

    public Nodes(List<String> des, List<String> sor){destination = des; sources = sor;}

    public void storeIfLabel(String str){ifLabel = str;}

    public void storeGotoLabel(String str){gotoLabel = str;}

    public String getIfLabel(){return ifLabel;}
    public String getGotoLabel(){return gotoLabel;}

    public Boolean ifLabelEmpty(){
        return ifLabel == null;
    }

    public Boolean gotoLabelEmpty(){
        return gotoLabel == null;
    }
}
