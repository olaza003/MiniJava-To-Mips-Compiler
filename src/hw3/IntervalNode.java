package hw3;

import cs132.vapor.ast.VInstr;

public class IntervalNode {
    public int start;
    public int end;
    public int location;
    public String variable;
    public boolean holder;
    public boolean calle = false;

    public IntervalNode(int startTime, String varName){
        start = startTime;
        variable = varName;
    }

    public IntervalNode(int startTime, int endTime, String varName){
        start = startTime;
        end = endTime;
        variable = varName;
    }

    public IntervalNode(int startTime, int endTime, String varName, VInstr v){
        start = startTime;
        end = endTime;
        variable = varName;
    }

    public void setCalle(){calle = true;}
}
