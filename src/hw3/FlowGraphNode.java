package hw3;

import cs132.vapor.ast.VInstr;

import java.util.ArrayList;
import java.util.List;
public class FlowGraphNode{
    public int index;
    public List<FlowGraphNode> succNode; //successor node
    public List<FlowGraphNode> predNode; //predecessor node
    public Integer instruction;
    public Nodes functionNode;

    public FlowGraphNode(Nodes n, Integer lineNum){
        functionNode = n;
        succNode = new ArrayList<FlowGraphNode>();
        predNode = new ArrayList<FlowGraphNode>();
        instruction = lineNum;
    }
}
