import java.util.*;

public class ArmstrongNumbersHash {
    private static Map<Long, List<Long>> reach;
    private static List<Long> results;
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

    private static void searchSecondHalf(int depth, int target, long number, long pow) {
        if (depth == target / 2) {
            long p = pow - number;
            if (!reach.containsKey(p)) reach.put(p, new ArrayList<>());
            reach.get(p).add(number);
            return;
        }

        number *= 10;
        for (int i = 0; i < 10; i++) {
            searchSecondHalf(depth + 1, target, number + i, pow + pows[i][target]);
        }
    }

    private static void searchFirstHalf(int depth, int target, long number, long pow) {
        if (depth == (target + 1) / 2) {
            number = number * (long) Math.pow(10, target / 2);
            long p = number - pow;

            if (reach.containsKey(p)) {
                for (Long l : reach.get(p)) {
                    results.add(number + l);
                }
            }
            return;
        }

        number *= 10;
        for (int i = 0; i < 10; i++) {
            if (i == 0 && depth == 0) continue;
            searchFirstHalf(depth + 1, target, number + i, pow + pows[i][target]);
        }
    }

    public static List<Long> generate(int maxN) {
        genPows(maxN);
        results = new ArrayList<>();
        reach = new HashMap<>();

        for (int N = 1; N <= maxN; N++) {
            reach.clear();
            searchSecondHalf(0, N, 0L, 0L);
            searchFirstHalf(0, N, 0L, 0L);
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
