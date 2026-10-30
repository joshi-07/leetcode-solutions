
import java.util.*;

class Solution {

    public int shortestPath(int n, int[][] edges, String labels, int k) {

        int m = edges.length;

        int[] head = new int[n];
        int[] to = new int[m];
        int[] weight = new int[m];
        int[] next = new int[m];

        Arrays.fill(head, -1);

        for (int i = 0; i < m; i++) {
            int u = edges[i][0];

            to[i] = edges[i][1];
            weight[i] = edges[i][2];
            next[i] = head[u];
            head[u] = i;
        }

        int states = n * k;

        long[] dist = new long[states];
        Arrays.fill(dist, Long.MAX_VALUE);

        PriorityQueue<Long> pq = new PriorityQueue<>();

        long stateCount = states;

        dist[0] = 0;
        pq.offer(0L);

        while (!pq.isEmpty()) {

            long current = pq.poll();

            long d = current / stateCount;
            int state = (int) (current % stateCount);

            if (d != dist[state]) {
                continue;
            }

            int u = state / k;
            int count = state % k + 1;

            if (u == n - 1) {
                return (int) d;
            }

            for (int e = head[u]; e != -1; e = next[e]) {

                int v = to[e];
                int w = weight[e];

                int newCount;

                if (labels.charAt(u) == labels.charAt(v)) {
                    newCount = count + 1;
                } else {
                    newCount = 1;
                }

                if (newCount > k) {
                    continue;
                }

                int newState = v * k + newCount - 1;
                long newDist = d + w;

                if (newDist < dist[newState]) {

                    dist[newState] = newDist;

                    long encoded = newDist * stateCount + newState;
                    pq.offer(encoded);
                }
            }
        }

        return -1;
    }
}
