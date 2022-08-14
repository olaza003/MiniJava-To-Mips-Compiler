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
            //VInstr node = funcBody[i];
            Nodes n = funcBody[i].accept(intervalVisitor);
            graph.addGraphNode(n);
            //System.out.println(node.sourcePos.line + ": " + node.toString());
            //System.out.println(node.accept(intervalVisitor));
            //System.out.println(node.sourcePos.line);
            //node.accept(intervalVisitor);
        }

        for(VCodeLabel label : func.labels){
            System.out.println(label.instrIndex + ": " + label.ident);
        }

        for(int i = 0; i < funcBody.length; ++i){
            //Nodes n = funcBody[i].accept(intervalVisitor);
            FlowGraphNode currNode = graph.getNode(i);
            FlowGraphNode prevNode;
            if(i == 0)
                prevNode = null;
            else
                prevNode = graph.getNode(i-1);

            if(prevNode != null)
                graph.addGraphEdge(prevNode, currNode);
        }

        return graph;
    }
}
