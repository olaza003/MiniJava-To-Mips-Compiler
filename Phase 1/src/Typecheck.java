
import java.io.InputStream;

import syntaxtree.Goal;
import typecheck.*;
//launcher
public class Typecheck{
    public static void processStream(InputStream stream){
        try{
            MiniJavaParser parser = new MiniJavaParser(stream);
            Goal root = parser.Goal();
            System.out.println("tests");
            Typecheckpass1 typeChecker = new Typecheckpass1(root);
            System.out.println("test0");
            typeChecker.check(root);
            System.out.println("test");
            System.out.println(root.f0 + " " + root.f1 + " " + root.f2);
            System.out.println("Program type checked successfully");
        }
        catch (ParseException e){
            System.err.println("Parse Error.");
            //e.printStackTrace();
        }catch (Exception e){
            System.out.println("Type error");
            //e.printStackTrace();
        }
        catch (Error e){
            System.out.println("Type error");
           // e.printStackTrace();
        }
    }

    public static void main(String args[]){processStream(System.in);}
}
