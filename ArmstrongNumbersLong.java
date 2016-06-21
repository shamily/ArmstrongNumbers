import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArmstrongNumbersLong {

    static int N; // Current length
    static long[][] pows = new long[10][20];

    public static void genPows() {
        for (int i = 0; i < pows.length; i++) {
            long p = 1;
            for (int j = 0; j < pows[i].length; j++) {
                pows[i][j] = p;
                p *= i;
            }
        }
    }

    static final int BASE = 10; // decimal base
    static int[] digits = new int[BASE];
    static int[] pd = new int[BASE];

    static List<Long> results = new ArrayList<>();
    static long maxPow;
    static long minPow;

    public static void preparePow(int N) {
        minPow = (long) Math.pow(10, N - 1);
        maxPow = (long) Math.pow(10, N);
    }

    public static boolean check(long pow) {
        if (pow >= maxPow) return false;
        if (pow < minPow) return false;

        for (int i = 0; i < BASE; i++) {
            pd[i] = 0;
        }

        while (pow > 0) {
            int i = (int) (pow % 10);
            pd[i]++;
            if (pd[i] > digits[i]) return false;
            pow = pow / 10;
        }

        for (int i = 0; i < BASE; i++) {
            if (pd[i] != digits[i]) return false;
        }

        return true;
    }

    public static void search(int digit, int unused, long pow) {
        if (pow >= maxPow) return;

        if (digit == -1) {
            if (check(pow)) results.add(pow);
            return;
        }

        if (digit == 0) {
            digits[digit] = unused;
            search(digit - 1, 0, pow + unused * pows[digit][N]);
        } else {
            // Check if we can generate more than minimum
            if (pow + unused * pows[digit][N] < minPow) return;

            long p = pow;
            for (int i = 0; i <= unused; i++) {
                digits[digit] = i;
                search(digit - 1, unused - i, p);
                if (i != unused) {
                    p += pows[digit][N];
                    // Check maximum and break the loop - doesn't help
                    // if (p >= maxPow) break;
                }
            }
        }
    }

        public static void main(String[] args) {
            Long tStart = System.currentTimeMillis();

            genPows();
            for (N = 0; N < 10; N++) {
                preparePow(N);
                search(BASE - 1, N, 0);
            }

            Collections.sort(results);

            Long tFinish = System.currentTimeMillis();

            System.out.println("Time  = " + (tFinish - tStart));
            System.out.println(String.format("Used JVM Memory: %.3f Mb", (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576));

            System.out.println(results.size());
            System.out.println(results);
        }
}
