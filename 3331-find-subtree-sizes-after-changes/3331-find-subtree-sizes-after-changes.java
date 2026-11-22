class Solution {
    private int[] head, next, res, last, parent;
    private char[] chars;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        this.parent = parent;
        this.chars = s.toCharArray();
        this.head = new int[n];
        this.next = new int[n];
        this.res = new int[n];
        this.last = new int[26];

        Arrays.fill(head, -1);
        Arrays.fill(last, -1);

        for (int i = 1; i < n; i++) {
            int p = parent[i];
            next[i] = head[p];
            head[p] = i;
        }

        dfs(0);
        return res;
    }

    private void dfs(int u) {
        int c = chars[u] - 'a';
        int prev = last[c];
        last[c] = u;
        res[u] = 1;

        for (int v = head[u]; v != -1; v = next[v]) {
            dfs(v);
        }

        last[c] = prev;
        if (u != 0) {
            int newParent = (prev != -1) ? prev : parent[u];
            res[newParent] += res[u];
        }
    }
}