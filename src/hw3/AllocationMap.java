package hw3;

import java.util.HashMap;

public class AllocationMap {
    public HashMap<String, Register> registerHashMap;
    public HashMap<String, Register> localMap;
    public  AllocationMap(HashMap<String, Register> regMap){
        registerHashMap = regMap;
        localMap = new HashMap<>();
    }
}
