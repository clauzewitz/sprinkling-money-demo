package com.clauzewitz.sprinklingmoneydemo.vo;

import com.clauzewitz.sprinklingmoneydemo.type.CurrencyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class SprinklingMoneyVO {
    @NonNull
    private String token;
    @JsonIgnore
    private long userId;
    @NonNull
    private String roomId;
    private double amount;
    @NonNull
    private CurrencyType currencyType;
    private long createDate;
    private long expiredDate;
    private boolean expired;

}
