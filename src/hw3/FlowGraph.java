package hw3;

import cs132.vapor.ast.VInstr;

import java.util.ArrayList;
import java.util.List;

public class FlowGraph {
    public List<FlowGraphNode> nodesList = new ArrayList<FlowGraphNode>();

    public void addGraphNode(Nodes n, Integer lineNum){
        nodesList.add(new FlowGraphNode(n, lineNum));
    }

    public void addGraphEdge(FlowGraphNode prevNode, FlowGraphNode nextNode){
        prevNode.succNode.add(nextNode);
        nextNode.predNode.add(prevNode);
    }

    public FlowGraphNode getNode(int bodyIndex){
        FlowGraphNode node = nodesList.get(bodyIndex);
        return node;
    }

}
