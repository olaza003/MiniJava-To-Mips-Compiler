package hw3;

public class IntervalNode {
    public int start;
    public int end;
    public int location;
    public String variable;

    public Register register;

    public IntervalNode(int startTime, String varName){
        start = startTime;
        variable = varName;
    }

    public IntervalNode(int startTime, int endTime, String varName){
        start = startTime;
        end = endTime;
        variable = varName;
    }
}
