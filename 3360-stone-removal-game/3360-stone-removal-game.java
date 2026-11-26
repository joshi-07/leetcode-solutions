class Solution {
    public boolean canAliceWin(int n) {
        int remove = 10;
        boolean aliceTurn = true;

        while (n >= remove) {
            n -= remove;
            remove--;
            aliceTurn = !aliceTurn;
        }

        // If aliceTurn is true now, Alice cannot move and Bob wins; otherwise Alice wins.
        return !aliceTurn;
    }
}