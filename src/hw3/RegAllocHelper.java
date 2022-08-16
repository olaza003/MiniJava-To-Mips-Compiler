package hw3;

import cs132.vapor.ast.VCodeLabel;
import cs132.vapor.ast.VFunction;
import cs132.vapor.ast.VInstr;

import java.util.*;

public class RegAllocHelper {

    public static FlowGraph generateFlowGraph(VFunction func){
        FlowGraph graph = new FlowGraph();
        IntervalVisit intervalVisitor = new IntervalVisit();
        VInstr[] funcBody = func.body;
        HashMap<String, Integer> labelMap = new HashMap<>();

        for(int i = 0; i < funcBody.length; ++i){
            VInstr node = funcBody[i];
            Nodes n = funcBody[i].accept(intervalVisitor);
            graph.addGraphNode(n, node.sourcePos.line, n.destination, n.sources);
            //System.out.println(node.sourcePos.line + ": " + node.toString());
            //System.out.println(node.accept(intervalVisitor));
            //System.out.println(node.sourcePos.line);
            //node.accept(intervalVisitor);
        }

        for(VCodeLabel label : func.labels){
            System.out.println(label.instrIndex + ": " + label.ident);
            labelMap.put(label.ident, label.instrIndex);
        }
        boolean gotoCheck = false;
        for(int i = 0; i < funcBody.length; ++i){
            //Nodes n = funcBody[i].accept(intervalVisitor);
            FlowGraphNode currNode = graph.getNode(i);
            FlowGraphNode prevNode = (i == 0) ? null : graph.getNode(i-1);

            if(gotoCheck) {
                prevNode = null;
                gotoCheck = false;
            }

            if(prevNode != null)
                graph.addGraphEdge(prevNode, currNode);

            if(!currNode.functionNode.ifLabelEmpty()){
                String ifLabel = currNode.functionNode.getIfLabel().substring(1);
                System.out.println(labelMap.get(ifLabel));
                FlowGraphNode newNode = graph.getNode(labelMap.get(ifLabel));
                graph.addGraphEdge(currNode, newNode);
                System.out.println();
            }

            if(!currNode.functionNode.gotoLabelEmpty()){
                System.out.println();
                String gotoLabel = currNode.functionNode.getGotoLabel().substring(1);
                System.out.println(gotoLabel);
                FlowGraphNode newNode = graph.getNode(labelMap.get(gotoLabel));
                graph.addGraphEdge(currNode, newNode);
                System.out.println();
                gotoCheck = true;
            }

            System.out.println("here");
        }
        return graph;
    }

    public static List<IntervalNode> generateLiveIntervals(FlowGraph graph, Liveness liveness){

        HashMap<String, IntervalNode> intervalMap = new HashMap<>();
        for(int i = 0; i < graph.nodesList.size(); ++i){
            FlowGraphNode n = graph.nodesList.get(i);
            List<String> active;
            active = graph.union(liveness.inList.get(i), liveness.defList.get(i));

            for(int j = 0; j < active.size(); ++j){
                String currString = active.get(j);
                if(intervalMap.containsKey(currString)){
                    intervalMap.get(currString).end = i;
                }else{
                    intervalMap.put(currString, new IntervalNode(i, currString)); //create interval object with start time and currString.
                                                                                  //end time is updated when currString is found in hashmap
                    if(n.functionNode.calle)
                        intervalMap.get(currString).calle = true;
                }
            }
        }
        System.out.println();
        List<IntervalNode> temp = new ArrayList<>();
        for(IntervalNode iNode : intervalMap.values())
            temp.add(iNode);
        return temp;
    }
}
