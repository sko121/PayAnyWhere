package com.britesky.payanywhere.api;

import android.util.Log;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Money {

    private BigDecimal _value;

    public Money() {
        _value = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }

    public Money(long l) {
        _value = (new BigDecimal(l)).setScale(2, RoundingMode.HALF_UP);
    }

    public Money(String s) {
        try {
            _value = (new BigDecimal(toBigDecimalString(s))).setScale(2,
                    RoundingMode.HALF_UP);
            return;
        } catch (NumberFormatException numberformatexception) {
            Log.d("NAB Number Format Exception",
                    numberformatexception.getMessage());
            _value = BigDecimal.ZERO;
        }
    }

    public Money(BigDecimal bigdecimal) {
        if (bigdecimal == null) {
            bigdecimal = BigDecimal.ZERO;
        }
        _value = bigdecimal;
        _value = _value.setScale(2, RoundingMode.HALF_UP);
    }

    public static Money add(Money money, Money amoney[]) {
        BigDecimal bigdecimal = money.toBigDecimal();
        if (amoney != null) {
            int i = amoney.length;
            for (int j = 0; j < i; j++) {
                Money money1 = amoney[j];
                if (money1 != null) {
                    bigdecimal = bigdecimal.add(money1.toBigDecimal());
                }
            }

        }
        return new Money(bigdecimal);
    }

    public static Money divide(Money money, int i) {
        return divide(money._value, new BigDecimal(i));
    }

    public static Money divide(Money money, Money money1) {
        return divide(money._value, money1._value);
    }

    public static Money divide(Money money, BigDecimal bigdecimal) {
        return divide(money._value, bigdecimal);
    }

    public static Money divide(BigDecimal bigdecimal, Money money) {
        return divide(bigdecimal, money._value);
    }

    private static Money divide(BigDecimal bigdecimal, BigDecimal bigdecimal1) {
        return new Money(bigdecimal.divide(bigdecimal1, RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP));
    }

    public static Money multiply(Money money, int i) {
        return multiply(money._value, new BigDecimal(i));
    }

    public static Money multiply(Money money, Money money1) {
        return multiply(money._value, money1._value);
    }

    public static Money multiply(Money money, BigDecimal bigdecimal) {
        return multiply(money._value, bigdecimal);
    }

    private static Money multiply(BigDecimal bigdecimal, BigDecimal bigdecimal1) {
        return new Money(bigdecimal.multiply(bigdecimal1).setScale(2,
                RoundingMode.HALF_UP));
    }

    public static Money multiplyRoundDown(Money money, BigDecimal bigdecimal) {
        return new Money(money._value.multiply(bigdecimal).setScale(2,
                RoundingMode.FLOOR));
    }

    public static Money multiplyRoundUp(Money money, BigDecimal bigdecimal) {
        return new Money(money._value.multiply(bigdecimal)
                .setScale(3, RoundingMode.FLOOR)
                .setScale(2, RoundingMode.CEILING));
    }

    public static Money subtract(Money money, Money amoney[]) {
        BigDecimal bigdecimal = money.toBigDecimal();
        int i = amoney.length;
        for (int j = 0; j < i; j++) {
            bigdecimal = bigdecimal.subtract(amoney[j].toBigDecimal());
        }

        return new Money(bigdecimal);
    }

    public static Money subtract(BigDecimal bigdecimal,
            Money amoney[]) {
        int i = amoney.length;
        for (int j = 0; j < i; j++) {
            bigdecimal = bigdecimal.subtract(amoney[j].toBigDecimal());
        }

        return new Money(bigdecimal);
    }

    public static String toBigDecimalString(String s) {
        String s1;
        try {
            s1 = NumberFormat.getCurrencyInstance(Locale.US).parse(s)
                    .toString();
        } catch (ParseException parseexception) {
            return s;
        }
        return s1;
    }

    public Money add(Money money) {
        if (money != null) {
            _value = _value.add(money._value);
        }
        return this;
    }

    public Money add(BigDecimal bigdecimal) {
        if (bigdecimal != null) {
            _value = _value.add(bigdecimal.setScale(2, RoundingMode.HALF_UP));
        }
        return this;
    }

    public BigDecimal addByValue(Money money) {
        return _value.add(money._value);
    }

    public Money divide(BigDecimal bigdecimal) {
        _value = _value.divide(bigdecimal.setScale(2, RoundingMode.HALF_UP));
        return this;
    }

    public boolean isEqualTo(Money money) {
        return toFormattedString().equals(money.toFormattedString());
    }

    public boolean isGreaterThan(Money money) {
        return 1 == _value.compareTo(money._value);
    }

    public boolean isGreaterThan(BigDecimal bigdecimal) {
        return 1 == _value.compareTo(bigdecimal);
    }

    public boolean isGreaterThanZero() {
        return 1 == _value.compareTo(BigDecimal.ZERO);
    }

    public boolean isLessThan(Money money) {
        return -1 == _value.compareTo(money._value);
    }

    public boolean isLessThan(BigDecimal bigdecimal) {
        return -1 == _value.compareTo(bigdecimal);
    }

    public boolean isLessThanZero() {
        return -1 == _value.compareTo(BigDecimal.ZERO);
    }

    public Money multiply(BigDecimal bigdecimal) {
        _value = _value.multiply(bigdecimal.setScale(2, RoundingMode.HALF_UP));
        return this;
    }

    public Money subtract(Money money) {
        _value = _value.subtract(money._value);
        return this;
    }

    public BigDecimal subtractByValue(Money money) {
        return _value.subtract(money._value);
    }

    public BigDecimal toBigDecimal() {
        return _value;
    }

    public double toDouble() {
        return _value.doubleValue();
    }

    public String toFormattedString() {
        return NumberFormat.getCurrencyInstance(Locale.US).format(toDouble());
    }

    public int toInt() {
        return _value.intValue();
    }

    public long toLong() {
        return _value.longValue();
    }

    public String toPlainString() {
        return _value.toPlainString();
    }

    public String toString() {
        return toFormattedString();
    }
}
