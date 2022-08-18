package hw3;

import cs132.vapor.ast.VCall;
import cs132.vapor.ast.VInstr;

import java.sql.SQLOutput;
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

    public boolean callee = false;

    public VInstr vi;

    public FlowGraphNode(Nodes n, Integer lineNum){
        functionNode = n;
        succNode = new ArrayList<>();
        predNode = new ArrayList<>();
        instruction = lineNum;
    }
    public FlowGraphNode(Nodes n, Integer lineNum, List<String> d, List<String> src, VInstr v){
        functionNode = n;
        succNode = new ArrayList<>();
        predNode = new ArrayList<>();
        instruction = lineNum;
        def = d;
        use = src;
        vi = v;
        if(v instanceof VCall) callee = true;
    }

    public List<String> removeDuplicate(List<String> in, List<String> out){
        List<String> temp = new ArrayList<>();

        for(String str : in){
            if(!out.contains(str)){
                temp.add(str);
            }
        }
        return temp;
    }
}
