import java.util.*;

class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {

        // Store key -> value for O(1) average lookup
        Map<String, String> map = new HashMap<>();

        for (List<String> pair : knowledge) {
            map.put(pair.get(0), pair.get(1));
        }

        StringBuilder result = new StringBuilder();

        int i = 0;

        while (i < s.length()) {

            // Normal character
            if (s.charAt(i) != '(') {
                result.append(s.charAt(i));
                i++;
                continue;
            }

            // Find the closing ')'
            int j = i + 1;

            while (s.charAt(j) != ')') {
                j++;
            }

            // Extract key between '(' and ')'
            String key = s.substring(i + 1, j);

            // Replace with value or '?'
            result.append(map.getOrDefault(key, "?"));

            // Move past ')'
            i = j + 1;
        }

        return result.toString();
    }
}