import java.util.ArrayList;
import java.util.List;

public class ArmstrongNumbersBruteforce {
    private static long[][] pows;

    private static void genPows(int N) {
        if (N > 20) throw new IllegalArgumentException();
        pows = new long[10][N + 1];
        for (int i = 0; i < pows.length; i++) {
            long p = 1;
            for (int j = 0; j < pows[i].length; j++) {
                pows[i][j] = p;
                p *= i;
            }
        }
    }

    private static boolean check(long i) {
        long powSum = 0;
        long x = i;
        int N = ((int) Math.log10(x)) + 1;
        do {
            int mod = (int) x % 10;
            powSum += pows[mod][N];
            x = x / 10;
        } while (x != 0);
        return powSum == i;
    }

    public static List<Long> generate(int maxN) {
        if (maxN >= 20) throw new IllegalArgumentException();
        List<Long> results = new ArrayList<>();
        genPows(maxN);
        long limit = (long) Math.pow(10, maxN);
        for (long i = 1; i < limit; i++) {
            if (check(i)) results.add(i);
        }
        return results;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        List<Long> list = generate(9);
        long finish = System.currentTimeMillis();
        System.out.println("Time consumed: " + (finish - start) + " ms");
        System.out.println(list.size());
        System.out.println(list);
    }
}
