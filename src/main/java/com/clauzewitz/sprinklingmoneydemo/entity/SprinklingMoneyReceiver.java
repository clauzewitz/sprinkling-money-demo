package com.clauzewitz.sprinklingmoneydemo.entity;

import com.clauzewitz.sprinklingmoneydemo.type.CurrencyType;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprinklingMoneyReceiver implements Serializable {
    private long userId;
    private double amount;
    @NonNull
    private CurrencyType currencyType;
    private long receivedDate;

    public SprinklingMoneyReceiver received(long userId) {
        this.userId = userId;
        this.receivedDate = Instant.now().toEpochMilli();
        return this;
    }
}
