class Solution {
    public int numOfSubarrays(int[] arr) {
        int mod = 1_000_000_007;
        int oddCount = 0;
        int evenCount = 1;
        int prefixSum = 0;
        int result = 0;

        for (int num : arr) {
            prefixSum += num;
            if (prefixSum % 2 != 0) {
                result = (result + evenCount) % mod;
                oddCount++;
            } else {
                result = (result + oddCount) % mod;
                evenCount++;
            }
        }

        return result;
    }
}