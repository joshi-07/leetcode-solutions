
class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];
        int maxDiff = 0;
        long totalDiff = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            totalDiff += diff[i];
            maxDiff = Math.max(maxDiff, diff[i]);
        }

        long k = (long) k1 + k2;

        // Enough operations to make every difference zero
        if (k >= totalDiff) {
            return 0L;
        }

        // Find the smallest possible maximum difference
        int left = 0, right = maxDiff;

        while (left < right) {
            int mid = left + (right - left) / 2;
            long required = 0;

            for (int d : diff) {
                if (d > mid) {
                    required += d - mid;
                }
            }

            if (required <= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        int threshold = left;
        long used = 0;
        long answer = 0;

        for (int d : diff) {
            int reduced = Math.min(d, threshold);
            answer += (long) reduced * reduced;

            if (d > threshold) {
                used += d - threshold;
            }
        }

        // Distribute remaining operations by reducing threshold-level
        // differences by one more
        long remaining = k - used;
        answer -= remaining * (2L * threshold - 1);

        return answer;
    }
}
