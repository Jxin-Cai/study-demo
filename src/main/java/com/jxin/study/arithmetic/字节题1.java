package com.jxin.study.arithmetic;

/**
 * @author Jxin
 * @version 1.0
 * @since 2020/9/27 16:10
 */
public class 字节题1 {

    public int getIdx(int[] gas, int[] cost){
        int idx = 0;
        while (idx < gas.length){
            final boolean can = can(idx, gas, cost);
            if(can){
                return idx;
            }
            idx++;
        }
        return -1;
    }
    private boolean can(int idx, int[] gas, int[] cost){
        int offset = idx;
        int sum = 0;

        for (int i = 0; i < gas.length; i++) {
            final int realOffset = offset < gas.length? offset: offset - gas.length;
            sum += gas[realOffset] - cost[realOffset];
            if(sum < 0){
                return false;
            }
            offset++;
        }
        return true;
    }
}
