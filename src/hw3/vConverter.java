package hw3;
import cs132.vapor.ast.VDataSegment;
import cs132.vapor.ast.VFunction;
import cs132.vapor.ast.VInstr;
import cs132.vapor.ast.VOperand;

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

        OutputVisit outputVisitor = new OutputVisit(map);
        int local = getLocal(map);
        int in = func.params.length - 4;
        if(in < 0) in = 0;
        int out = 0;

        fileOutput += "func " + func.ident + " [in " + in + ", out " + out + ", local " + local + "]";
        System.out.println("\nfunc " + func.ident + " [in " + in + ", out " + out + ", local " + local + "]");
        incrementTab();
        for (VInstr vInstr : func.body) {
            String n = vInstr.accept(outputVisitor);
            fileOutput += tab + n + "\n";
        }
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

}
