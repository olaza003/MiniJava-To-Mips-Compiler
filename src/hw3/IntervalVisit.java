package hw3;

import cs132.vapor.ast.*;

public class IntervalVisit extends VInstr.VisitorR<String, RuntimeException> {

    @Override
    public String visit(VAssign node) throws RuntimeException {
        String dest = node.dest.toString();
        String source = node.source.toString();
        System.out.println("VAssign dest: " + dest);
        System.out.println("VAssign source: " + source);
        return null;
    }

    @Override
    public String visit(VCall node) throws RuntimeException {
        String dest = node.dest.toString();
        System.out.println("VCall dest: " + dest);
        for(VOperand op : node.args){
            String oper = op.toString();
            System.out.println("VCall op: " + op);
        }
        return null;
    }

    @Override
    public String visit(VBuiltIn node) throws RuntimeException {
        String dest;
        if(node.dest != null) {
            dest = node.dest.toString();
            System.out.println("VbuiltIn dest: " + dest);
        }
        for(VOperand op : node.args){
            String oper = op.toString();
            System.out.println("VbuiltIn op: " + op);
        }
        return null;
    }

    @Override
    public String visit(VMemWrite node) throws RuntimeException {
        VMemRef.Global dest = (VMemRef.Global)node.dest;
        //String dest = node.dest.toString();
        String source = node.source.toString();
        System.out.println("VMemWrite dest: " + dest.base.toString());
        System.out.println("VMemWrite source: " + source);
        return null;
    }

    @Override
    public String visit(VMemRead node) throws RuntimeException {
        String dest = node.dest.toString();
        VMemRef.Global source = (VMemRef.Global)node.source;
        System.out.println("VMemRead dest: " + dest);
        System.out.println("VMemRead source: " + source.base.toString());
        return null;
    }

    @Override
    public String visit(VBranch node) throws RuntimeException {
        String source = node.value.toString();
        String target = node.target.toString();
        System.out.println("VBranch source: " + source);
        System.out.println("VBranch target: " + target);
        return null;
    }

    @Override
    public String visit(VGoto node) throws RuntimeException {
        String Goto = node.target.toString();
        System.out.println("VGoto target: " + Goto);
        return null;
    }

    @Override
    public String visit(VReturn node) throws RuntimeException {
        String retValue;
        if(node.value != null) {
            retValue = node.value.toString();
            System.out.println("VReturn value: " + retValue);
        }
        return null;
    }
}
