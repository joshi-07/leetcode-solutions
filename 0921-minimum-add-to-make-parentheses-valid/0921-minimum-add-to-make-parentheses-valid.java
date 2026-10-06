class Solution {
    public int minAddToMakeValid(String s) {
        int balance = 0;
        int additions = 0;

        for (char c : s.toCharArray()) {

            if (c == '(') {
                balance++;
            } else {
                if (balance > 0) {
                    balance--;
                } else {
                    // Need to insert '(' before this ')'
                    additions++;
                }
            }
        }

        // Any remaining '(' needs a ')'
        additions += balance;

        return additions;
    }
}