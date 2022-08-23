package HelperFiles;

import cs132.vapor.ast.*;

import java.util.*;

public class IntervalVisit extends VInstr.VisitorR<Nodes, RuntimeException> {

    @Override
    public Nodes visit(VAssign node) throws RuntimeException {
        List<String> temp1 = new ArrayList<>();
        String dest = node.dest.toString();
        if(dest != null && Character.isLetter(dest.charAt(0))){
            temp1.add(dest);
        }

        String source = node.source.toString();
        List<String> temp2 = new ArrayList<>();
        if(source != null && Character.isLetter(source.charAt(0))){
            temp2.add(source);
        }

        Nodes n = new Nodes(temp1, temp2);
        return n;
    }

    @Override
    public Nodes visit(VCall node) throws RuntimeException {
        String dest = node.dest.toString();
        List<String> temp1 = new ArrayList<>();
        if(dest != null && Character.isLetter(dest.charAt(0))){
            temp1.add(dest);
        }

        List<String> temp2 = new ArrayList<>();
        for(int i = 0; i < node.args.length; i++){
            String oper = node.args[i].toString();
            if(oper != null && Character.isLetter(oper.charAt(0))){
                temp2.add(oper);
            }
        }
        String callAddr = node.addr.toString();
        temp2.add(callAddr);
        Nodes n = new Nodes(temp1, temp2);
        n.calle = true;
        return n;
    }

    @Override
    public Nodes visit(VBuiltIn node) throws RuntimeException {
        List<String> temp1 = new ArrayList<>();
        if(node.dest != null ) {
            String dest = node.dest.toString();
            if(Character.isLetter(dest.charAt(0))){
                temp1.add(dest);
            }

        }

        List<String> temp2 = new ArrayList<>();
        for(int i = 0; i < node.args.length; i++){
            String oper = node.args[i].toString();
            if(oper != null && Character.isLetter(oper.charAt(0))){
                temp2.add(oper);
            }
        }
        Nodes n = new Nodes(temp1, temp2);
        return n;
    }

    @Override
    public Nodes visit(VMemWrite node) throws RuntimeException {
        List<String> temp1 = new ArrayList<>();
        //VMemRef.Global dest = (VMemRef.Global)node.dest;

        List<String> temp2 = new ArrayList<>();
        String dest = ((VMemRef.Global)node.dest).base.toString();
        if(dest != null && Character.isLetter(dest.charAt(0))){
            //temp1.add(dest);
            temp2.add(dest);
        }
        String source = node.source.toString();
        if(source != null && Character.isLetter(source.charAt(0))){
            temp2.add(source);
        }
        Nodes n = new Nodes(temp1, temp2);
        return n;
    }

    @Override
    public Nodes visit(VMemRead node) throws RuntimeException {
        String dest = node.dest.toString();
        List<String> temp1 = new ArrayList<>();
        if(dest != null && Character.isLetter(dest.charAt(0))){
            temp1.add(dest);
        }

        String source = ((VMemRef.Global)node.source).base.toString();
        List<String> temp2 = new ArrayList<>();
        if(source != null && Character.isLetter(source.charAt(0))){
            temp2.add(source);
        }
        Nodes n = new Nodes(temp1, temp2);
        return n;
    }

    @Override
    public Nodes visit(VBranch node) throws RuntimeException {
        String source = node.value.toString();
        String target = node.target.toString();

        List<String> temp2 = new ArrayList<>();
        if(source != null && Character.isLetter(source.charAt(0))){
            temp2.add(source);
        }
        Nodes n = new Nodes(new ArrayList<>(), temp2);
        n.storeIfLabel(target);
        return n;
    }

    @Override
    public Nodes visit(VGoto node) throws RuntimeException {
        String Goto = node.target.toString();
        String label = node.target.toString().substring(1,node.target.toString().length());

        Nodes n = new Nodes(new ArrayList<>(), new ArrayList<>());
        n.storeGotoLabel(Goto);
        return n;
    }

    @Override
    public Nodes visit(VReturn node) throws RuntimeException {
        String retValue;
        List<String> temp2 = new ArrayList<>();
        if(node.value != null ) {
            retValue = node.value.toString();
            if(Character.isLetter(retValue.charAt(0))){
                temp2.add(retValue);
            }
            System.out.println("VReturn value: " + retValue);
        }
        Nodes n = new Nodes(new ArrayList<>(), temp2);
        return n;
    }

}
