package com.clauzewitz.sprinklingmoneydemo.vo;

import com.clauzewitz.sprinklingmoneydemo.type.CurrencyType;
import lombok.*;

@Data
@Builder
public class SprinklingMoneyReceiverVO {
    private long userId;
    private double amount;
    private long receivedDate;
    @NonNull
    private CurrencyType currencyType;
}
