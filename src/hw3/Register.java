package hw3;

import java.util.*;

public class Register {
    public String register;

    public static String[] registers = {"t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7", "s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7"};
    //, "a0", "a1", "a2", "a3", "v0", "v1"};
    //according to regalloc.txt, "8 + 9 = 17 general use registers passed to register allocation algorithm."

    public Register(String reg){
        register = reg;
    }

    public static List<Register> createRegisterPool(){
        List<Register> regList = new ArrayList<>();
        for(String reg : registers){
            regList.add(new Register(reg));
        }
        return regList;
    }
}
