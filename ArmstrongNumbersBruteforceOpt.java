import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArmstrongNumbersBruteforceOpt {
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

    private static List<Long> results;
    private static int N;

    private static void search(int depth, long num, long pow) {
        if (depth == N) {
            if (pow == num) results.add(num);
            return;
        }

        num *= 10;
        for (int i = 0; i < 10; i++) {
            if (depth == 0 && i == 0) continue;
            search(depth + 1, num + i, pow + pows[i][N]);
        }
    }

    public static List<Long> generate(int maxN) {
        if (maxN >= 20) throw new IllegalArgumentException();
        results = new ArrayList<>();
        genPows(maxN);
        for (N = 1; N <= maxN; N++) {
            search(0, 0, 0);
        }
        Collections.sort(results);
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
