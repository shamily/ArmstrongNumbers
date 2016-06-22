# Armstrong Numbers - Efficient Generation Algorithms

## What is that all about?

The task is to generate Armstrong Numbers from 1 up to the length of *N* decimal digits.

Armstrong number (aka Narcissistic number) of length *N* digits is a number which is equal to the sum of its digits each in power of *N*. For example: 153 = 1^3 + 5^3 + 3^3 = 3 + 125 + 27 = 153

More info at [wiki](https://en.wikipedia.org/wiki/Narcissistic_number)

## Brute force algorithm

There is an obvious bruteforce algorithms that:

0. Pre-generation of all powers *i^j*, where i is a digits, and j is possible length from 1 to *N* - this is necessary for all solutions
1. For each integer *i* from 1 to *K*
2. Divides *i* by digits
3. Calculate power of each digit
4. Sum up those powers
5. If this sum is equal to *i* - add it to the result list
 
Implementation: [ArmstrongNumbersBruteforce.java](https://github.com/shamily/ArmstrongNumbers/blob/master/ArmstrongNumbersBruteforce.java)

It can be improved by parallel calculation of sum of digit powers to the number generation.

Implementation: [ArmstrongNumbersBruteforceOpt.java](https://github.com/shamily/ArmstrongNumbers/blob/master/ArmstrongNumbersBruteforceOpt.java)

## Better approach - multi-sets

We may note that for each multi-set of digits, like [1, 1, 2, 4, 5, 7, 7] there is only one sum of powers, which in its turn may either be or be not represented by the digits from set. In the example 1^7 + 1^7 + 2^7 + 4^7 + 5^7 + 7^7 + 7^7 = 1741725, which can be represented by the digits and thus is an Armstrong number.

We may build an algorighm basing on this consideration.

1. For each number length from 1 to N
2. Generate all possible multi-sets of N digits
3. For each multi-set calculate sum of digits^N
4. Check if it''s possible to represent the number we got on step 4 with the digits from the multi-set
5. If so - add the number to the result list

Implementation of the algorithm with optimizations:
* For long: [ArmstrongNumbersLong.java](https://github.com/shamily/ArmstrongNumbers/blob/master/ArmstrongNumbersLong.java)
* For BigInteger: [ArmstrongNumbersBigInteger](https://github.com/shamily/ArmstrongNumbers/blob/master/ArmstrongNumbersBigInteger.java)

## Another approach - Hash

There is another interesting idea of bruteforce approach improvement. 

1. Divide a number for two equal parts. In case of an odd *N* first part will be a bit longer. For example, if *N*=7, the number will be divide like XXXXYYY, where XXXX the first part (4 decimal digits), and YYY the second part with 3 digits.
2. Generate all integers *i* of the second part (in our example there will be integers from 001 to 999).
3. Calculate *p* equal to sum of digits in power of *N*.
4. Add to some hash the following pair {p-i, i}. For example, for *i*=725, *p*=7^7+2^7+5^7=901796. We add pair {901071, 725}.
5. Generate all integers *i* of the first part without leading zeros (in our example there will be integers from 1000 to 9999).
6. Calculate *p* equal to sum of digits in power of *N*.
7. Check if hash has a key of (i\*10^(N/2)-p). For example, *i*=1741, thus *p*=1^7 + 7^7 + 4^7 + 1^7=839929. We look for key (1741000 - 839929) = (901071). OMG! It exists!!!
8. In case that key exists we unite the Armstrong number from two parts and add it to the result list. 1741000 + 725 = 1741725

One addition, is that we cannot store simply (key, value), we need to store multiple values, for example to be able to generate 370 and 371.

Implementation: [ArmstrongNumbersHash.java](https://github.com/shamily/ArmstrongNumbers/blob/master/ArmstrongNumbersHash.java)


## Benchmarking

Let's compare the algorithms performance for different numbers of length *N*. I did the tests with my MacBook Pro.

| Algorithm   | int (N<10) | long (N<20) | BigInteger (N<40)  | Comments |
| ------------- |:-------------:|:-----:|:-----:|-----|
| Brute Force            |  ~55 seconds | few thousand years | N/A | Just wait! |
| Improved Brute Force   | ~3.7 s       |  ~300 years        | N/A |            |
| Hash Approach          |  50 ms       | minutes            | N/A | Hash consumes quite a lot of memory and if we leave algorithm as is it will throw OutOfMemory |
| Multi-set Approach     | 11 ms        | ~550 ms            |~ 0.5 hours |     |


Clear win of the multi-set algorithm!
