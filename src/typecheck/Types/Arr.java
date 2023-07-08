package typecheck.Types;
import syntaxtree.*;

public class Arr implements Scope{
    String typeHold;
    private static Arr ourInstance = new Arr();

    public static Arr instance() {return ourInstance;}

    public Arr(){}

    public String name() {return "array";}
    public String typeName(){return "array";}
    public void nameAdd(String s){}
}
