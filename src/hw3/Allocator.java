package hw3;

import cs132.vapor.ast.VVarRef;

import java.util.List;

public class Allocator {
    List<String> activeList;

    public AllocationMap computeAllocation(List<IntervalNode> intervals, VVarRef.Local[] params){
        List<String> active;
        sortStartIntervalList(intervals);


        for(IntervalNode i: intervals){
            expireOldIntervals(i);
        }


        AllocationMap temp = new AllocationMap();
        return temp;
    }

    public void expireOldIntervals(IntervalNode i){

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
