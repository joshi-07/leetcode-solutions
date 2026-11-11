class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        long totalCost = 0;
        
        long common = Math.min(need1, need2);
        long rem1 = need1 - common;
        long rem2 = need2 - common;
        
        // 1. Handle the overlapping requirement using the cheaper option between Type 3 or (Type 1 + Type 2)
        totalCost += common * Math.min((long) costBoth, (long) cost1 + cost2);
        
        // 2. Handle the remaining Type 1 requirement
        totalCost += rem1 * Math.min((long) costBoth, (long) cost1);
        
        // 3. Handle the remaining Type 2 requirement
        totalCost += rem2 * Math.min((long) costBoth, (long) cost2);
        
        return totalCost;
    }
}