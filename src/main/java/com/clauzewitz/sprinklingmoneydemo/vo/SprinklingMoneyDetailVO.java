package com.clauzewitz.sprinklingmoneydemo.vo;

import com.clauzewitz.sprinklingmoneydemo.type.CurrencyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Data
@Builder
public class SprinklingMoneyDetailVO {

    @NonNull
    private String token;
    @JsonIgnore
    private long userId;
    @NonNull
    private String roomId;
    private double amount;
    @NonNull
    private CurrencyType currencyType;
    @NonNull
    private List<SprinklingMoneyReceiverVO> toReceivers;
    private long createDate;
    private long expiredDate;
    private boolean expired;

    public double getReceivedAmount() {
        return this.toReceivers.stream().filter(receiver -> receiver.getUserId() > 0L).mapToDouble(SprinklingMoneyReceiverVO::getAmount).sum();
    }

}
