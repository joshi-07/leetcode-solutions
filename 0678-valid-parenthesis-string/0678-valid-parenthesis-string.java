class Solution {
    public boolean checkValidString(String s) {
        int minOpen = 0; // Minimum possible number of unmatched '('
        int maxOpen = 0; // Maximum possible number of unmatched '('

        for (char c : s.toCharArray()) {
            if (c == '(') {
                minOpen++;
                maxOpen++;
            } 
            else if (c == ')') {
                minOpen--;
                maxOpen--;
            } 
            else { // '*'
                // '*' can be '(' or ')' or empty
                minOpen--; // Treat '*' as ')'
                maxOpen++; // Treat '*' as '('
            }

            // Even with all '*' used optimally, we have too many ')'
            if (maxOpen < 0) {
                return false;
            }

            // Minimum cannot go below 0
            minOpen = Math.max(minOpen, 0);
        }

        // If we can end with zero unmatched '('
        return minOpen == 0;
    }
}