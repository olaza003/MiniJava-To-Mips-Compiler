package hw2.prints;
import java.util.*;
import java.io.*;

public class CodeGenHelper {
    public String Add(String lhs, String rhs){
        return "Add(" + lhs + " " + rhs + ")";
    }

    public String Sub(String lhs, String rhs){
        return "Sub(" + lhs + " " + rhs + ")";
    }

    public String MulS(String lhs, String rhs){
        return "MulS(" + lhs + " " + rhs + ")";
    }

    public String Eq(String lhs, String rhs){
        return "Eq(" + lhs + " " + rhs + ")";
    }

    public String Lt(String lhs, String rhs){
        return "Lt(" + lhs + " " + rhs + ")";
    }
    public String LtS(String lhs, String rhs){
        return "LtS(" + lhs + " " + rhs + ")";
    }

    public String HeapAllocZ(int n){
        return "HeapAllocZ(" + Integer.toString(n) + ")";
    }

    public String AllocArray(int n){
        return "call :AllocArray(" + /*n.toString() +*/")";
    }

    public String Array(String lhs, String rhs){
        return "[" + lhs + "] = :vmt_" + rhs; //[t.0] = :vmt_Fac
    }

    //if t.0 goto :null1

    public String nullStr(String lhs, String rhs){
        return "if " + lhs + " goto :" + rhs;
    }
}
