import cs132.util.ProblemException;
import cs132.vapor.parser.VaporParser;
import cs132.vapor.ast.VaporProgram;
import cs132.vapor.ast.VBuiltIn.Op;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStream;



public class V2VM {
    public static void processStream(InputStream stream){
        try{
            //MiniJavaParser parser = new MiniJavaParser(stream);
            //Goal root = parser.Goal();
            //pass typecheck = new pass(root);
            System.out.println("done");
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


    public static VaporProgram parseVapor(InputStream in, PrintStream err) throws IOException {
        Op[] ops = {
          Op.Add, Op.Sub, Op.MulS, Op.Eq, Op.Lt, Op.LtS,
          Op.PrintIntS, Op.HeapAllocZ, Op.Error,
        };
        boolean allowLocals = true;
        String[] registers = null;
        boolean allowStack = false;
      
        VaporProgram tree;
        try {
          tree = VaporParser.run(new InputStreamReader(in), 1, 1,
                                 java.util.Arrays.asList(ops),
                                 allowLocals, registers, allowStack);
        }
        catch (ProblemException ex) {
          err.println(ex.getMessage());
          return null;
        }
      
        return tree;
      }
}
