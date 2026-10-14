class Solution {
    public String maxSumOfSquares(int num, int sum) {
        if (sum > 9 * num) {
            return "";
        }

        StringBuilder ans = new StringBuilder(num);

        while (sum >= 9) {
            ans.append('9');
            sum -= 9;
        }

        if (sum > 0) {
            ans.append((char) ('0' + sum));
        }

        while (ans.length() < num) {
            ans.append('0');
        }

        return ans.toString();
    }
}