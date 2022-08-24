package HelperFiles;

public class Register {
    public String register;

    public IntervalNode interNode;


    //, "a0", "a1", "a2", "a3", "v0", "v1"};
    //according to regalloc.txt, "8 + 9 = 17 general use registers passed to register allocation algorithm."

    public Register(String reg, IntervalNode n){
        register = reg; interNode = n;
    }

    public Register(String reg){register = reg;}


}
