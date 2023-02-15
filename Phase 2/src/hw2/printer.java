package hw2;
import java.util.*;
//printer for state 1
public class printer {
    holders h;
    Map<String, Map<String, String>> vt;
    Map<String, Map<String, Integer>> vt2;
    public printer(holders hold){
        h = hold;
        //vt = hold.getVtable();
        vt2 = hold.getTempVtabel();

        /* Set entry1 = vt.entrySet();
        Iterator it = entry1.iterator();
        while(it.hasNext()){
            Set entry2 = it.entrySet();
        } */
    }

    /* public String print(){
        String output = "";
        for(String temp: vt.keySet() ){
            output += "const vmt_" + temp + "\n"; 
            for(String c : vt.get(temp).keySet()){
                output += "  :" + temp + "." + c + "\n";
            }
        }
        return output;
    } */

    public String print(){
        String output = "\n";
        for(String temp: vt2.keySet() ){
            output += "const vmt_" + temp + "\n"; 
            for(String c : vt2.get(temp).keySet()){
                output += "  :" + temp + "." + c + "\n";
            }
            output += "\n";
        }
        return output;
    }
}
/*
 * for (String temp : vtable.keySet()) {
            for (String c : vtable.get(temp).keySet()) {
                System.out.println("key--" + c + "--value--" + vtable.get(temp).get(c));
            }
            System.out.println("-------------");
        }
 */
