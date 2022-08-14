package hw3;

import cs132.vapor.ast.VFunction;
import cs132.vapor.ast.VInstr;

public class RegAllocHelper {

    public static FlowGraph generateFlowGraph(VFunction func){
        FlowGraph graph = new FlowGraph();
        IntervalVisit intervalVisitor = new IntervalVisit();
        VInstr[] funcBody = func.body;

        for(int i = 0; i < funcBody.length; ++i){
            VInstr node = funcBody[i];
            //System.out.println(node.sourcePos.line + ": " + node.toString());
            //System.out.println(node.accept(intervalVisitor));
            //System.out.println(node.sourcePos.line);
            node.accept(intervalVisitor);
        }
        return graph;
    }
}
