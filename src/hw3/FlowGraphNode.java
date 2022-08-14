package hw3;

import java.util.ArrayList;
import java.util.List;
public class FlowGraphNode{
    public int index;
    public List<FlowGraphNode> succNode; //successor node
    public List<FlowGraphNode> predNode; //predecessor node

    public Nodes functionNode;

    public FlowGraphNode(Nodes n){
        functionNode = n;
        succNode = new ArrayList<FlowGraphNode>();
        predNode = new ArrayList<FlowGraphNode>();
    }
}
