package algo;

/**
 * @author MiskuZero
 */
public class SquareMidRandom {

    private static long seed;

    static {
        seed = next();
    }

    private static int next() {
        return (int) ((System.nanoTime() & 0x3fffc) + (System.nanoTime() & 0xfffc));
    }

    public static int next(int bits) {
        long s = seed;
        if ((s & (s - 1)) == 0)
            s = next();
        s = ((s * s) << 16) >>> 32;
        seed = s;
        return (int) (s >>> (32 - bits));
    }

    public static int nextInt(int bound) {
        int r = next(31);
        int m = bound - 1;
        if ((bound & m) == 0)
            r = (int) ((bound * (long) r) >> 31);
        else
            for (int u = r; u - (r = u % bound) + m < 0; u = next(31)) ;
        return r;
    }

}
