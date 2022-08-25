import cs132.vapor.ast.*;
public class Visitor extends VInstr.VisitorR<String, RuntimeException>{
    private static final String IDENT = "  ";
    public String destination = "";
    public String source = "";
    public String operation = "";
    public int offset = 0;

    @Override
    public String visit(VAssign vAssign) throws RuntimeException {
        System.out.println("VAssign");
        String str = "";
        if(vAssign.source instanceof VVarRef.Register){
            return IDENT + "move " + vAssign.dest.toString() + " " + vAssign.source.toString() + "\n";
        }
        else{
            return IDENT + "li "+ vAssign.dest.toString() + " " + vAssign.source.toString() + "\n";
        }
    }

    @Override
    public String visit(VCall vCall) throws RuntimeException {
        System.out.println("VCall");

        return IDENT + "jalr " + vCall.addr.toString() + "\n";
    }

    @Override
    public String visit(VBuiltIn vBuiltIn) throws RuntimeException {
        System.out.println("VBuiltin");
        String operator = vBuiltIn.op.name;
        VOperand[] hold = vBuiltIn.args;

        String str = "";
        System.out.println("op.name: " + vBuiltIn.op.name);

        if (operator == "Sub") {
            return IDENT + "subu " + vBuiltIn.dest.toString() + " " + vBuiltIn.args[0].toString() + " " +
                    vBuiltIn.args[1].toString() + "\n";
        }
        else if(operator == "HeapAllocZ"){
            str += IDENT + "li $a0 " + vBuiltIn.args[0].toString() + "\n";
            str += IDENT + "jal _heapAlloc\n";
            str += IDENT + "move " + vBuiltIn.dest.toString() + " $v0\n";
            return str;
        }
        else if(operator == "PrintIntS"){
            str += IDENT + "move $a0 " + vBuiltIn.args[0].toString() + "\n";
            str += IDENT + "jal _print\n";
            return str;
        }
        else if(operator == "Error"){
            str += IDENT + "la $a0 _str0\n";
            str += IDENT + "j _error\n";
            return str;
        } else if (operator == "LtS") {
            return IDENT + "slti " + vBuiltIn.dest + " " + vBuiltIn.args[0].toString() + " " + vBuiltIn.args[1].toString() + "\n";
        } else if (operator == "MulS") {
            return IDENT + "mul " + vBuiltIn.dest + " " + vBuiltIn.args[0].toString() + " " + vBuiltIn.args[1].toString() + "\n";
        } else if (operator == "Add") {
            return IDENT + "Add " + vBuiltIn.dest + " " + vBuiltIn.args[0].toString() + " " + vBuiltIn.args[1].toString() + "\n";
        }
        //str += "here: " + vBuiltIn.op.name + "\n";

        str += "error";
        return str;
    }

    @Override
    public String visit(VMemWrite vMemWrite) throws RuntimeException {
        //System.out.println("VMemWrite");
        String str = "";
        if(vMemWrite.dest instanceof VMemRef.Global){
            if(vMemWrite.source instanceof VVarRef.Register){
                destination = ((VMemRef.Global) vMemWrite.dest).base.toString();
                source = vMemWrite.source.toString();
                offset = ((VMemRef.Global) vMemWrite.dest).byteOffset;
                str += IDENT + String.format("sw %s %d(%s)\n", source, offset, destination);
            }
            else{
                String tempDest = "";
                operation = (vMemWrite.source instanceof VLabelRef) ? "la" : "li";
                destination = ((VMemRef.Global) vMemWrite.dest).base.toString();
                tempDest = "$t9";
                offset = ((VMemRef.Global) vMemWrite.dest).byteOffset;
                source = (vMemWrite.source instanceof VLabelRef) ? vMemWrite.source.toString().substring(1) : vMemWrite.source.toString();
                str += IDENT + String.format("%s %s %s\n", operation, tempDest, source);
                str += IDENT + String.format("sw %s %d(%s)\n", tempDest, offset, destination);
            }
        }
        else{ //if(vMemWrite.dest instanceof VMemRef.Stack)
            System.out.println("here");
            destination = "$sp";
            source = vMemWrite.source.toString();
            offset = ((VMemRef.Stack)vMemWrite.dest).index * 4;
            str += IDENT + String.format("sw %s %d(%s)\n", source, offset, destination);
        }return str;
    }

    @Override
    public String visit(VMemRead vMemRead) throws RuntimeException {
        //System.out.println("VMemRead");
        return IDENT + "vMemRead\n";
    }

    @Override
    public String visit(VBranch vBranch) throws RuntimeException {
        System.out.println("VBranch");
        if(vBranch.positive)
            return IDENT + "bnez " + vBranch.value.toString() + " " + vBranch.target.ident + "\n";
        else
            return IDENT + "beqz " + vBranch.value.toString() + " " + vBranch.target.ident + "\n";
    }

    @Override
    public String visit(VGoto vGoto) throws RuntimeException {
        System.out.println("VGoto");
        return IDENT + "j " + vGoto.target.toString().substring(1) + "\n";
    }

    @Override
    public String visit(VReturn vReturn) throws RuntimeException {
        System.out.println("VReturn");
        return "";
    }
}