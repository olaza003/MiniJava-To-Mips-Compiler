import cs132.vapor.ast.VCodeLabel;
import cs132.vapor.ast.VDataSegment;
import cs132.vapor.ast.VFunction;
import cs132.vapor.ast.VInstr;

import java.util.*;

public class Printer {
    public String dataSeg = "";
    public String dataFunc = "";
    public String ending = "";
    private static final String IDENT = "  ";
    private String ident = "";
    //handles the extra like .data, class with its methods or dataSegments
    public String printDataSeg(VDataSegment[] cov){
        dataSeg += ".data\n\n";
        increaseIdent();
        for(int i = 0; i < cov.length; i++){
            dataSeg += cov[i].ident + ":\n";
            for(int k = 0; k < cov[i].values.length; k++){
                dataSeg += ident + cov[i].values[k].toString().substring(1) + "\n";
            }
            dataSeg += "\n";
        }
        decreaseIdent();
        dataSeg += "\n" + ident + ".text\n\n";
        increaseIdent();
        dataSeg += ident + "jal Main\n";
        dataSeg += ident + "li $v0 10\n";
        dataSeg += ident + "syscall\n\n";
        decreaseIdent();
        return dataSeg;
    }

    public String printFunction(VFunction func){
        HashMap<Integer, List<String>> labelMap = labelGetter(func);
        VInstr[] funcBody = func.body;
        Visitor visit = new Visitor();
        //System.out.println(func.ident + ":\n");
        dataFunc += func.ident + ":\n";
        increaseIdent();

        dataFunc += ident + "sw $fp -8($sp)\n";
        dataFunc += ident + "move $fp $sp\n";
        dataFunc += ident + "subu $sp $sp " + Integer.toString((func.stack.out + func.stack.local + 2)*4) + "\n";
        dataFunc += ident + "sw $r -4($fp)\n";
        /*for(VCodeLabel label : func.labels){
            System.out.println(label.instrIndex + ": " + label.ident);
        }*/

        for(int i = 0; i < funcBody.length; i++){
            VInstr node = funcBody[i];
            printLabels(labelMap, i);
            String s = node.accept(visit);
            dataFunc += ident + s + "\n";
            //System.out.println(node);
        }

        dataFunc += ident + "lw $ra -4($fp)\n";
        dataFunc += ident + "lw $fp -8($fp)\n";
        dataFunc += ident + "addu $sp $sp " + Integer.toString((func.stack.out + func.stack.local + 2)*4) + "\n";
        dataFunc += ident + "jr $ra\n";
        decreaseIdent();

        dataFunc += "\n";
        return dataFunc;
    }

    public void increaseIdent(){ident += IDENT;}
    public void decreaseIdent(){ident = ident.substring(0, ident.length() - IDENT.length());}

    public String printEnd(){
        ending += "_print:\n";
        increaseIdent();
        ending += ident + "li $v0 1   # syscall: print integer\n";
        ending += ident + "syscall\n";
        ending += ident + "la $a0 _newline\n";
        ending += ident + "li $v0 4   # syscallL print string\n";
        ending += ident + "syscall\n";
        ending += ident + "jr $ra\n\n";

        ending += "_error:\n";
        ending += ident + "li $v0 4   # syscall: print string\n";
        ending += ident + "syscall";
        ending += ident + "li $v0 10   # syscall: exit\n";
        ending += ident + "syscall\n\n";

        ending += "_heapAlloc:\n";
        ending += ident + "li $v0 9   # syscall: sbrk\n";
        ending += ident + "syscall\n";
        ending += ident + "jr $ra\n\n";

        ending += ".data\n.align 0\n_newline: .asciiz \"\\n\"\n_str0: .asciiz \"null pointer\\n\"";
        return ending;
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
                dataFunc += s + ":\n";
            }
        }
    }
}