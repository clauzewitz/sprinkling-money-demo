package com.clauzewitz.sprinklingmoneydemo.req;

import com.clauzewitz.sprinklingmoneydemo.type.CurrencyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@NoArgsConstructor
public class SprinklingMoneyReq {
    @NonNull
    private double amount;
    @NonNull
    private int memberCnt;
    @NonNull
    private CurrencyType currencyType = CurrencyType.KRW;

    @JsonIgnore
    public boolean isValid() {
        return amount > 0d && memberCnt > 0 && (amount / memberCnt) >= 1;
    }

    public double getAmount() {
        return new BigDecimal(amount).setScale(currencyType.getDigit(), RoundingMode.DOWN).doubleValue();
    }
}
