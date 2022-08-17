package hw3;

import cs132.vapor.ast.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OutputVisit extends VInstr.VisitorR<String, RuntimeException> {

    public AllocationMap map;
    public String tab;

    public OutputVisit(AllocationMap m, String indent){
        map = m;
        tab = indent;
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
        /*String LHS = "";
        String RHS = "";
        Register varReg = map.registerHashMap.get(vCall.dest.toString());
        LHS = varReg.register;
        Register addrReg = map.registerHashMap.get(vCall.addr.toString());
        RHS = "call " + addrReg.register + "(";
        for(VOperand arg: vCall.args){
            if(map.registerHashMap.containsKey(arg.toString())){
                RHS += map.registerHashMap.get(arg.toString()).register;
            }
            else
                RHS += arg.toString();
            if(arg != vCall.args[vCall.args.length-1])
                RHS += " ";
        }
        RHS += ")\n";*/
        String output = "";
        String[] aRegisters = {"$a0", "$a1", "$a2", "$a3"};
        for(int i = 0;i < vCall.args.length; ++i){
            if(i < 4){
                if(map.registerHashMap.containsKey(vCall.args[i].toString())){
                    if(i != 0)
                        output += aRegisters[0] + " = " + vCall.args[i].toString() + "\n";
                    else
                        output += tab + aRegisters[0] + " = " + vCall.args[i].toString() + "\n";
                }

                else {
                    output += tab + "out[" + Integer.toString(i - 4) + "] = ";
                    if(map.registerHashMap.containsKey(vCall.args[i].toString()))
                        output += map.registerHashMap.get(vCall.args[i].toString());
                    else
                        output += vCall.args[i].toString() + "\n";
                }
            }
        }
        return output;
    }

    @Override
    public String visit(VBuiltIn vBuiltIn) throws RuntimeException {
        if(vBuiltIn.dest != null) { //null when Error or no LHS
            String LHS = "";
            String RHS = "";
            Register varReg = map.registerHashMap.get(vBuiltIn.dest.toString());
            LHS = varReg.register;
            RHS = vBuiltIn.op.name + "(";
            for(VOperand arg: vBuiltIn.args){
                if(map.registerHashMap.containsKey(arg.toString())){
                    RHS += map.registerHashMap.get(arg.toString()).register;
                }
                else
                    RHS += arg.toString();
                if(arg != vBuiltIn.args[vBuiltIn.args.length-1])
                    RHS += " ";
            }
            RHS += ")";
            return LHS + " = " + RHS;
        }
        else{
            String operand = vBuiltIn.args[0].toString();
            String arg = (map.registerHashMap.containsKey(operand)) ? map.registerHashMap.get(operand).register : operand;
            String errorMsg = String.format(vBuiltIn.op.name + "(%s)", arg);
            return errorMsg;
        }
    }

    @Override
    public String visit(VMemWrite vMemWrite) throws RuntimeException {
        String LHS = "";
        String RHS = "";
        Register dest = map.registerHashMap.get(((VMemRef.Global)vMemWrite.dest).base.toString());
        LHS = String.format("[%s]", dest.register);
        RHS = vMemWrite.source.toString();
        return LHS + " = " + RHS;
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
