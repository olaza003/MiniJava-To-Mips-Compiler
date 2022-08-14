package hw3;

import java.util.List;

public class FlowGraph {


    public class FlowGraphNode{
        public int index;
        public List<FlowGraphNode> nextNode;
        public List<FlowGraphNode> prevNode;
    }
}
