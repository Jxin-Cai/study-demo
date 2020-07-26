package com.jxin.study.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 期中考试Test
 * @author Jxin
 * @version 1.0
 * @since jdk 1.8
 */
public class MidsemesterTest {
    @Test
    public void test(){
        fourSum(new int[]{5,5,3,5,1,-5,1,-2}, 4);
    }

    // 4数之和
    public List<List<Integer>> fourSum(int[] nums, int target) {
        if(nums == null || nums.length < 4){
            return Collections.emptyList();
        }
        Arrays.sort(nums);
        final List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if(i > 0 && nums[i] == nums[i - 1]){
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                final int val = target - (nums[i] + nums[j]);
                if(j > i + 1 && nums[j] == nums[j - 1]){
                    continue;
                }
                for (int y = nums.length - 1; y > j + 1; y--) {
                    if(y < nums.length - 1 && nums[y] == nums[y + 1]){
                        continue;
                    }
                    for (int x = y - 1; x > j ; x--) {
                        if(x < y - 1 && nums[x] == nums[x + 1]){
                            continue;
                        }
                        if(nums[y] + nums[x] > val){
                            continue;
                        }
                        if(nums[y] + nums[x] < val){
                            break;
                        }
                        final List<Integer> meetList = new ArrayList<>(4);
                        meetList.add(nums[i]);
                        meetList.add(nums[j]);
                        meetList.add(nums[x]);
                        meetList.add(nums[y]);
                        result.add(meetList);
                    }
                }
            }
        }
        return result;
    }
    public int jump1(int[] nums) {
        int count = 0;
        int start = 0;
        int end = 0;
        int far;
        while(end < nums.length - 1){
            far = 0;
            for(int i = start ; i <= end; i++){
                if(i + nums[i] > far){
                    far = i + nums[i];
                }
            }
            start = end + 1;
            end = far;
            count++;
        }
        return count;
    }

    // 跳跃
    public int jump(int[] nums) {
        if(nums == null || nums.length < 2){
            return 0;
        }

        int maxLenth = nums.length - 1;
        int result = 0;
        while (maxLenth != 0){
            result++;
            maxLenth = farthestOffset(nums, maxLenth);
        }
       return result;
    }

    private int farthestOffset(int[] nums, int maxLenth) {
        int minOffset = maxLenth;
        for (int i = maxLenth - 1; i >= 0; i--) {
            if(i + nums[i] < maxLenth){
               continue;
            }

            minOffset = i;
        }
        return minOffset;
    }
}
