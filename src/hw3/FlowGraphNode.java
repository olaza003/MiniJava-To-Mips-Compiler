package hw3;

import cs132.vapor.ast.VInstr;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FlowGraphNode{
    public List<FlowGraphNode> succNode; //successor node
    public List<FlowGraphNode> predNode; //predecessor node

    public List<String> in;
    public List<String> out;
    public List<String> use;
    public List<String> def;
    public Integer instruction;
    public Nodes functionNode;

    public FlowGraphNode(Nodes n, Integer lineNum){
        functionNode = n;
        succNode = new ArrayList<>();
        predNode = new ArrayList<>();
        instruction = lineNum;
    }
    public FlowGraphNode(Nodes n, Integer lineNum, List<String> d, List<String> src){
        functionNode = n;
        succNode = new ArrayList<>();
        predNode = new ArrayList<>();
        instruction = lineNum;
        def = d;
        use = src;
    }
}
