package hw3;
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
        //fileString += "func " + func.ident + "\n";
        HashMap<Integer, String> labelMap = new HashMap<>();
        for(VCodeLabel label: func.labels){
            labelMap.put(label.instrIndex, label.ident);
        }

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
        printArgs(func, map);

        OutputVisit outputVisitor = new OutputVisit(map, tab);
        for (int i = 0; i < func.body.length; ++i) {
            VInstr vInstr = func.body[i];

            if(labelMap.containsKey(i))
                printLabels(labelMap.get(i));

            String n = vInstr.accept(outputVisitor);

            fileString += tab + n + "\n";
        }
        fileString += "\n";
        decrementTab();
        writeToFile(fileString);
    }

    public void incrementTab(){
        tab += "  ";
    }

    public void decrementTab(){
        int decrease = tab.length() - 2;
        tab = tab.substring(0, decrease);
    }

    public void writeToFile(String output){
        try {
            outputFile = new FileWriter("src\\P.vaporm");
            outputFile.write(output);
            outputFile.close();
        }catch (Exception e){
            System.out.println("file error");
        }
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
        System.out.println("\nfunc " + func.ident + " [in " + in + ", out " + out + ", local " + local + "]");
        incrementTab();
    }

    public void printArgs(VFunction func, AllocationMap map){
        //local -> in -> out
        int i = 0;
        for(String key: map.localMap.keySet()){
            fileString += tab + "local[" + i + "] = " + map.localMap.get(key).register + "\n";
            i++;
        }
        i = 0;
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
    public void printLabels(String label){
        fileString += label + ":\n";
    }
}
