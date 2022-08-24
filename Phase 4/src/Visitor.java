import cs132.vapor.ast.*;
public class Visitor extends VInstr.VisitorR<String, RuntimeException>{
    private static final String IDENT = "  ";
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
        //HeapAllocZ, PrintInts, Error, LtS, Sub, MulS, Add
        /*VOperand arg0 = vBuiltIn.args[0];
        VOperand arg1 = vBuiltIn.args[1];
        boolean Literals = false;
        int a0 = 0, a1 = 0;
        if(arg0 instanceof VLitInt && arg1 instanceof  VLitInt) { //check if both are actual numbers
            Literals = true;
            a0 = Integer.parseInt(arg0.toString());
            a1 = Integer.parseInt(arg1.toString());
        }
        boolean firstVal = false; //check if the first [arg[0]] is at least an actual number
        if(arg0 instanceof  VLitInt){
            firstVal = true;
            a0 = Integer.parseInt(arg0.toString());
        }*/

        String str = "";
        System.out.println("op.name: " + vBuiltIn.op.name);

        if (operator == "Sub") {
            /*if(Literals) {
                return IDENT + "li " +  vBuiltIn.dest.toString() + " " + (a0-a1);
            }
            else{
                if(firstVal){
                    str += IDENT + "li $t9 " + arg0.toString() + "\n";
                    str += IDENT + "sub " + vBuiltIn.dest.toString() +  " $t9 " + arg1.toString() + "\n";
                    return str;
                }
                else{

                }
            }*/
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
        System.out.println("VMemWrite");
        return "";
    }

    @Override
    public String visit(VMemRead vMemRead) throws RuntimeException {
        System.out.println("VMemRead");
        return "";
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
