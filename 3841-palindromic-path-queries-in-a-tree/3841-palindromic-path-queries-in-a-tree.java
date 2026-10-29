import java.util.*;

class Solution {
    private int[] bit;
    private int n;

    private void upd(int i, int v) {
        for (i++; i <= n + 1; i += i & -i) bit[i] ^= v;
    }

    private int qry(int i) {
        int r = 0;
        for (i++; i > 0; i -= i & -i) r ^= bit[i];
        return r;
    }

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        this.n = n;
        bit = new int[n + 3];

        // adjacency (arrays)
        int[] head = new int[n], nxt = new int[2 * (n - 1)], to = new int[2 * (n - 1)];
        Arrays.fill(head, -1);
        int ec = 0;
        for (int[] e : edges) {
            to[ec] = e[1]; nxt[ec] = head[e[0]]; head[e[0]] = ec++;
            to[ec] = e[0]; nxt[ec] = head[e[1]]; head[e[1]] = ec++;
        }

        int LOG = 1;
        while ((1 << LOG) < n) LOG++;
        int[][] up = new int[LOG + 1][n];
        int[] depth = new int[n], tin = new int[n], tout = new int[n], P = new int[n];
        char[] c = s.toCharArray();

        // iterative DFS from root 0
        int[] stack = new int[n], it = new int[n];
        int sp = 0, timer = 0;
        stack[sp++] = 0;
        up[0][0] = 0;
        P[0] = 1 << (c[0] - 'a');
        tin[0] = timer++;
        System.arraycopy(head, 0, it, 0, n);
        while (sp > 0) {
            int u = stack[sp - 1];
            if (it[u] != -1) {
                int e = it[u];
                it[u] = nxt[e];
                int v = to[e];
                if (v == up[0][u] && u != 0 || (u == 0 && false)) continue;
                if (v == up[0][u]) continue;
                up[0][v] = u;
                depth[v] = depth[u] + 1;
                P[v] = P[u] ^ (1 << (c[v] - 'a'));
                tin[v] = timer++;
                stack[sp++] = v;
            } else {
                tout[u] = timer - 1;
                sp--;
            }
        }
        for (int k = 1; k <= LOG; k++)
            for (int v = 0; v < n; v++)
                up[k][v] = up[k - 1][up[k - 1][v]];

        List<Boolean> ans = new ArrayList<>();
        for (String q : queries) {
            String[] p = q.split(" ");
            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char nc = p[2].charAt(0);
                int d = (1 << (c[u] - 'a')) ^ (1 << (nc - 'a'));
                if (d != 0) {
                    upd(tin[u], d);
                    upd(tout[u] + 1, d);
                    c[u] = nc;
                }
            } else {
                int u = Integer.parseInt(p[1]), v = Integer.parseInt(p[2]);
                // LCA
                int a = u, b = v;
                if (depth[a] < depth[b]) { int t = a; a = b; b = t; }
                int diff = depth[a] - depth[b];
                for (int k = 0; k <= LOG; k++)
                    if ((diff >> k & 1) != 0) a = up[k][a];
                if (a != b) {
                    for (int k = LOG; k >= 0; k--)
                        if (up[k][a] != up[k][b]) { a = up[k][a]; b = up[k][b]; }
                    a = up[0][a];
                }
                int lca = a;
                int mu = P[u] ^ qry(tin[u]);
                int mv = P[v] ^ qry(tin[v]);
                int mask = mu ^ mv ^ (1 << (c[lca] - 'a'));
                ans.add((mask & (mask - 1)) == 0);
            }
        }
        return ans;
    }
}