package hw2;
import java.util.*;

public class holders {
    LinkedHashMap<String, Map<String, Integer>> temp_record = new LinkedHashMap<>();
    //this is what gets printed
    //<Class, <Var, position>>
    LinkedHashMap<String, Map<String, Integer>> temp_vtable = new LinkedHashMap<>();

    //we get all the infromation from above map and transfer it to a real object and vtable records
    //this holds the actual values and indecies 
    LinkedHashMap<String, Map<String, Integer>> record = new LinkedHashMap<>();
    LinkedHashMap<String, Map<String, Integer>> vtable = new LinkedHashMap<>();

    Map<String, String> variables = new HashMap<>();
    //just stores all the variables it passes
    //<variables, method> 

    public holders(){}

    //we only add methods here when theres no class extends
    public void vtableAdder(String a, LinkedHashMap<String, Integer> m){ 
        //for simplicity both the temp and real would get updated
        int pos = 0;
        for(String s: m.keySet()){
            Map<String, Integer> inner = temp_vtable.get(a);
            if(inner == null){
                inner = new LinkedHashMap<>();
                temp_vtable.put(a, inner);
            }
            //int val = m.get(s);
            inner.put(s, pos); //val; removed this so that we have 
            pos++;
        }
        pos = 0;
        for(String s: m.keySet()){
            Map<String, Integer> inner = vtable.get(a);
            if(inner == null){
                inner = new LinkedHashMap<>();
                vtable.put(a, inner);
            }
            //int val = m.get(s);
            inner.put(s, pos);
            pos++;
        }
    }

    //same thing as above but this stores the variables instead and also no extends
    public void recordAdder(String a , LinkedHashMap<String, Integer> m){
        int pos = 0;
        for(String s: m.keySet()){
            Map<String, Integer> inner = temp_record.get(a);
            if(inner == null){
                inner = new LinkedHashMap<>();
                temp_record.put(a, inner);
            }
            //int val = m.get(s);
            inner.put(s, pos);
            pos++;
        }
        pos = 0;
        for(String s: m.keySet()){
            Map<String, Integer> inner = record.get(a);
            if(inner == null){
                inner = new LinkedHashMap<>();
                record.put(a, inner);
            }
            //int val = m.get(s);
            inner.put(s, pos);
            pos++;
        }
    }

    public Map<String, Map<String, Integer>> getTempRecord(){return temp_record;}
    public Map<String, Map<String, Integer>> getTempVtabel(){return temp_vtable;}
    public Map<String, Map<String, Integer>> getRecord(){return record;}
    public Map<String, Map<String, Integer>> getVtabel(){return vtable;}

    public int getOffsetRecord(String a){ //update this for both vtable and record
        int i;
        try{
            i = record.get(a).size() * 4;
        } catch(Exception e){
            i = 0;
        }
        return i + 4;
    }

    public int getOffsetVtable(String a, String b){
        int i;
        try{
            i = vtable.get(a).get(b) * 4;
        }catch(Exception e){
            i = 0;
        }
        return i;
    }

    public int getRecordOffset(String a, String b){
        int i = 0;
        try{
            i = record.get(a).get(b) * 4 + 4;
        }catch(Exception e){
            i = 0;
        }
        return i;
    }

    //class A extend B ==> A(a) B(b)
    public void ClassExtendRecord(String a, String b, LinkedHashMap<String, Integer> c){//for extends for record
        Map<String, Integer> temp = getRecordMap(b); //has the records from the extended class
        LinkedHashMap<String, Integer> temp1 = combineMap(temp, c);//new LinkedHashMap<>();
        int pos = 0;
        for(String s: temp1.keySet()){
            Map<String, Integer> inner = record.get(a);
            if(inner == null){
                inner = new LinkedHashMap<>();
                record.put(a, inner);
            }
            //int val = temp1.get(s);
            inner.put(s, pos);
            pos++;
        }

        //mainly adding stuff to temp_record
        int position = 0;
        for(String s: c.keySet()){
            Map<String, Integer> inner = temp_record.get(a);
            if(inner == null){
                inner = new LinkedHashMap<>();
                temp_record.put(a, inner);
            }
            //int val = m.get(s);
            inner.put(s, position);
            position++;
        }

       
        /* System.out.println("record");
        for(String s: record.keySet()){
            System.out.println(s + " = " + record.get(s));
        }
        

        System.out.println("temp_record: ");
        for(String s: temp_record.keySet()){
            System.out.println(s + " = " + temp_record.get(s));
        } */
    }

    public void ClassExtendVtable(String a, String b, LinkedHashMap<String, Integer> c){//for extends for record
        Map<String, Integer> temp = getVtableMap(b); //has the records from the extended class
        LinkedHashMap<String, Integer> temp1 = combineMap(temp, c);//new LinkedHashMap<>();
        int pos = 0;
        for(String s: temp1.keySet()){
            Map<String, Integer> inner = vtable.get(a);
            if(inner == null){
                inner = new LinkedHashMap<>();
                vtable.put(a, inner);
            }
            //int val = temp1.get(s);
            inner.put(s, pos);
            pos++;
        }
        
        int position = 0;
        for(String s: c.keySet()){
            Map<String, Integer> inner = temp_vtable.get(a);
            if(inner == null){
                inner = new LinkedHashMap<>();
                temp_vtable.put(a, inner);
            }
            //int val = m.get(s);
            inner.put(s, position); //val; removed this so that we have 
            position++;
        }

        /* System.out.println("vtable");
        for(String s: vtable.keySet()){
            System.out.println(s + " = " + vtable.get(s));
        }

        System.out.println("temp_vtable: ");
        for(String s: temp_vtable.keySet()){
            System.out.println(s + " = " + temp_vtable.get(s));
        } */

    }


    public Map<String, Integer> getRecordMap(String a){ //access the temp_record
        return temp_record.get(a);
    }

    public Map<String, Integer> getVtableMap(String a){ //access the temp_vtable
        return temp_vtable.get(a);
    }

    public LinkedHashMap<String, Integer> combineMap(Map<String, Integer> a, LinkedHashMap<String, Integer> b){
        LinkedHashMap<String, Integer> temp = new LinkedHashMap<>();
        int pos = 0;
        for(String s: a.keySet()){ //copies a(extended class) to temp
            pos = a.get(s);
            temp.put(s, pos);
        }

        for(String s: b.keySet()){
            if(!temp.containsKey(s)){
                pos++;
                temp.put(s,pos);
            }
        }

        return temp;
    }

    public boolean isValidRecord(String a, String b){ //true if the variable exist
        try{
            if(record.get(a).containsKey(b))
                return true;
        }catch(Exception e){
            return false;
        }
        
        return false;
    }

    public void getKeys(){
        for(String key: record.keySet())
            System.out.println(key);
    }

    public void VariablesAdd(String a, String b){
        variables.put(a, b);
    }

    public boolean isValid(String a){ //true if the variable exist
        if(variables.containsKey(a)){return true;}
        return false;
    }

    public void printVariable(){
        System.out.println("variable");
        for(String s: variables.keySet()){
            System.out.println(s + " = " + variables.get(s));
        }
    }
}
