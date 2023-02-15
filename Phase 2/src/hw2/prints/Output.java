package hw2.prints;
import java.util.*;
import java.io.*;

import syntaxtree.Identifier;

public class Output {
    private String output = "";
    private static final String IDENT = "  "; //2 space
    private String indent = "";
    private String temp = "";

    public Output(){output = "";}

    public void addtext(String s){
        output +=indent + s + "\n"; //mayb remove the indent if theres a break point
    }

    public void tempString(String s){temp += s;}
    public String getTemp(){return temp;} //more for when (10 11 15 13)
    public void clearTemp(){temp = "";}
    public String getOutput(){return output;}
    //public void addIdent(){output += indent;}
    public void addLine(){output += "\n";}
    public void increaseIdent(){indent += IDENT;}
    public void decreaseIdent(){indent = indent.substring(0, indent.length() - IDENT.length());}
}
