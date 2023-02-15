package typecheck.Types;
import syntaxtree.*;

public class Id implements Scope{
    String str;
    String typeHold = "";
    private static Id ourInstance = new Id();

    public static Id instance() {return ourInstance;}

    public Id(){}

    public Id(String s){str = s;}
    public Id(String s1, String s2){str = s1; typeHold = s2;}

    public String name() {return str;}

    public void nameAdd(String s){str = s;}

    public String typeName(){return typeHold;}

}
