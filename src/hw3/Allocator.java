package hw3;

import cs132.vapor.ast.VVarRef;

import java.util.*;

public class Allocator {
    public List<IntervalNode> active;

    public RegisterPool regPool;
    public String[] registers = {"t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8", "s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7"};

    public List<IntervalNode> intervalStack;

    public HashMap<String, Register> regHashMap = new HashMap<>();

    public AllocationMap computeAllocation(List<IntervalNode> intervals, VVarRef.Local[] params){

        active = new ArrayList<>();
        regPool.createRegisterPool(registers);
        sortIntervalList(intervals, true);
        for(IntervalNode i: intervals){
            expireOldIntervals(i);
            if(active.size() == regPool.allRegisters.size())
                spillAtInterval(i);
            else {
                regHashMap.put(i.variable, regPool.getRegister());
                active.add(i);
                sortIntervalList(active, false);
            }
        }
        AllocationMap temp = new AllocationMap(regHashMap);
        return temp;
    }

    public void expireOldIntervals(IntervalNode i){
        for(IntervalNode j: active){
            if(j.end >= i.start)
                return;
            active.remove(j);
            //regPool.add(j.register);
        }
    }

    public void spillAtInterval(IntervalNode i){
        IntervalNode spill = active.get(active.size() - 1);
        if(spill.end > i.end){
            i.register = spill.register;
            intervalStack.add(spill);
            active.remove(spill);
            active.add(i);
            sortIntervalList(active, false);
        }
        else
            intervalStack.add(i);
    }

    public void sortIntervalList(List<IntervalNode> intervals, boolean isStart){
        for(int i = 0; i < intervals.size(); i++){
            IntervalNode min = intervals.get(i);
            int minId = i;
            for(int j = i + 1; j < intervals.size(); j++){
                int jVal = (isStart) ? intervals.get(j).start : intervals.get(j).end;
                int minVal = (isStart) ? min.start: min.end;

                if(jVal < minVal){
                    min = intervals.get(j);
                    minId = j;
                }
            }
            IntervalNode temp = intervals.get(i);
            intervals.set(i, min);
            intervals.set(minId, temp);
        }
    }
}
