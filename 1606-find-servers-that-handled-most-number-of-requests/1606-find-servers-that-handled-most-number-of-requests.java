
import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {

        // Stores available server IDs in sorted order
        TreeSet<Integer> available = new TreeSet<>();

        // Stores {completionTime, serverId}
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> Long.compare(a[0], b[0])
        );

        int[] count = new int[k];

        // Initially, all servers are available
        for (int i = 0; i < k; i++) {
            available.add(i);
        }

        for (int i = 0; i < arrival.length; i++) {

            // Release servers whose requests have completed
            while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) {
                long[] completed = busy.poll();
                available.add((int) completed[1]);
            }

            // If all servers are busy, drop the request
            if (available.isEmpty()) {
                continue;
            }

            // Preferred server for this request
            int preferred = i % k;

            // Find the next available server
            Integer server = available.ceiling(preferred);

            // Wrap around if necessary
            if (server == null) {
                server = available.first();
            }

            // Assign the request
            available.remove(server);
            count[server]++;

            // Calculate completion time
            long completionTime = (long) arrival[i] + load[i];

            busy.offer(new long[]{completionTime, server});
        }

        // Find the maximum number of handled requests
        int max = 0;

        for (int requests : count) {
            max = Math.max(max, requests);
        }

        // Collect all busiest servers
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (count[i] == max) {
                result.add(i);
            }
        }

        return result;
    }
}
