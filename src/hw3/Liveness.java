package hw3;

import java.util.*;

public class Liveness {
    public List<String> inList;
    public List<String> outList;
    public List<String> defList;
    public List<String> useList;

    public Liveness(List<String> in, List<String> out, List<String> def, List<String> use){
        inList = in;
        outList = out;
        defList = def;
        useList = use;
    }
}
