import cs132.util.ProblemException;
import cs132.vapor.parser.VaporParser;
import cs132.vapor.ast.VDataSegment;
import cs132.vapor.ast.VFunction;
import cs132.vapor.ast.VOperand;
import cs132.vapor.ast.VaporProgram;
import cs132.vapor.ast.VBuiltIn.Op;

import java.io.*;

import java.util.*;

import hw3.*;

public class V2VM {
    public static String content = "";
    public static void processStream(InputStream stream){
        try{
            VaporProgram program = parseVapor(System.in, System.err);
            Allocator allocator = new Allocator();
            vConverter conv = new vConverter();

            conv.getSegments(program.dataSegments);
            //System.out.println(program.functions.);
            for(VFunction func : program.functions){
                System.out.println(func.ident);

                FlowGraph graph = RegAllocHelper.generateFlowGraph(func);
                Liveness liveness = graph.computeLiveness();
                List<IntervalNode> intervals = RegAllocHelper.generateLiveIntervals(graph, liveness); //'this' variable range incorrect, everything else correct
                AllocationMap map = allocator.computeAllocation(intervals, func.params);
                conv.outputFunction(func, map, liveness);
                printHelper(func, graph, liveness, intervals, map);
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
        printFiles();
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

    public static void printHelper(VFunction func, FlowGraph graph, Liveness liveness, List<IntervalNode> intervals, AllocationMap map){
        content += "function: " + func.ident + "\n";
        content += "==========Graph==========\n";
        List<FlowGraphNode> tempGraph = new ArrayList<>(graph.nodesList);
        for(FlowGraphNode node: tempGraph){
            String newContent = "node: " + node.functionNode.destination + " " + node.toString();
            newContent += "\n\tinList: " + Arrays.toString(node.in.toArray());
            newContent += "\n\toutList: " + Arrays.toString(node.out.toArray());
            newContent += "\n\tuseList: " + Arrays.toString(node.use.toArray());
            newContent += "\n\tdefList: " + Arrays.toString(node.def.toArray());
            newContent += "\n\tsuccessors: " + Arrays.toString(node.succNode.toArray());
            newContent += "\n\tpredecessors: " + Arrays.toString(node.predNode.toArray());
            newContent += "\n\tline number: " + node.instruction + "\n";
            content += newContent;
        }

        content += "==========Intervals==========\n";
        List<IntervalNode> tempInterval = new ArrayList<>(intervals);
        for(IntervalNode intNode: tempInterval){
            String temp = "variable: " + intNode.variable;
            temp += "\n\tstart: " + intNode.start;
            temp += "\n\tend: " + intNode.end;
            temp += "\n\tcallee: " + intNode.calle + "\n";
            content += temp;
        }

        content += "==========Register Map==========\n";
        HashMap<String, Register> tempMap = new HashMap<>(map.registerHashMap);
        for(String key: tempMap.keySet()){
            String temp = "variable: " + key;
            temp += "\n\tregister: " + tempMap.get(key).register + "\n";
            content += temp;
        }
        content += "\n\n\n";
    }

    public static void printFiles(){
        try{
            FileWriter file = new FileWriter("src\\tester.txt");
            file.write(content);
            file.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
