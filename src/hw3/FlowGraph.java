package hw3;

import cs132.vapor.ast.VInstr;

import java.util.ArrayList;
import java.util.List;

public class FlowGraph {
    public List<FlowGraphNode> nodesList = new ArrayList<FlowGraphNode>();

    public void addGraphNode(VInstr currNode, String a, String b){
        FlowGraphNode newNode = new FlowGraphNode(currNode, a, b);
        nodesList.add(newNode);
    }

    public class FlowGraphNode{
        public int index;
        public List<FlowGraphNode> succNode; //successor node
        public List<FlowGraphNode> predNode; //predecessor node

        public String dest;
        public String source;
        public VInstr instr;

        public FlowGraphNode(VInstr node, String a, String b){
            instr = node;
            dest = a;
            source = b;
        }
    }
}
