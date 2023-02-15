package hw2.Types;
import java.util.*;
import java.io.*;

public class Scope {
    String name = ""; // num
    String content = "";
    String val = ""; //like t.0 or t.1
    boolean ref; //
    boolean isCall; 
    boolean isNull = false;

    public Scope(String newName, String newVal, boolean newRef, boolean newCall){
        name = newName; //ident, etc
        val = newVal; //'t' variable
        //content = s3;
        ref = newRef;
        isCall = newCall;
    }

    public Scope(String newName){
        name = newName;
        val = "";
        ref = false;
        isCall = false;
    }

    public void changeName(String s){name = s;}
    public void changeVal(String s){val = s;}
    public void changeRef(boolean s){ref = s;}
    public void changeIsCall(boolean s){isCall = s;}
    public void changeNull(boolean s){isNull = s;}

    public String getName(){return name;}
    public String getVal(){return val;}
    public boolean getRef(){return ref;}
    public boolean isCall(){return isCall;}
    public boolean getNull(){return isNull;}
    /* String name();
    String typeName();
    void nameAdd(String s); */
}
