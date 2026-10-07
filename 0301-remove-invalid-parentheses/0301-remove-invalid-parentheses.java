import java.util.*;

class Solution {

    public List<String> removeInvalidParentheses(String s) {

        // Find minimum number of '(' and ')' to remove
        int leftRemove = 0;
        int rightRemove = 0;

        for (char c : s.toCharArray()) {

            if (c == '(') {
                leftRemove++;
            } 
            else if (c == ')') {

                if (leftRemove > 0) {
                    leftRemove--;
                } 
                else {
                    rightRemove++;
                }
            }
        }

        Set<String> result = new HashSet<>();

        backtrack(
            s,
            0,
            0,
            leftRemove,
            rightRemove,
            new StringBuilder(),
            result
        );

        return new ArrayList<>(result);
    }

    private void backtrack(
        String s,
        int index,
        int balance,
        int leftRemove,
        int rightRemove,
        StringBuilder current,
        Set<String> result
    ) {

        // Invalid parenthesis order
        if (balance < 0) {
            return;
        }

        // We reached the end
        if (index == s.length()) {

            if (balance == 0 &&
                leftRemove == 0 &&
                rightRemove == 0) {

                result.add(current.toString());
            }

            return;
        }

        char c = s.charAt(index);

        // --------------------------------
        // Current character is '('
        // --------------------------------
        if (c == '(') {

            // Remove '('
            if (leftRemove > 0) {

                backtrack(
                    s,
                    index + 1,
                    balance,
                    leftRemove - 1,
                    rightRemove,
                    current,
                    result
                );
            }

            // Keep '('
            current.append(c);

            backtrack(
                s,
                index + 1,
                balance + 1,
                leftRemove,
                rightRemove,
                current,
                result
            );

            current.deleteCharAt(current.length() - 1);
        }

        // --------------------------------
        // Current character is ')'
        // --------------------------------
        else if (c == ')') {

            // Remove ')'
            if (rightRemove > 0) {

                backtrack(
                    s,
                    index + 1,
                    balance,
                    leftRemove,
                    rightRemove - 1,
                    current,
                    result
                );
            }

            // Keep ')' only if there is '(' before it
            if (balance > 0) {

                current.append(c);

                backtrack(
                    s,
                    index + 1,
                    balance - 1,
                    leftRemove,
                    rightRemove,
                    current,
                    result
                );

                current.deleteCharAt(current.length() - 1);
            }
        }

        // --------------------------------
        // Current character is a letter
        // --------------------------------
        else {

            current.append(c);

            backtrack(
                s,
                index + 1,
                balance,
                leftRemove,
                rightRemove,
                current,
                result
            );

            current.deleteCharAt(current.length() - 1);
        }
    }
}