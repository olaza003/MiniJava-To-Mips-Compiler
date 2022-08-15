package hw3;
import cs132.vapor.ast.VDataSegment;
import cs132.vapor.ast.VFunction;

public class vConverter {
    public void getSegments(VDataSegment[] convSegment){
        String output = "";
        int tabCount = 0;
        for(VDataSegment segment : convSegment){
            output += "const " + segment.ident + "\n";
        }
        System.out.println(output);
    }

    public void outputFunction(VFunction func, AllocationMap map, Liveness liveness){
        System.out.println("do something");
    }
}
