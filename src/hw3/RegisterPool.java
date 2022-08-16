package hw3;

import java.util.ArrayList;
import java.util.List;

public class RegisterPool {

    public List<Register> regInUse = new ArrayList<>(); //list of registers currently in use
    public List<Register> allRegisters = new ArrayList<>();
    public List<Register> sRegister = new ArrayList<>();

    public RegisterPool(String[] regs, String[] sregs){
        for(String reg: regs)
            allRegisters.add(new Register(reg));

        for(String str : sregs) {
            allRegisters.add(new Register(str)); //check later cx its overriden
            sRegister.add(new Register(str));
        }
    }

    /*public Register getRegister(){
        List<Register> unusedReg = new ArrayList<>(allRegisters);
        Register newRegister = new Register("null");
        unusedReg.removeIf(reg -> regInUse.contains(reg));
        if(!unusedReg.isEmpty()){
            newRegister = unusedReg.get(0);
            unusedReg.remove(newRegister);
            regInUse.add(newRegister);
        }
        return newRegister;
    }*/

    public Register getRegister(IntervalNode iNode){
        List<Register> unusedReg = new ArrayList<>(allRegisters);
        List<Register> unusedSreg = new ArrayList<>(sRegister);

        Register newRegister = new Register("null", iNode);
        unusedReg.removeIf(reg -> regInUse.contains(reg));
        unusedSreg.removeIf(reg -> regInUse.contains(reg));
        if(!unusedReg.isEmpty()){
            if(iNode.calle){
                newRegister = unusedSreg.get(0);
                unusedSreg.remove(newRegister);
                regInUse.add(newRegister);
            }else {
                newRegister = unusedReg.get(0);
                unusedReg.remove(newRegister);
                regInUse.add(newRegister);
            }
        }
        addInode(newRegister, iNode);
        return newRegister;
    }

    public void freeUsed(Register reg){
        regInUse.remove(reg);
    }

    public void addInode(Register reg, IntervalNode iNode){
        reg.interNode = iNode;
    }
}
