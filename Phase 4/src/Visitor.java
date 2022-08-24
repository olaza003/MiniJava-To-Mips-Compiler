import cs132.vapor.ast.*;
public class Visitor extends VInstr.VisitorR<String, RuntimeException>{
    @Override
    public String visit(VAssign vAssign) throws RuntimeException {
        //System.out.println("VAssign");
        return "vAssign";
    }

    @Override
    public String visit(VCall vCall) throws RuntimeException {
        //System.out.println("VCall");
        return "vCall";
    }

    @Override
    public String visit(VBuiltIn vBuiltIn) throws RuntimeException {
        //System.out.println("VBuiltin");
        return "vBuiltIn";
    }

    @Override
    public String visit(VMemWrite vMemWrite) throws RuntimeException {
        //System.out.println("VMemWrite");
        return "vMemWrite";
    }

    @Override
    public String visit(VMemRead vMemRead) throws RuntimeException {
        //System.out.println("VMemRead");
        return "vMemRead";
    }

    @Override
    public String visit(VBranch vBranch) throws RuntimeException {
        //System.out.println("VBranch");
        return "vBranch";
    }

    @Override
    public String visit(VGoto vGoto) throws RuntimeException {
        //System.out.println("VGoto");
        return "vGoto";
    }

    @Override
    public String visit(VReturn vReturn) throws RuntimeException {
        //System.out.println("VReturn");
        return "vReturn";
    }
}