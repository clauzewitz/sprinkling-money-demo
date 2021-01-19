package com.clauzewitz.sprinklingmoneydemo.service;

import com.clauzewitz.sprinklingmoneydemo.entity.SprinklingMoneyReceiver;
import com.clauzewitz.sprinklingmoneydemo.req.SprinklingMoneyReq;
import com.clauzewitz.sprinklingmoneydemo.vo.SprinklingMoneyDetailVO;
import com.clauzewitz.sprinklingmoneydemo.vo.SprinklingMoneyVO;

public interface SprinklingMoneyService {
    public SprinklingMoneyVO sprinklingMoney(SprinklingMoneyReq sprinklingMoneyReq);
    public SprinklingMoneyReceiver receiveSprinklingMoney(String token);
    public SprinklingMoneyDetailVO getSprinklingMoneyInfo(String token);
}
