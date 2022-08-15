package hw3;

import cs132.vapor.ast.VVarRef;

import java.util.*;

public class Allocator {
    List<IntervalNode> active;
    List<Register> registerPool;
    public AllocationMap computeAllocation(List<IntervalNode> intervals, VVarRef.Local[] params){
        active = new ArrayList<>();
        registerPool = Register.createRegisterPool();
        sortStartIntervalList(intervals);
        for(IntervalNode i: intervals){
            expireOldIntervals(i);
            if(active.size() == registerPool.size())
                spillAtInterval(i);
            else {
                //remove from pool of registers
                active.add(i);
            }
        }


        AllocationMap temp = new AllocationMap();
        return temp;
    }

    public void expireOldIntervals(IntervalNode i){
        for(IntervalNode j: active){
            if(j.end >= i.start)
                return;
            active.remove(j);
            registerPool.add(i.register);
            //add register[j] to pool of free registers
        }
    }

    public void spillAtInterval(IntervalNode i){
        IntervalNode spill = active.get(active.size() - 1);
        if(spill.end > i.end){
            i.register = spill.register;
            //spill.location = new stack location;
            active.remove(spill);
            insertByEndPoint(i);
        }
    }

    public void insertByEndPoint(IntervalNode iNode){
        for(int i = 0; i < active.size(); ++i){
            if(active.get(i).end > iNode.end) {
                active.add(i - 1, iNode);
                return;
            }
        }
    }

    public void sortStartIntervalList(List<IntervalNode> intervals){
        for(int i = 0; i < intervals.size(); i++){
            IntervalNode min = intervals.get(i);
            int minId = i;
            for(int j = i + 1; j < intervals.size(); j++){
                if(intervals.get(j).start < min.start){
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
