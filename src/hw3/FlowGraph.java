package hw3;

import cs132.vapor.ast.VInstr;

import java.util.ArrayList;
import java.util.List;

public class FlowGraph {
    public List<FlowGraphNode> nodesList = new ArrayList<FlowGraphNode>();

    public void addGraphNode(Nodes n){
        nodesList.add(new FlowGraphNode(n));
    }

    public void addGraphEdge(FlowGraphNode node1, FlowGraphNode node2){
        node1.succNode.add(node2);
        node2.predNode.add(node1);
    }

    public FlowGraphNode getNode(int bodyIndex){
        FlowGraphNode node = nodesList.get(bodyIndex);
        return node;
    }

}
