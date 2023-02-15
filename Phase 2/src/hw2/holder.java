package hw2;
import java.util.*;

public class holder {
    String SC; //super class
    LinkedHashMap<String, Map<String, String>> object = new LinkedHashMap<>(); //hold variables
    //Class, <var, element>>
    LinkedHashMap<String, Map<String, String>> vtable = new LinkedHashMap<>(); //hold methods
    //class, < method, element>>

    LinkedHashMap<String, Map<String, String>> vtable2 = new LinkedHashMap<>(); //hold methods2


    //--holds the real stuff with the extends 
    LinkedHashMap<String, Map<String, String>> vt = new LinkedHashMap<>(); //vtable but updated
    LinkedHashMap<String, Map<String, String>> variable = new LinkedHashMap<>();

    Map<String, String> Class_extend = new HashMap<>();

    public holder(){} //mayb change the Map in the middle to LinkedHashMap and use addMethod to add all 
                      //in order after reaching new class

    public void addMethod(String a, String b, String c ){
        //vtable.add()
        Map<String, String> inner = vtable.get(a);
        if(inner == null){
            inner = new HashMap<>();
        vtable.put(a, inner);
        }
        inner.put(b, c);
    }

    public void addExtendClass(String cl, String ex){ //class B extends A: cl(B); ex(A)
        Class_extend.put(cl, ex);
    }

    public void MethodAdder(String a, LinkedHashMap<String, String> m){//Init=Element
        //vtable2.put(a, m);
        
        for(String s : m.keySet()){
            Map<String, String> inner = vtable2.get(a);
            if(inner == null){
                inner = new LinkedHashMap<>();
                vtable2.put(a,inner);
            }
            String str = m.get(s);
            inner.put(s, str);
        }
    }

    public void VariableAdder(String a, LinkedHashMap<String, String> m){
        
        for(String s : m.keySet()){
            Map<String, String> inner = variable.get(a);
            if(inner == null){
                inner = new LinkedHashMap<>();
                variable.put(a,inner);
            }
            String str = m.get(s);
            inner.put(s, str);
        }
    }

    public void addObject(String a, String b, String c){
        Map<String, String> inner = object.get(a);
        System.out.println("Storing");
        if(inner == null){
            inner = new HashMap<>();
            object.put(a, inner);
        }
        inner.put(b, c);
    }

    public void SuperClass(String a){SC = a;}

    public void print(){
        System.out.println("Object:\n");
        for (String temp : object.keySet()) {
            for (String c : object.get(temp).keySet()) {
                System.out.println("key--" + c + "--value--" + object.get(temp).get(c));
            }
            System.out.println("-------------");
        }
        System.out.println("\n\nVtable: \n");
        for (String temp : vtable.keySet()) {
            for (String c : vtable.get(temp).keySet()) {
                System.out.println("key--" + c + "--value--" + vtable.get(temp).get(c));
            }
            System.out.println("-------------");
        }

    }

    public Map<String, Map<String, String>> getVtable(){
        return vtable;
    }

    public Map<String, Map<String, String>> getVtable2(){
        return vtable2;
    }

    public int vt2_size(String str){
        return vtable2.get(str).size();
      }

    public int getOffset(String str){//pass in the class and get the offset 
        return object.get(str).size();
    }

    public void updateBothTable(){
        vt = vtable2;
        variable = object;
        System.out.println("Size of vt: " + vt.size());
         for(String temp : Class_extend.keySet()){//LHS:  Class A extend B => LHS(A)
            String rhs = Class_extend.get(temp); //RHS(B)
            
            //iterate through the rhs and grab the inputs
            for(String str1 : vtable2.keySet()){
                LinkedHashMap<String, String> mapper = new LinkedHashMap<>();
                if(str1 == rhs){
                    for(String str2 : vtable2.get(str1).keySet()){
                        if(vt.get(temp).get(str2) == null){
                            String pass = vtable2.get(str1).get(str2); //types
                            mapper.put(str2, pass);
                        }
                    }
                    MethodAdder(str1, mapper);
                    mapper.clear();
                }
            }
        } 
    }
}
