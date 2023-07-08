package typecheck.Types;
import syntaxtree.*;

public class Int implements Scope{
    String str, typeHold;
    private static Int ourInstance = new Int();

    public static Int instance() {return ourInstance;}

    public Int(){

    }

    public String name() {return "int";}
    public String typeName(){return "int";}
    public void nameAdd(String s){}
}
