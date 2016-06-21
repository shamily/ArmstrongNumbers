# Armstrong Numbers - Efficient Generation Algorithms

## What is that all about?

Armstrong number (aka Narcissistic number) of length N digits is a number which is equal to the sum of its digits each in power of N. For example: 153 = 1^3 + 5^3 + 3^3 = 3 + 125 + 27 = 153

More info at [wiki](https://en.wikipedia.org/wiki/Narcissistic_number)

## Why do we need to optimize?

Let's say that we need to generate Armstrong Numbers from 1 to K (of length N digits). There is an obvious bruteforce algorithms that:
1. For each integer *i* from 1 to *K*
2. Divides *i* by digits
3. Calculate power of each digit
4. Sum up those powers
5. If this sum is equal to *i* - add it to the result list

Such approach works reasonable time only for int numbers (N<10). For long''s (N<20) it works more than a night, which is not OK.

## Better approach

We may note that for each multi-set of digits, like [1, 1, 2, 4, 5, 7, 7] there is only one sum of powers, which in its turn may either be or be not represented by the digits from set. In the example 1^7 + 1^7 + 2^7 + 4^7 + 5^7 + 7^7 + 7^7 = 1741725, which can be represented by the digits and thus is an Armstrong number.

We may build an algorighm basing on this consideration.

1. For each number length from 1 to N
2. Generate all possible multi-sets of N digits
3. For each multi-set calculate sum of digits^N
4. Check if it''s possible to represent the number we got on step 4 with the digits from the multi-set
5. If so - add the number to the result list

