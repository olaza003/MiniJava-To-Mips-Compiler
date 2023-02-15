
import java.io.InputStream;

import hw2.*;
import syntaxtree.Goal;

public class J2V{
    public static void processStream(InputStream stream){
        try{
            MiniJavaParser parser = new MiniJavaParser(stream);
            Goal root = parser.Goal();
            pass typecheck = new pass(root);
            //System.out.println("done");
        }
        catch (Exception e){
            System.out.println("Type error");
            e.printStackTrace();
        } 
        catch(Error e){
            System.out.println("error");
        }
    }
    public static void main(String args[]){
        processStream(System.in);
    }
}
