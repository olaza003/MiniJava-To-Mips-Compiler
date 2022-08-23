package HelperFiles;

import cs132.vapor.ast.VCall;
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
            if(node instanceof VCall) n.calle = true;
            graph.addGraphNode(n, node.sourcePos.line, n.destination, n.sources, node);
        }

        for(VCodeLabel label : func.labels){
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
                FlowGraphNode newNode = graph.getNode(labelMap.get(ifLabel));
                graph.addGraphEdge(currNode, newNode);
            }

            if(!currNode.functionNode.gotoLabelEmpty()){
                String gotoLabel = currNode.functionNode.getGotoLabel().substring(1);
                FlowGraphNode newNode = graph.getNode(labelMap.get(gotoLabel));
                graph.addGraphEdge(currNode, newNode);
                gotoCheck = true;
            }
        }
        return graph;
    }

    public static List<IntervalNode> generateLiveIntervals(FlowGraph graph, Liveness liveness){ //code ended up working without liveness
        HashMap<String, IntervalNode> intervalMap = new HashMap<>();                            //for some reason, not sure why
        for(int i = 0; i < graph.nodesList.size(); ++i){
            FlowGraphNode n = graph.nodesList.get(i);
            List<String> active;
            //active = graph.union(liveness.inList.get(i), liveness.defList.get(i));
            //tried working with liveness but using n ended up working for us
            active = graph.union(n.in, n.def);

            for(int j = 0; j < active.size(); ++j){
                String currString = active.get(j);
                if(intervalMap.containsKey(currString)){
                    intervalMap.get(currString).start = Math.min(intervalMap.get(currString).start, i);
                    intervalMap.get(currString).end = Math.max(intervalMap.get(currString).end, i);
                }else{
                    intervalMap.put(currString, new IntervalNode(i, i, currString)); //create interval object with start time and currString.
                                                                                  //end time is updated when currString is found in hashmap
                }
            }
        }

        for(FlowGraphNode n : graph.nodesList){
            List<String> temp = n.removeDuplicate(n.out, n.def);
            if(n.vi instanceof VCall){
                for(String str : temp){
                    intervalMap.get(str).setCalle();
                }
            }
        }

        List<IntervalNode> temp = new ArrayList<>();
        for(IntervalNode iNode : intervalMap.values())
            temp.add(iNode);
        return temp;
    }
}
