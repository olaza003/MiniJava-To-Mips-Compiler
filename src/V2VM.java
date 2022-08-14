import cs132.util.ProblemException;
import cs132.vapor.parser.VaporParser;
import cs132.vapor.ast.VDataSegment;
import cs132.vapor.ast.VFunction;
import cs132.vapor.ast.VOperand;
import cs132.vapor.ast.VaporProgram;
import cs132.vapor.ast.VBuiltIn.Op;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStream;

import hw3.*;

public class V2VM {
    public static void processStream(InputStream stream){
        try{
            VaporProgram program = parseVapor(System.in, System.err);
            vConverter conv = new vConverter();

            conv.getSegments(program.dataSegments);

            for(VFunction func : program.functions){
                System.out.println(func.ident);
                FlowGraph graph = RegAllocHelper.generateFlowGraph(func);
                System.out.println();
            }
        }
        catch (Exception e){
            System.out.println("Type error");
            e.printStackTrace();
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

      public void print(VaporProgram vprog){
          for(VDataSegment it : vprog.dataSegments){
            System.out.println("const " + it.ident);
            for(VOperand.Static ir : it.values){
                System.out.println("\t" + ir);
            }
          }
      }
}
