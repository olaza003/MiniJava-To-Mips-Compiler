package typecheck.Types;
import syntaxtree.*;

public class Bool implements Scope{
    String typeHold;
    private static Bool ourInstance = new Bool();

    public static Bool instance() {return ourInstance;}

    public Bool(){}

    public String name() {return "boolean";}
    public String typeName(){return "boolean";}
}
