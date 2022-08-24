package HelperFiles;
import cs132.vapor.ast.*;

import java.io.FileWriter;
import java.util.*;

public class vConverter {

    public String fileString;

    public FileWriter outputFile;

    public String tab;

    public vConverter(){
        fileString = "";
        tab = "";
    }

    public void getSegments(VDataSegment[] convSegment){
        for(VDataSegment segment : convSegment){
            fileString += "const " + segment.ident + "\n";
            incrementTab();
            for (VOperand.Static value : segment.values) {
                fileString += tab + value.toString() + "\n";
            }
            fileString += "\n";
            decrementTab();

        }
    }

    public void outputFunction(VFunction func, AllocationMap map, Liveness liveness){
        HashMap<Integer, List<String>> labelMap = labelGetter(func);

        int local = getLocal(map);
        int in = func.params.length - 4;
        if(in < 0) in = 0;
        int out = 0;

        //get the out value
        for(VInstr node : func.body){
            int holder = 0;
            if(node instanceof VCall) {
                VOperand[] v = ((VCall) node).args;
                holder = v.length - 4;
            }
            if(out < holder) out = holder;
        }

        printFuncLine(func, in, out, local);
        printArgs(func, map, local);

        OutputVisit outputVisitor = new OutputVisit(map, tab, local);
        for (int i = 0; i < func.body.length; ++i) {
            VInstr vInstr = func.body[i];
            printLabels(labelMap, i);
            String visitOutput = vInstr.accept(outputVisitor);

            fileString += tab + visitOutput + "\n";
        }
        fileString += "\n";
        decrementTab();
    }

    public void incrementTab(){
        tab += "  ";
    }

    public void decrementTab(){
        int decrease = tab.length() - 2;
        tab = tab.substring(0, decrease);
    }

    public Integer getLocal(AllocationMap map){
        HashMap<String, Register> temp = map.registerHashMap;
        int max = 0;
        for(String s : temp.keySet()){
            if(temp.get(s).register.charAt(1) == 's'){
                map.localMap.put(s, temp.get(s));
                char c = temp.get(s).register.charAt(2);
                int i = Character.getNumericValue(c);
                max = Math.max(max, i+1);
            }
        }
        return max;
    }

    public void printFuncLine(VFunction func, int in, int out, int local){
        fileString += "func " + func.ident + " [in " + in + ", out " + out + ", local " + local + "]\n";
        incrementTab();
    }

    public void printArgs(VFunction func, AllocationMap map, int local){
        //local -> in -> out
        if(local > 0){
            for(int i = 0; i < local; i++){
                fileString += tab + "local[" + i + "] = $s" + i + "\n";
            }
        }

        int i = 0;
        int var = 0;
        for(VVarRef.Local param :func.params){
            String paramIdent = param.ident;
            if(i < 4) {
                if (map.registerHashMap.containsKey(paramIdent)) {
                    fileString += tab + map.registerHashMap.get(paramIdent).register + String.format(" = $a%s\n", i);
                }
            }
            else{
                if (map.registerHashMap.containsKey(paramIdent)) {
                    fileString += tab + map.registerHashMap.get(paramIdent).register + String.format(" = in[%s]\n", var++);
                }
            }
            i++;
        }
    }
    public HashMap<Integer, List<String>> labelGetter(VFunction func){
        HashMap<Integer, List<String>> labelMap = new HashMap<>();
        for (VCodeLabel l : func.labels) {
            List<String> temp = new ArrayList<>();
            if(!labelMap.containsKey(l.instrIndex)) {  //if key in label hashmap isnt within hashmap
                temp.add(l.ident);
                labelMap.put(l.instrIndex, temp);
            }
            else{ //if key is found, means that there is another label at that instruction index and append to that list
                temp = new ArrayList<>(labelMap.get(l.instrIndex));
                temp.add(l.ident);
                labelMap.put(l.instrIndex, temp);
            }
        }
        return labelMap;
    }

    public void printLabels(HashMap<Integer, List<String>> labelMap, int key){
        if(labelMap.containsKey(key)) {
            for(String s: labelMap.get(key)){
                fileString += s + ":\n";
            }
        }
    }
}
