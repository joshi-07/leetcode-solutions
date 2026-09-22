class Solution {

    private int n, k, size;
    private int[] product;
    private int[] count;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;

        // Iterative segment tree size
        size = 1;
        while (size < n) {
            size <<= 1;
        }

        product = new int[size << 1];
        count = new int[(size << 1) * k];

        // Identity element for multiplication modulo k
        int identity = 1 % k;

        // Padding leaves are empty segments -> identity
        java.util.Arrays.fill(product, identity);

        // Build leaves
        for (int i = 0; i < n; i++) {
            int node = size + i;
            int val = nums[i] % k;

            product[node] = val;
            count[node * k + val] = 1;
        }

        // Build tree
        for (int node = size - 1; node > 0; node--) {
            pull(node);
        }

        int[] ans = new int[queries.length];

        // Maximum number of segment nodes needed in a query
        int[] nodes = new int[64];

        for (int qi = 0; qi < queries.length; qi++) {
            int index = queries[qi][0];
            int value = queries[qi][1];
            int start = queries[qi][2];
            int x = queries[qi][3];

            // ----------------------------------------------------
            // 1. Persistent update
            // ----------------------------------------------------
            int node = size + index;
            int val = value % k;

            product[node] = val;

            int base = node * k;
            for (int r = 0; r < k; r++) {
                count[base + r] = 0;
            }
            count[base + val] = 1;

            node >>= 1;
            while (node > 0) {
                pull(node);
                node >>= 1;
            }

            // ----------------------------------------------------
            // 2. Query range [start, n)
            // ----------------------------------------------------
            int left = size + start;
            int right = size + n;

            int leftCount = 0;
            int rightStart = 64;

            /*
             * Store selected segment-tree nodes in their correct
             * left-to-right order.
             */
            while (left < right) {
                if ((left & 1) != 0) {
                    nodes[leftCount++] = left++;
                }

                if ((right & 1) != 0) {
                    --right;
                    nodes[--rightStart] = right;
                }

                left >>= 1;
                right >>= 1;
            }

            // Accumulator for the whole queried range
            int[] accCount = new int[k];
            int accProduct = 1 % k;

            // First the left-side nodes
            for (int i = 0; i < leftCount; i++) {
                int curNode = nodes[i];
                int curBase = curNode * k;
                int curProduct = product[curNode];

                for (int r = 0; r < k; r++) {
                    accCount[(accProduct * r) % k] += count[curBase + r];
                }

                accProduct = (accProduct * curProduct) % k;
            }

            // Then the right-side nodes
            for (int i = rightStart; i < 64; i++) {
                int curNode = nodes[i];
                int curBase = curNode * k;
                int curProduct = product[curNode];

                for (int r = 0; r < k; r++) {
                    accCount[(accProduct * r) % k] += count[curBase + r];
                }

                accProduct = (accProduct * curProduct) % k;
            }

            ans[qi] = accCount[x];
        }

        return ans;
    }

    // Recalculate a segment tree node
    private void pull(int node) {
        int left = node << 1;
        int right = left | 1;

        int leftProduct = product[left];

        product[node] = (leftProduct * product[right]) % k;

        int base = node * k;
        int leftBase = left * k;
        int rightBase = right * k;

        // Prefixes entirely inside left child
        for (int r = 0; r < k; r++) {
            count[base + r] = count[leftBase + r];
        }

        // Prefixes that enter the right child
        for (int r = 0; r < k; r++) {
            int newResidue = (leftProduct * r) % k;
            count[base + newResidue] += count[rightBase + r];
        }
    }
}