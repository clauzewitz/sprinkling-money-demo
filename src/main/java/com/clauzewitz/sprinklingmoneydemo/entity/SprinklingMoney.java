package com.clauzewitz.sprinklingmoneydemo.entity;

import com.clauzewitz.sprinklingmoneydemo.type.CurrencyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sprinklings")
public class SprinklingMoney implements Serializable {

    @Id
    private ObjectId id;
    @NonNull
    private long userId;
    @NonNull
    private String roomId;
    @Indexed(name = "idx_token", unique = true, direction = IndexDirection.ASCENDING)
    @NonNull
    private String token;
    @NonNull
    private double amount;
    @NonNull
    private CurrencyType currencyType;
    @NonNull
    private List<SprinklingMoneyReceiver> toReceivers;
    @Builder.Default
    private long createDate = Instant.now().toEpochMilli();
    @Builder.Default
    private long expiredDate = LocalDateTime.now().plusMinutes(10L).toInstant(ZoneOffset.UTC).toEpochMilli();

    @JsonIgnore
    public String getId() {
        return id.toString();
    }

    public SprinklingMoneyReceiver updateReceiver(long userId) {

        if (this.isAlreadyReceived(userId)) {
            return null;
        }

        SprinklingMoneyReceiver sprinklingMoneyReceiver = toReceivers.stream()
                .filter(receiver -> receiver.getUserId() <= 0).findFirst().orElse(null);

        if (sprinklingMoneyReceiver == null) {
            return null;
        }

        return sprinklingMoneyReceiver.received(userId);
    }

    @JsonIgnore
    public boolean isExpired() {
        LocalDateTime expiredDateTime = Instant.ofEpochMilli(this.expiredDate).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return LocalDateTime.now().isAfter(expiredDateTime);
    }

    @JsonIgnore
    public boolean isViewable() {
        LocalDateTime viewableDateTime = Instant.ofEpochMilli(this.createDate).atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(7L);
        return LocalDateTime.now().isBefore(viewableDateTime);
    }

    @JsonIgnore
    public boolean isAlreadyReceived(long userId) {
        return toReceivers.stream().anyMatch(receiver -> receiver.getUserId() == userId);
    }

    @JsonIgnore
    private long calculateExpiredDate() {
        LocalDateTime localDateTime = Instant.ofEpochMilli(this.createDate).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime.plusDays(7L).toInstant(ZoneOffset.UTC).toEpochMilli();
    }

}
