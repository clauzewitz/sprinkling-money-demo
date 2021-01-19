package com.clauzewitz.sprinklingmoneydemo.util;

import com.clauzewitz.sprinklingmoneydemo.type.CurrencyType;
import io.netty.util.internal.MathUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class MoneyUtil {
    private static final int DEFAULT_SCALE = 2;
    private static BigDecimal MAX_PERCENTAGE = new BigDecimal(100.0f);


    public static List<Double> calcAmountPerMemberCnt(final double amount, final CurrencyType currencyType, final int memberCnt) {

        if ((int) amount == memberCnt) {
            return new ArrayList<Double>(Collections.nCopies(memberCnt, BigDecimal.ONE.doubleValue()));
        }

        BigDecimal bigAmount = new BigDecimal(amount).setScale(currencyType.getDigit(), RoundingMode.DOWN);

        List<BigDecimal> percentageList = MoneyUtil.calcPercentagePerMemberCnt(memberCnt, new BigDecimal(amount));
        return percentageList.stream()
                .map(percent -> bigAmount.multiply(percent).setScale(currencyType.getDigit(), RoundingMode.HALF_EVEN).doubleValue())
                .collect(Collectors.toList());
    }

    public static List<BigDecimal> calcPercentagePerMemberCnt(final int memberCnt, final BigDecimal amount) {
        Random random = new Random();
        List<BigDecimal> percentageList = new ArrayList<BigDecimal>(memberCnt);
        BigDecimal maxSeed = new BigDecimal(amount.toString());

        for (int i = 1; i < memberCnt; i++) {
            BigDecimal percentage = BigDecimal.ZERO;

            try {
                percentage = new BigDecimal(random.nextInt(maxSeed.intValue() - (memberCnt - i)));
            } catch (IllegalArgumentException e) {
                percentage = BigDecimal.ZERO;
            }

            if (percentage.compareTo(BigDecimal.ONE) < 0) {
                percentage = BigDecimal.ONE;
            }

            BigDecimal calcPercentage = percentage.divide(amount, DEFAULT_SCALE, RoundingMode.HALF_EVEN);

            if (calcPercentage.compareTo(BigDecimal.ZERO) == 0) {
                calcPercentage = percentage.divide(amount, DEFAULT_SCALE, RoundingMode.UP);
                percentage = calcPercentage.multiply(amount);
            }

            if (calcPercentage.compareTo(BigDecimal.ONE) == 0) {
                calcPercentage = percentage.divide(amount, DEFAULT_SCALE, RoundingMode.DOWN);
                percentage = calcPercentage.multiply(amount);
            }

            percentageList.add(calcPercentage);
            maxSeed = maxSeed.subtract(percentage);
        }

        percentageList.add(percentageList.stream().reduce(BigDecimal.ONE, (a, b) -> a.subtract(b)));
        return percentageList;
    }

    public static String getRandomizeString(final long seed, final int stringLength) {
        int leftLimit = 48;
        int rightLimit = 122;
        Random random = new Random(seed);

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

}
