import cs132.util.ProblemException;
import cs132.vapor.parser.VaporParser;
import cs132.vapor.ast.VDataSegment;
import cs132.vapor.ast.VFunction;
import cs132.vapor.ast.VOperand;
import cs132.vapor.ast.VaporProgram;
import cs132.vapor.ast.VBuiltIn.Op;

import java.io.*;

import java.util.*;

import HelperFiles.*;

public class V2VM {
    public static String content = "";
    public static void processStream(InputStream stream){
        try{
            VaporProgram program = parseVapor(System.in, System.err);
            Allocator allocator = new Allocator();
            vConverter conv = new vConverter();

            conv.getSegments(program.dataSegments);
            for(VFunction func : program.functions){
                System.out.println(func.ident);

                FlowGraph graph = RegAllocHelper.generateFlowGraph(func);
                Liveness liveness = graph.computeLiveness();
                List<IntervalNode> intervals = RegAllocHelper.generateLiveIntervals(graph, liveness); //'this' variable range incorrect, everything else correct
                AllocationMap map = allocator.computeAllocation(intervals, func.params);
                conv.outputFunction(func, map, liveness);
            }
            System.out.println(conv.fileString);
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
}
