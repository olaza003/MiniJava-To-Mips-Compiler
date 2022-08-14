package hw3;

import cs132.vapor.ast.VCodeLabel;
import cs132.vapor.ast.VFunction;
import cs132.vapor.ast.VInstr;

import java.util.HashMap;

public class RegAllocHelper {

    public static FlowGraph generateFlowGraph(VFunction func){
        FlowGraph graph = new FlowGraph();
        IntervalVisit intervalVisitor = new IntervalVisit();
        VInstr[] funcBody = func.body;
        HashMap<String, Integer> labelMap = new HashMap<String, Integer>();

        for(int i = 0; i < funcBody.length; ++i){
            VInstr node = funcBody[i];
            //System.out.println(node.sourcePos.line + ": " + node.toString());
            //System.out.println(node.accept(intervalVisitor));
            System.out.print(node.sourcePos.line);
            node.accept(intervalVisitor);
        }
        System.out.println("yes");
        for(VCodeLabel label : func.labels){
            labelMap.put(label.ident, label.instrIndex);
            //System.out.println(label.instrIndex + ": " + label.ident);
        }


        return graph;
    }
}
