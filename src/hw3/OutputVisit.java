package hw3;

import cs132.vapor.ast.*;

public class OutputVisit extends VInstr.VisitorR<String, RuntimeException> {

    public AllocationMap map;

    public OutputVisit(AllocationMap m){
        map = m;
    }

    @Override
    public String visit(VAssign vAssign) throws RuntimeException {

        Register varReg = map.registerHashMap.get(vAssign.dest.toString());
        String LHS = varReg.register;
        String RHS = (vAssign.source instanceof  VVarRef) ? map.registerHashMap.get(vAssign.source.toString()).register : vAssign.source.toString();

        return LHS + " = " + RHS;
    }

    @Override
    public String visit(VCall vCall) throws RuntimeException {
        return null;
    }

    @Override
    public String visit(VBuiltIn vBuiltIn) throws RuntimeException {
        //Register varReg = map.registerHashMap.get(vBuiltIn.dest.toString());

        return null;
    }

    @Override
    public String visit(VMemWrite vMemWrite) throws RuntimeException {
        return null;
    }

    @Override
    public String visit(VMemRead vMemRead) throws RuntimeException {
        return null;
    }

    @Override
    public String visit(VBranch vBranch) throws RuntimeException {
        return null;
    }

    @Override
    public String visit(VGoto vGoto) throws RuntimeException {
        return null;
    }

    @Override
    public String visit(VReturn vReturn) throws RuntimeException {
        return null;
    }
}
