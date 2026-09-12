import java.util.*;

class Solution {
    
    static class Interval {
        int l, r, w, index;

        Interval(int l, int r, int w, int index) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.index = index;
        }
    }

    static class State {
        long weight;
        int[] indices;

        State(long weight, int[] indices) {
            this.weight = weight;
            this.indices = indices;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        Interval[] arr = new Interval[n];

        for (int i = 0; i < n; i++) {
            List<Integer> x = intervals.get(i);

            arr[i] = new Interval(
                x.get(0),
                x.get(1),
                x.get(2),
                i
            );
        }

        // Sort by ending position
        Arrays.sort(arr, (a, b) -> {
            if (a.r != b.r)
                return Integer.compare(a.r, b.r);
            return Integer.compare(a.l, b.l);
        });

        // prev[i] = number of intervals before i
        // whose ending point is strictly less than arr[i].l
        int[] prev = new int[n];

        for (int i = 0; i < n; i++) {
            int left = 0;
            int right = i - 1;
            int ans = -1;

            while (left <= right) {
                int mid = left + (right - left) / 2;

                if (arr[mid].r < arr[i].l) {
                    ans = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            prev[i] = ans + 1;
        }

        // dp[i][k] = best answer using first i intervals
        // with at most k intervals selected
        State[][] dp = new State[n + 1][5];

        for (int i = 0; i <= n; i++) {
            for (int k = 0; k <= 4; k++) {
                dp[i][k] = new State(0, new int[0]);
            }
        }

        for (int i = 1; i <= n; i++) {

            Interval cur = arr[i - 1];

            for (int k = 1; k <= 4; k++) {

                // Don't take current interval
                State skip = dp[i - 1][k];

                // Take current interval
                State before = dp[prev[i - 1]][k - 1];

                int[] newIndices =
                    Arrays.copyOf(before.indices, before.indices.length + 1);

                newIndices[newIndices.length - 1] = cur.index;

                // Keep indices sorted for lexicographical comparison
                Arrays.sort(newIndices);

                State take = new State(
                    before.weight + cur.w,
                    newIndices
                );

                dp[i][k] = better(skip, take);
            }
        }

        return dp[n][4].indices;
    }

    // Returns the better state
    static State better(State a, State b) {

        if (a.weight != b.weight) {
            return a.weight > b.weight ? a : b;
        }

        // Same weight -> lexicographically smaller indices
        int len = Math.min(a.indices.length, b.indices.length);

        for (int i = 0; i < len; i++) {
            if (a.indices[i] != b.indices[i]) {
                return a.indices[i] < b.indices[i] ? a : b;
            }
        }

        return a.indices.length <= b.indices.length ? a : b;
    }
}