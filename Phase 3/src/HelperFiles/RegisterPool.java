package HelperFiles;

import java.util.*;

public class RegisterPool {

    public List<Register> regInUse = new ArrayList<>(); //list of registers currently in use
    public List<Register> allRegisters = new ArrayList<>();
    public List<Register> sRegister = new ArrayList<>();

    public RegisterPool(String[] regs, String[] sregs){
        for(String reg: regs)
            allRegisters.add(new Register(reg));

        for(String str : sregs) {
            Register temp = new Register(str);
            if(!regContains(allRegisters, temp))
                allRegisters.add(temp); //check later cx its overriden
            if(!regContains(sRegister, temp))
                sRegister.add(temp);
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
        List<Register> unusedReg = new ArrayList<>(allRegisters);//allRegisters;
        List<Register> unusedSreg = new ArrayList<>(sRegister);//sRegister;

        Register newRegister = new Register("null", iNode);
        unusedReg.removeIf(reg -> regContains(regInUse, reg));
        unusedSreg.removeIf(reg -> regContains(regInUse, reg));
        /*if(!unusedReg.isEmpty()){
            if(iNode.calle){
                newRegister = unusedSreg.get(0);
                unusedSreg.remove(newRegister);
                regInUse.add(newRegister);
            }else {
                newRegister = unusedReg.get(0);
                unusedReg.remove(newRegister);
                regInUse.add(newRegister);
            }
        }*/
        if(!unusedSreg.isEmpty() && iNode.calle){
            newRegister = unusedSreg.get(0);
            unusedSreg.remove(newRegister);
            regInUse.add(newRegister);
            addInode(newRegister, iNode);
            return newRegister;
        }
        newRegister = unusedReg.get(0);
        unusedReg.remove(newRegister);
        regInUse.add(newRegister);
        addInode(newRegister, iNode);
        return newRegister;
    }

    public void freeUsed(Register reg){
        regInUse.remove(reg);
        char temp = reg.register.charAt(1);

        if(temp == 's'){
            if(sRegister.size() == 0) {
                sRegister.add(reg);
            }
            else {
                for (int i = 0; i < sRegister.size(); ++i) {
                    int usedCount = Character.getNumericValue(reg.register.charAt(2));
                    int sRegCount = Character.getNumericValue(sRegister.get(i).register.charAt(2));
                    /*if(usedCount == 0) {
                        sRegister.add(0, reg);
                        break;
                    }
                    else if (usedCount == 7) {
                        sRegister.add(7, reg);
                        break;
                    }
                    else{
                        if(i != usedCount)
                            continue;
                        else {
                            sRegister.add(i, reg);
                            break;
                        }
                    }*/
                    if(i != usedCount)
                        continue;
                    else {
                        sRegister.add(i, reg);
                        break;
                    }
                }
            }
        }
    }

    public void addInode(Register reg, IntervalNode iNode){
        reg.interNode = iNode;
    }


    public boolean regContains(List<Register> regList, Register regChecked){
        for(int i = 0; i < regList.size(); ++i){
            if(regChecked.register.equals(regList.get(i).register)){
                return true;
            }
        }
        return false;
    }
}
