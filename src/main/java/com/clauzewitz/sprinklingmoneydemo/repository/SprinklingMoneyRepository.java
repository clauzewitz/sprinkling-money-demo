package com.clauzewitz.sprinklingmoneydemo.repository;

import com.clauzewitz.sprinklingmoneydemo.entity.SprinklingMoney;
import com.clauzewitz.sprinklingmoneydemo.prop.CacheProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "sprinklingMoneyRepository")
public interface SprinklingMoneyRepository extends MongoRepository<SprinklingMoney, String> {
    @Cacheable(value = CacheProperties.NAME, key = "#token", unless = "#result == null")
    public SprinklingMoney findOneByToken(String token);
    @Cacheable(value = CacheProperties.NAME, key = "#token", unless = "#result == null")
    public SprinklingMoney findOneByTokenAndRoomId(String token, String roomId);
    public SprinklingMoney findByRoomId(String roomId);
    public SprinklingMoney removeAllByRoomId(String roomId);
}
