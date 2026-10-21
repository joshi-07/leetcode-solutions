class Solution {
    public int minOperations(String s) {
        int n = s.length();
        
        boolean isSorted = true;
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) > s.charAt(i + 1)) {
                isSorted = false;
                break;
            }
        }
        
        if (isSorted) {
            return 0;
        }
        
        if (n == 2) {
            return -1;
        }
        
        char minRest = s.charAt(1);
        for (int i = 2; i < n; i++) {
            if (s.charAt(i) < minRest) {
                minRest = s.charAt(i);
            }
        }
        
        char maxRest = s.charAt(0);
        for (int i = 1; i < n - 1; i++) {
            if (s.charAt(i) > maxRest) {
                maxRest = s.charAt(i);
            }
        }
        
        if (s.charAt(0) <= minRest || s.charAt(n - 1) >= maxRest) {
            return 1;
        }
        
        char maxChar = s.charAt(0);
        char minChar = s.charAt(0);
        int maxCount = 0;
        int minCount = 0;
        
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c > maxChar) {
                maxChar = c;
                maxCount = 1;
            } else if (c == maxChar) {
                maxCount++;
            }
            
            if (c < minChar) {
                minChar = c;
                minCount = 1;
            } else if (c == minChar) {
                minCount++;
            }
        }
        
        if (s.charAt(0) == maxChar && s.charAt(n - 1) == minChar && maxCount == 1 && minCount == 1) {
            return 3;
        }
        
        return 2;
    }
}