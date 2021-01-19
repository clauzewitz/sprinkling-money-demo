package com.clauzewitz.sprinklingmoneydemo.serviceImpl;

import com.clauzewitz.sprinklingmoneydemo.entity.SprinklingMoney;
import com.clauzewitz.sprinklingmoneydemo.entity.SprinklingMoneyReceiver;
import com.clauzewitz.sprinklingmoneydemo.handler.exception.*;
import com.clauzewitz.sprinklingmoneydemo.prop.CacheProperties;
import com.clauzewitz.sprinklingmoneydemo.repository.SprinklingMoneyRepository;
import com.clauzewitz.sprinklingmoneydemo.req.SprinklingMoneyReq;
import com.clauzewitz.sprinklingmoneydemo.service.SprinklingMoneyService;
import com.clauzewitz.sprinklingmoneydemo.type.CurrencyType;
import com.clauzewitz.sprinklingmoneydemo.util.AuthUtil;
import com.clauzewitz.sprinklingmoneydemo.util.MoneyUtil;
import com.clauzewitz.sprinklingmoneydemo.vo.SprinklingMoneyDetailVO;
import com.clauzewitz.sprinklingmoneydemo.vo.SprinklingMoneyReceiverVO;
import com.clauzewitz.sprinklingmoneydemo.vo.SprinklingMoneyVO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "sprinklingMoneyService")
@RequiredArgsConstructor
public class SprinklingMoneyServiceImpl implements SprinklingMoneyService {

    @NonNull
    private final SprinklingMoneyRepository sprinklingMoneyRepository;

    @Override
    @Transactional
    public SprinklingMoneyVO sprinklingMoney(SprinklingMoneyReq sprinklingMoneyReq) {
        long userId = AuthUtil.getUserId();
        String roomId = AuthUtil.getRoomId();

        if (!StringUtils.hasText(roomId)) {
            throw new BadRequestException();
        }

        double amount = sprinklingMoneyReq.getAmount();
        CurrencyType currencyType = sprinklingMoneyReq.getCurrencyType();
        int memberCnt = sprinklingMoneyReq.getMemberCnt();
        long temp = userId + memberCnt + Instant.now().toEpochMilli();

        List<Double> amountList = MoneyUtil.calcAmountPerMemberCnt(amount, currencyType, memberCnt);
        System.out.println(amountList.stream().reduce((a, b) -> a + b));
        String token = MoneyUtil.getRandomizeString(temp << 23, 3);
        SprinklingMoney sprinklingMoney = SprinklingMoney.builder()
                .userId(userId)
                .roomId(roomId)
                .token(token)
                .amount(amount)
                .currencyType(currencyType)
                .toReceivers(amountList.stream().map(receiveAmount -> SprinklingMoneyReceiver.builder()
                        .amount(receiveAmount).currencyType(currencyType).build()).collect(Collectors.toList()))
                .build();

        sprinklingMoneyRepository.save(sprinklingMoney);
        SprinklingMoneyVO sprinklingMoneyVO = SprinklingMoneyVO.builder()
                .token(token)
                .roomId(roomId)
                .amount(amount)
                .currencyType(currencyType)
                .createDate(sprinklingMoney.getCreateDate())
                .expiredDate(sprinklingMoney.getExpiredDate())
                .expired(sprinklingMoney.isExpired())
                .build();

        return sprinklingMoneyVO;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheProperties.NAME, key = "#token")
    public SprinklingMoneyReceiver receiveSprinklingMoney(final String token) {
        long userId = AuthUtil.getUserId();
        String roomId = AuthUtil.getRoomId();

        if (!StringUtils.hasText(roomId)) {
            throw new BadRequestException();
        }

        SprinklingMoney sprinklingMoney = this.getSprinklingMoney(token, roomId);

        if (sprinklingMoney.isExpired()) {
            throw new ExpiredException();
        }

        if (userId == sprinklingMoney.getUserId()) {
            throw new NoPermissionException();
        }

        if (sprinklingMoney.isAlreadyReceived(userId)) {
            throw new AlreadyReceivedException();
        }

        SprinklingMoneyReceiver receiver = sprinklingMoney.updateReceiver(userId);

        if (receiver == null) {
            throw new NotReceivedException();
        }

        sprinklingMoneyRepository.save(sprinklingMoney);

        return receiver;
    }

    @Override
    public SprinklingMoneyDetailVO getSprinklingMoneyInfo(String token) {
        long userId = AuthUtil.getUserId();
        String roomId = AuthUtil.getRoomId();

        if (!StringUtils.hasText(roomId)) {
            throw new BadRequestException();
        }

        SprinklingMoney sprinklingMoney = this.getSprinklingMoney(token, roomId);

        if (userId != sprinklingMoney.getUserId()) {
            throw new NoPermissionException();
        }

        if (sprinklingMoney.isExpired() || !sprinklingMoney.isViewable()) {
            throw new ExpiredException();
        }

        SprinklingMoneyDetailVO sprinklingMoneyVO = SprinklingMoneyDetailVO.builder()
                .token(token)
                .roomId(roomId)
                .amount(sprinklingMoney.getAmount())
                .currencyType(sprinklingMoney.getCurrencyType())
                .toReceivers(sprinklingMoney.getToReceivers().stream().map(receiver ->
                    SprinklingMoneyReceiverVO.builder()
                        .userId(receiver.getUserId())
                        .amount(receiver.getAmount())
                        .currencyType(receiver.getCurrencyType())
                        .receivedDate(receiver.getReceivedDate())
                        .build()).collect(Collectors.toList()))
                .createDate(sprinklingMoney.getCreateDate())
                .expiredDate(sprinklingMoney.getExpiredDate())
                .expired(sprinklingMoney.isExpired())
                .build();

        return sprinklingMoneyVO;
    }

    private SprinklingMoney getSprinklingMoney(String token) {
        SprinklingMoney sprinklingMoney = sprinklingMoneyRepository.findOneByToken(token);

        if (sprinklingMoney == null) {
            throw new NotExsistException();
        }

        return sprinklingMoney;
    }

    private SprinklingMoney getSprinklingMoney(String token, String roomId) {
        SprinklingMoney sprinklingMoney = sprinklingMoneyRepository.findOneByTokenAndRoomId(token, roomId);

        if (sprinklingMoney == null) {
            throw new NotExsistException();
        }

        return sprinklingMoney;
    }

}
