package hw3;
import cs132.vapor.ast.*;

import java.io.FileWriter;
import java.util.HashMap;

public class vConverter {

    public String fileOutput;

    public FileWriter outputFile;

    public String tab;

    public vConverter(){
        fileOutput = "";
        tab = "";
    }

    public void getSegments(VDataSegment[] convSegment){
        for(VDataSegment segment : convSegment){
            fileOutput += "const " + segment.ident + "\n";
            incrementTab();
            for (VOperand.Static value : segment.values) {
                fileOutput += tab + value.toString() + "\n";
            }
            fileOutput += "\n";
            decrementTab();

        }
    }

    public void outputFunction(VFunction func, AllocationMap map, Liveness liveness){
        //fileOutput += "func " + func.ident + "\n";
        int labelOffset = 0;
        Boolean labelFinished = false;

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



        fileOutput += "func " + func.ident + " [in " + in + ", out " + out + ", local " + local + "]\n";
        System.out.println("\nfunc " + func.ident + " [in " + in + ", out " + out + ", local " + local + "]");
        incrementTab();
        OutputVisit outputVisitor = new OutputVisit(map, tab);
        for (int i = 0; i < func.body.length; ++i) {
            VInstr vInstr = func.body[i];
            if(func.labels.length != 0 && !labelFinished) {
                if (i == func.labels[i - labelOffset].instrIndex) {
                    printLabels(func.labels[i - labelOffset]);
                    if((i-labelOffset) == (func.labels.length-1))
                        labelFinished = true;
                }
                else
                    labelOffset++;

            }
            String n = vInstr.accept(outputVisitor);

            fileOutput += tab + n + "\n";
        }
        fileOutput += "\n";
        decrementTab();
        writeToFile(fileOutput);
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
                char c = temp.get(s).register.charAt(2);
                int i = Character.getNumericValue(c);
                max = Math.max(max, i+1);
            }
        }
        return max;
    }

    public void printLabels(VCodeLabel func){
        decrementTab();
        fileOutput += func.ident + ":\n";
        incrementTab();
    }

}
