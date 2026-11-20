class Solution {
    public int maxSelectedElements(int[] nums) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            if (num < min) min = num;
            if (num > max) max = num;
        }

        byte[] freq = new byte[max - min + 1];
        for (int num : nums) {
            int idx = num - min;
            if (freq[idx] < 2) {
                freq[idx]++;
            }
        }

        int dp0 = 0; // max length ending at x - 1
        int dp1 = 0; // max length ending at x
        int ans = 0;

        for (byte count : freq) {
            if (count == 0) {
                dp0 = dp1;
                dp1 = 0;
            } else if (count == 1) {
                dp1 = dp1 + 1;
                dp0 = dp0 + 1;
            } else {
                dp0 = dp0 + 1;
                dp1 = dp0 + 1;
            }
            if (dp1 > ans) ans = dp1;
            if (dp0 > ans) ans = dp0;
        }

        return ans;
    }
}