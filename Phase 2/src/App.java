import java.util.*;
public class App {
    
    public static void main(String[] args) throws Exception {
        LinkedHashMap<String, LinkedHashMap<String, Integer>> map = new LinkedHashMap<>();
        System.out.println("Hello, World!");
        LinkedHashMap<String, Integer> s = new LinkedHashMap<>();
        s.clear();
        s.put("a", 1);
        s.put("b", 2);
        s.put("c", 3);
        map.put("A", s);
        s.clear();

        int map_size = map.get("A").size();
        System.out.println("size of the map in A: " + map_size);

        s.put("d", 1);
        s.put("e", 6);
        s.put("f", 0);

        /* LinkedHashMap<String, Integer> inner = map.get("B");
        if(inner == null){
            inner = new LinkedHashMap<>();
            map.put("B", inner);
        }
        inner.putAll(s); */
        map.put("B", s);

        int map_size2 = map.get("B").size();
        System.out.println("size of the map in A: " + map_size2);

        for (String temp : map.keySet()) {
            for (String c : map.get(temp).keySet()) {
                System.out.println("key--" + c + "--value--" + map.get(temp).get(c));
            }
            System.out.println("-------------");
        }
    }
}
