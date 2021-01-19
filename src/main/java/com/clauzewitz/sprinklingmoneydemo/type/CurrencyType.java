package com.clauzewitz.sprinklingmoneydemo.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum CurrencyType {
    USD("USD", 2),
    KRW("KRW", 0);

    private String code;
    private int digit;

    public static CurrencyType getCurrencyTypeByCode(String code) {
        return Arrays.stream(CurrencyType.values())
                .filter(currencyType -> currencyType.code.equals(code))
                .findFirst()
                .orElse(null);
    }

    public static CurrencyType getCurrencyTypeByCode(CurrencyType type) {
        return Arrays.stream(CurrencyType.values())
                .filter(currencyType -> currencyType.equals(type))
                .findFirst()
                .orElse(null);
    }
}
