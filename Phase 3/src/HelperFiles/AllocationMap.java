package HelperFiles;

import java.util.*;

public class AllocationMap {
    public HashMap<String, Register> registerHashMap;
    public HashMap<String, Register> localMap;

    public List<IntervalNode> interStack;
    public  AllocationMap(HashMap<String, Register> regMap, List<IntervalNode> intervalStack){
        registerHashMap = regMap;
        localMap = new HashMap<>();
        interStack = intervalStack;
    }
}
