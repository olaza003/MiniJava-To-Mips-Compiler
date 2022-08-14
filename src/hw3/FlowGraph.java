package hw3;

import java.util.*;

public class FlowGraph {
    public List<FlowGraphNode> nodesList = new ArrayList<>();

    public void addGraphNode(Nodes n, Integer lineNum, List<String> d, List<String> src){
        nodesList.add(new FlowGraphNode(n, lineNum, d, src));
    }

    public void addGraphEdge(FlowGraphNode prevNode, FlowGraphNode nextNode){
        prevNode.succNode.add(nextNode);
        nextNode.predNode.add(prevNode);
    }

    public FlowGraphNode getNode(int bodyIndex){
        FlowGraphNode node = nodesList.get(bodyIndex);
        return node;
    }

    public void computeLiveness(){

        for(FlowGraphNode n: nodesList){
           n.in = new ArrayList<>();
           n.out = new ArrayList<>();
        }
        do{
            for(FlowGraphNode n: nodesList){
                List<String> inPrime = n.in;
                List<String> outPrime = n.out;
                n.in = union(n.use, subtraction(n.out, n.def));
                for(FlowGraphNode s: n.succNode)
                    n.out = union(n.in, s.);
            }
        }while()
    }

    public ArrayList<String> union(List<String> LHS, List<String> RHS){
        Set<String> tempSet = new HashSet<>();
        tempSet.addAll(LHS);
        tempSet.addAll(RHS);
        return new ArrayList<String>(tempSet);
    }

    public ArrayList<String> intersection(List<String> LHS, List<String> RHS){
        ArrayList<String> tempList = new ArrayList<>();

        for(String s: LHS)
            if(RHS.contains(s))
                tempList.add(s);
        return tempList;
    }

    public ArrayList<String> subtraction(List<String> LHS, List<String> RHS) {
        ArrayList<String> tempList = new ArrayList<>();
        for(String s : LHS){
            if(!RHS.contains(s))
                tempList.add(s);
        }
        return tempList;
    }
}
