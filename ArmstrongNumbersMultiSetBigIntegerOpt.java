import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ArmstrongNumbersMultiSetBigIntegerOpt {
    private static final int BASE = 10; // decimal base - cannot be changed, since I use BigInteger.TEN
    private static final int MAX_N = 50;
    private static BigInteger[][] pows = new BigInteger[BASE][MAX_N];

    private static void genPows() {
        for (int i = 0; i < pows.length; i++) {
            BigInteger p = BigInteger.ONE;
            for (int j = 0; j < pows[i].length; j++) {
                pows[i][j] = p;
                p = p.multiply(BigInteger.valueOf(i));
            }
        }
    }

    private static int N; // Current length
    private static int[] digits = new int[BASE];
    private static int[] pd = new int[BASE];

    private static List<BigInteger> results = new ArrayList<>();
    private static BigInteger maxPow;
    private static BigInteger minPow;

    private static void preparePow(int N) {
        minPow = BigInteger.ONE;
        for (int i = 0; i < N-1; i++) {
            minPow = minPow.multiply(BigInteger.TEN);
        }
        maxPow = minPow.multiply(BigInteger.TEN);
    }

    private static boolean checkWithChars(BigInteger pow) {
        int compareMax = pow.compareTo(maxPow);
        if (compareMax >= 0 ) return false;
        int compareMin = pow.compareTo(minPow);
        if (compareMin < 0) return false;

        for (int i = 0; i < BASE; i++) {
            pd[i]= 0;
        }

        String s = pow.toString();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int x = c-'0';
            pd[x]++;
            if (pd[x] > digits[x]) return false;
        }

        for (int i = 0; i < BASE; i++) {
            if (pd[i] != digits[i]) return false;
        }

        return true;
    }

    private static boolean check(BigInteger pow) {
        int compareMax = pow.compareTo(maxPow);
        if (compareMax >= 0 ) return false;
        int compareMin = pow.compareTo(minPow);
        if (compareMin < 0) return false;

        for (int i = 0; i < BASE; i++) {
            pd[i]= 0;
        }

        while (pow.compareTo(BigInteger.ZERO) != 0) {
            BigInteger[] resultAndRemainder = pow.divideAndRemainder(BigInteger.TEN);
            // int i = pow.mod(BigInteger.TEN).intValue();
            int i = resultAndRemainder[1].intValue();
            pd[i]++;
            if (pd[i] > digits[i]) return false;
            // pow = pow.divide(BigInteger.TEN);
            pow = resultAndRemainder[0];
        }

        for (int i = 0; i < BASE; i++) {
            if (pd[i] != digits[i]) return false;
        }

        return true;
    }

    private static void search(int digit, int unused, BigInteger pow) {
        int compareMax = pow.compareTo(maxPow);
        if (compareMax >= 0 ) return;

        if (digit == -1) {
            if (checkWithChars(pow)) results.add(pow);
//            if (check(pow)) results.add(pow);
            return;
        }

        if (digit == 0) {
            digits[digit] = unused;
            search(digit - 1, 0,  pow.add(pows[digit][N].multiply(BigInteger.valueOf(unused))));
        } else {
            // Check if we can generate more than minimum
            if ((pow.add(pows[digit][N].multiply(BigInteger.valueOf(unused)))).compareTo(minPow) < 0) return;

            BigInteger bi = pow;
            for (int i = 0; i <= unused; i++) {
                digits[digit] = i;
                search(digit - 1, unused - i, bi);
                if (i!=unused) bi = bi.add(pows[digit][N]);
            }
        }
    }

    public static void main(String[] args) {
        Date start = new Date();
        genPows();
        for (N = 1; N < 40; N++) {
//            results.clear();
            long time = (new Date().getTime()) - start.getTime();
            System.out.println("N = " + N);
            System.out.println("time = " + time);
            preparePow(N);
            search(BASE - 1, N, BigInteger.ZERO);
//            System.out.println("Found = " +  results.size());
//            System.out.println(results);
        }

        Collections.sort(results);
        System.out.println(results.size());
        System.out.println(results);
        Date finish = new Date();
        System.out.println("Overall time used: " + (finish.getTime() - start.getTime()));
    }

}
