package hw3;
import cs132.vapor.ast.VDataSegment;
import cs132.vapor.ast.VFunction;
import cs132.vapor.ast.VInstr;
import cs132.vapor.ast.VOperand;

import java.io.FileWriter;

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
            decrementTab();

        }
    }

    public void outputFunction(VFunction func, AllocationMap map, Liveness liveness){
        fileOutput += "func " + func.ident + "\n";
        incrementTab();
        OutputVisit outputVisitor = new OutputVisit(map);

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
        tab = tab.substring(0, 2);
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

}
