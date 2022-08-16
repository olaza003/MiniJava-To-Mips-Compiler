package hw3;

import java.util.ArrayList;
import java.util.List;

public class RegisterPool {

    public List<Register> regInUse = new ArrayList<>(); //list of registers currently in use
    public List<Register> allRegisters = new ArrayList<>();

    public RegisterPool(String[] regs){
        for(String reg: regs)
            allRegisters.add(new Register(reg));
    }

    public Register getRegister(){
        List<Register> unusedReg = allRegisters;
        Register newRegister = new Register("null");
        unusedReg.removeIf(reg -> regInUse.contains(reg));
        if(!unusedReg.isEmpty()){
            newRegister = unusedReg.get(0);
            unusedReg.remove(newRegister);
            regInUse.add(newRegister);
        }
        return newRegister;
    }

    public void freeUsed(Register reg){
        regInUse.remove(reg);
    }
}
