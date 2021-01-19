package com.clauzewitz.sprinklingmoneydemo.controller;

import com.clauzewitz.sprinklingmoneydemo.entity.SprinklingMoneyReceiver;
import com.clauzewitz.sprinklingmoneydemo.handler.exception.BadRequestException;
import com.clauzewitz.sprinklingmoneydemo.req.SprinklingMoneyReq;
import com.clauzewitz.sprinklingmoneydemo.service.SprinklingMoneyService;
import com.clauzewitz.sprinklingmoneydemo.vo.SprinklingMoneyDetailVO;
import com.clauzewitz.sprinklingmoneydemo.vo.SprinklingMoneyVO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/sprinkling")
public class SprinklingMoneyController {

    @NonNull
    private final SprinklingMoneyService sprinklingMoneyService;

    @PostMapping
    public ResponseEntity<SprinklingMoneyVO> sprinklingMoney(@RequestBody SprinklingMoneyReq sprinklingMoneyReq) {

        if (!sprinklingMoneyReq.isValid()) {
            throw new BadRequestException();
        }

        SprinklingMoneyVO sprinklingMoneyVO = sprinklingMoneyService.sprinklingMoney(sprinklingMoneyReq);

        if (sprinklingMoneyVO == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().body(sprinklingMoneyVO);
    }

    @PutMapping(path = "/{token}")
    public ResponseEntity<SprinklingMoneyReceiver> receiveSprinklingMoney(@PathVariable String token) {
        SprinklingMoneyReceiver receiver = sprinklingMoneyService.receiveSprinklingMoney(token);

        return ResponseEntity.ok().body(receiver);
    }

    @GetMapping(path = "/{token}")
    public ResponseEntity<SprinklingMoneyDetailVO> getSprinklingMoney(@PathVariable String token) {
        SprinklingMoneyDetailVO sprinklingMoneyVO = sprinklingMoneyService.getSprinklingMoneyInfo(token);

        return ResponseEntity.ok().body(sprinklingMoneyVO);
    }

}
