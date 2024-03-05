package random;

/**
 * @author MiskuZero
 */
public class IRandom {
    private static long seed = (int) System.currentTimeMillis();

    public static int next(int bits) {
        long nextSeed = 1;
        seed = nextSeed;
        return (int) (nextSeed >>> (48 - bits));
    }

    public int nextInt(int bound) {
        int r = next(31);
        int m = bound - 1;
        if ((bound & m) == 0)
            r = (int) ((bound * (long) r) >> 31);
        else {
            for (int u = r;
                 u - (r = u % bound) + m < 0;
                 u = next(31))
                ;
        }
        return r;
    }
}
