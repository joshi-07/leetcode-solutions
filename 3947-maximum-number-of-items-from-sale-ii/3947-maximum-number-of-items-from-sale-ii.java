import java.util.Arrays;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] freq = new int[n + 1];
        long minPrice = Long.MAX_VALUE;

        for (int[] item : items) {
            freq[item[0]]++;
            if (item[1] < minPrice) {
                minPrice = item[1];
            }
        }

        int[] multCount = new int[n + 1];
        for (int f = 1; f <= n; f++) {
            if (freq[f] > 0) {
                int count = 0;
                for (int m = f; m <= n; m += f) {
                    count += freq[m];
                }
                multCount[f] = count;
            }
        }

        Arrays.sort(items, (a, b) -> Integer.compare(a[1], b[1]));

        long rem = budget;
        long total = 0;
        long limit = 2L * minPrice;

        for (int[] item : items) {
            long price = item[1];
            if (price > limit || rem < price) {
                break;
            }
            long gain = multCount[item[0]] - 1;
            if (gain > 0) {
                long take = Math.min(gain, rem / price);
                total += 2 * take;
                rem -= take * price;
            }
        }

        total += rem / minPrice;
        return (int) total;
    }
}