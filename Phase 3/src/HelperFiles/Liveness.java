package HelperFiles;

import java.util.*;

public class Liveness {
    public List<List<String>> inList;
    public List<List<String>> outList;
    public List<List<String>> defList;
    public List<List<String>> useList;

    public Liveness(List<List<String>> in, List<List<String>> def, List<List<String>> use){
        inList = in;
        defList = def;
        useList = use;
    }

    public Liveness(List<List<String>> in, List<List<String>> out, List<List<String>> def, List<List<String>> use){
        inList = in;
        outList = out;
        defList = def;
        useList = use;
    }
}
