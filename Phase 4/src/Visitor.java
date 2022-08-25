import cs132.vapor.ast.*;
public class Visitor extends VInstr.VisitorR<String, RuntimeException>{
    private static final String IDENT = "  ";
    @Override
    public String visit(VAssign vAssign) throws RuntimeException {
        //System.out.println("VAssign");
        String str = "";
        if(vAssign.source instanceof VVarRef.Register){
            return IDENT + "move " + vAssign.dest.toString() + " " + vAssign.source.toString() + "\n";
        }
        else if(vAssign.source instanceof VLitInt){
            return IDENT + "li "+ vAssign.dest.toString() + " " + vAssign.source.toString() + "\n";
        }
        else {
            return IDENT + "la "+ vAssign.dest.toString() + " " + vAssign.source.toString().substring(1) + "\n";
        }
    }

    @Override
    public String visit(VCall vCall) throws RuntimeException {
        //System.out.println("VCall");
        return vCall.addr instanceof VAddr.Var<?> ? IDENT + "jalr " + vCall.addr.toString() + "\n" : IDENT + "jal " + vCall.addr.toString().substring(1) + "\n";
        //return IDENT + "jalr " + vCall.addr.toString() + "\n";
    }

    @Override
    public String visit(VBuiltIn vBuiltIn) throws RuntimeException {
        //System.out.println("VBuiltin");
        String operator = vBuiltIn.op.name;
        VOperand[] hold = vBuiltIn.args;

        //VOperand arg0 = vBuiltIn.args[0];
        //VOperand arg1 = vBuiltIn.args[1];
        /*boolean Literals = false;
        int a0 = 0, a1 = 0;
        if(vBuiltIn.args[0] instanceof VLitInt && vBuiltIn.args[1] instanceof  VLitInt) { //check if both are actual numbers
            Literals = true;
            a0 = Integer.parseInt(vBuiltIn.args[0].toString());
            a1 = Integer.parseInt(vBuiltIn.args[1].toString());
        }
        boolean firstVal = false; //check if the first [arg[0]] is at least an actual number
        if(vBuiltIn.args[0] instanceof  VLitInt){
            firstVal = true;
            a0 = Integer.parseInt(vBuiltIn.args[0].toString());
        }*/
        int a0 = 0, a1 = 0;

        String str = "";
        //System.out.println("op.name: " + vBuiltIn.op.name);

        if (operator == "Sub") {
            boolean Literals = vBuiltIn.args[0] instanceof VLitInt && vBuiltIn.args[1] instanceof  VLitInt ? true : false;
            boolean firstVal = vBuiltIn.args[0] instanceof  VLitInt ? true : false;
            if(Literals){
                a0 = Integer.parseInt(vBuiltIn.args[0].toString());
                a1 = Integer.parseInt(vBuiltIn.args[1].toString());
                return IDENT + "li " + vBuiltIn.dest.toString() + " " + (a0-a1) + "\n";
            }
            else{
                if(firstVal){
                    a0 = Integer.parseInt(vBuiltIn.args[0].toString());
                    str += IDENT + "li $t9 " + a0 + "\n";
                    str += IDENT + "subu " + vBuiltIn.dest.toString() + " $t9 " + vBuiltIn.args[1].toString() + "\n";
                }
                else{
                    return IDENT + "subu " + vBuiltIn.dest.toString() + " " + vBuiltIn.args[0].toString() + " "
                            + vBuiltIn.args[1].toString() + "\n";
                }
            }
            /*return IDENT + "subu " + vBuiltIn.dest.toString() + " " + vBuiltIn.args[0].toString() + " " +
                    vBuiltIn.args[1].toString() + "\n";*/
        }
        else if(operator == "HeapAllocZ"){
            str += vBuiltIn.args[0] instanceof VLitInt ? IDENT + "li $a0 " + vBuiltIn.args[0].toString() + "\n" :IDENT + "move $a0 " + vBuiltIn.args[0].toString() + "\n";
            //str += IDENT + "li $a0 " + vBuiltIn.args[0].toString() + "\n";
            str += IDENT + "jal _heapAlloc\n";
            str += IDENT + "move " + vBuiltIn.dest.toString() + " $v0\n";
            return str;
        }
        else if(operator == "PrintIntS"){
            String s =vBuiltIn.args[0].toString();
            //str += IDENT + "move $a0 " + vBuiltIn.args[0].toString() + "\n";
            if(vBuiltIn.args[0] instanceof VLitInt) str += IDENT +"li $a0 " + s + "\n";
            else str += IDENT + "move $a0 " + s + "\n";
            str += IDENT + "jal _print\n";
            return str;
        }
        else if(operator == "Error"){
            String s = vBuiltIn.args[0].toString();
            if(s.equals("\"null pointer\"")){
                str += IDENT + "la $a0 _str0\n";
                str += IDENT + "j _error\n";
                return str;
            }
            else{
                str += IDENT + "la $a0 _str1\n";
                str += IDENT + "j _error\n";
                return str;
            }
        } else if (operator == "LtS") {
            boolean Literals = vBuiltIn.args[0] instanceof VLitInt && vBuiltIn.args[1] instanceof  VLitInt ? true : false;
            boolean firstVal = vBuiltIn.args[0] instanceof  VLitInt ? true : false;
            boolean secondVal = vBuiltIn.args[1] instanceof  VLitInt ? true : false;

            if(Literals){
                return IDENT + "slt " + vBuiltIn.dest.toString() + " " + vBuiltIn.args[0].toString() + " "
                            + vBuiltIn.args[1].toString() + "\n";
            }
            else{
                if(firstVal || secondVal){
                    return IDENT + "slti " + vBuiltIn.dest.toString() + " " + vBuiltIn.args[0].toString() + " "
                            + vBuiltIn.args[1].toString() + "\n";
                }
                else{
                    return IDENT + "slt " + vBuiltIn.dest.toString() + " " + vBuiltIn.args[0].toString() + " "
                            + vBuiltIn.args[1].toString() + "\n";
                }
            }
            //return IDENT + "slt " + vBuiltIn.dest + " " + vBuiltIn.args[0].toString() + " " + vBuiltIn.args[1].toString() + "\n";
        } else if (operator == "MulS") {
            boolean Literals = vBuiltIn.args[0] instanceof VLitInt && vBuiltIn.args[1] instanceof  VLitInt ? true : false;
            boolean firstVal = vBuiltIn.args[0] instanceof  VLitInt ? true : false;
            boolean secondVal = vBuiltIn.args[1] instanceof  VLitInt ? true : false;

            if(Literals){
                a0 = Integer.parseInt(vBuiltIn.args[0].toString());
                a1 = Integer.parseInt(vBuiltIn.args[1].toString());
                return IDENT + "li " + vBuiltIn.dest.toString() + " " + (a0*a1) + "\n";
            }
            else{
                if(firstVal && !secondVal){
                    a0 = Integer.parseInt(vBuiltIn.args[0].toString());
                    str += IDENT + "li $t9 " + a0 + "\n";
                    str += IDENT + "mul " + vBuiltIn.dest.toString() + " $t9 " + vBuiltIn.args[1].toString() + "\n";//vBuiltIn.args[1].toString()
                }
                else{
                    return IDENT + "mul " + vBuiltIn.dest.toString() + " " + vBuiltIn.args[0].toString() + " "
                            + vBuiltIn.args[1].toString() + "\n";
                }
            }
            //return IDENT + "mul " + vBuiltIn.dest + " " + vBuiltIn.args[0].toString() + " " + vBuiltIn.args[1].toString() + "\n";
        } else if (operator == "Add") {
            return IDENT + "addu " + vBuiltIn.dest + " " + vBuiltIn.args[0].toString() + " " + vBuiltIn.args[1].toString() + "\n";
        } else if (operator == "Lt") {
            boolean Literals = vBuiltIn.args[0] instanceof VLitInt && vBuiltIn.args[1] instanceof  VLitInt ? true : false;
            boolean firstVal = vBuiltIn.args[0] instanceof  VLitInt ? true : false;
            boolean secondVal = vBuiltIn.args[1] instanceof  VLitInt ? true : false;

            if(Literals){
               /* return IDENT + "slt " + vBuiltIn.dest.toString() + " " + vBuiltIn.args[0].toString() + " "
                        + vBuiltIn.args[1].toString() + "\n";*/
                return "Lt 1\n";
            }
            else{
                if(firstVal || secondVal){
                    /*return IDENT + "slti " + vBuiltIn.dest.toString() + " " + vBuiltIn.args[0].toString() + " "
                            + vBuiltIn.args[1].toString() + "\n";*/
                    //return "Lt 2\n";
                    if(firstVal){
                        str += IDENT + "li $t9 " + vBuiltIn.args[0].toString() + "\n";
                        str += IDENT + "sltu " + vBuiltIn.dest.toString() + " $t9 " + vBuiltIn.args[1].toString() + "\n";
                        return str;
                    }

                    if(secondVal){
                        str += IDENT + "li $t9 " + vBuiltIn.args[1].toString() + "\n";
                        str += IDENT + "sltu " + vBuiltIn.dest.toString() + " $t9 " + vBuiltIn.args[0].toString() + "\n";
                        return str;
                    }

                }
                else{
                    /*return IDENT + "slt " + vBuiltIn.dest.toString() + " " + vBuiltIn.args[0].toString() + " "
                            + vBuiltIn.args[1].toString() + "\n";*/
                    return IDENT + "sltu " + vBuiltIn.dest + " " + vBuiltIn.args[0].toString() + " " + vBuiltIn.args[1].toString() + "\n";
                }
            }
            
        }
        //str += "here: " + vBuiltIn.op.name + "\n";

        //str += "error\n";
        return str;
    }

    @Override
    public String visit(VMemWrite vMemWrite) throws RuntimeException {
        //System.out.println("VMemWrite");
        return IDENT + "vMemWrite\n";
    }

    @Override
    public String visit(VMemRead vMemRead) throws RuntimeException {
        //System.out.println("VMemRead");
        String destination = vMemRead.dest.toString();
        VMemRef s = vMemRead.source;
        if(vMemRead.source instanceof VMemRef.Global){
            return IDENT + "lw " + destination + " " + ((VMemRef.Global)s).byteOffset + "("
                    +((VMemRef.Global)s).base.toString() + ")\n";
        }
        else{
            //System.out.println("Checker : " + ((VMemRef.Stack)vMemRead.source).region.name());
            String str = IDENT + "lw " + destination + " " +(((VMemRef.Stack)s).index)*4;// + "($sp)\n";
            str += ((VMemRef.Stack)vMemRead.source).region.name() == "In" ? "($fp)\n" : "($sp)\n";
            return str;
        }
        //return IDENT + "vMemRead\n";
    }

    @Override
    public String visit(VBranch vBranch) throws RuntimeException {
        //System.out.println("VBranch");
        if(vBranch.positive)
            return IDENT + "bnez " + vBranch.value.toString() + " " + vBranch.target.ident + "\n";
        else
            return IDENT + "beqz " + vBranch.value.toString() + " " + vBranch.target.ident + "\n";
    }

    @Override
    public String visit(VGoto vGoto) throws RuntimeException {
        //System.out.println("VGoto");
        return IDENT + "j " + vGoto.target.toString().substring(1) + "\n";
    }

    @Override
    public String visit(VReturn vReturn) throws RuntimeException {
        //System.out.println("VReturn");
        return "";
    }
}