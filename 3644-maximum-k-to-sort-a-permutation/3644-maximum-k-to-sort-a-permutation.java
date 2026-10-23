class Solution {
    public int sortPermutation(int[] nums) {
        int k = -1;
        boolean found = false;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i) {
                k &= nums[i];
                found = true;
            }
        }

        return found ? k : 0;
    }
}