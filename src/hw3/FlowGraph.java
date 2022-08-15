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

    public Liveness computeLiveness(){

        for(FlowGraphNode n: nodesList){
           n.in = new ArrayList<>();
           n.out = new ArrayList<>();
        }
        int counter = 0;
        boolean[] check = new boolean[nodesList.size()];
        do{
            int i = 0;
            for(FlowGraphNode n: nodesList){
                List<String> inPrime = n.in; //in'[n] = in[n]
                List<String> outPrime = n.out; //out'[n] = out[n]
                //def = destination; use = source

                //in[n] = use[n] U (out[n] - def[n])
                List<String> temp = new ArrayList<>();
                temp = union(n.use, subtraction(n.out, n.def));
                //n.in = union(n.use, subtraction(n.out, n.def));

                List<String> temp2 = new ArrayList<>();
                //out[n] = U(in all s in the n success Node) in[s]
                for(FlowGraphNode s: n.succNode) { //union of the in of the succ instructions
                    //n.out = union(n.in, s.);
                    //AddArray(temp2, s.in);
                    temp2 = union(temp2, s.in);
                }

                n.in = temp;
                n.out = temp2;
                check[i++] = CheckSimilarity(n.in, inPrime, n.out, outPrime);
            }
            counter++;
        }while(!Alltrue(check));
        return
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

    public void AddArray(List<String> LHS, List<String> RHS){
        for(String str : RHS){
            LHS.add(str);
        }
    }

    public boolean CheckSimilarity(List<String> in1, List<String> in2, List<String> out1, List<String> out2){
        return (in1.equals(in2) && out1.equals(out2));
    }

    public boolean Alltrue(boolean[] b){
        for(int i = 0; i < b.length; i++){
            if(b[i] == false) return false;
        }
        return true;
    }

}
