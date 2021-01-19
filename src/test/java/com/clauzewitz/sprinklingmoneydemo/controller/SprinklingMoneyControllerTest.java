package com.clauzewitz.sprinklingmoneydemo.controller;

import com.clauzewitz.sprinklingmoneydemo.entity.SprinklingMoney;
import com.clauzewitz.sprinklingmoneydemo.repository.SprinklingMoneyRepository;
import com.clauzewitz.sprinklingmoneydemo.req.SprinklingMoneyReq;
import com.clauzewitz.sprinklingmoneydemo.service.SprinklingMoneyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("뿌리기 API 테스트")
class SprinklingMoneyControllerTest {
    final String BASE_URI = "/sprinkling";

    @Autowired
    private MockMvc mockMvc;

    @Resource(name = "sprinklingMoneyService")
    private SprinklingMoneyService sprinklingMoneyService;

    @Resource(name = "sprinklingMoneyRepository")
    private SprinklingMoneyRepository sprinklingMoneyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public String toJsonString(SprinklingMoneyReq sprinklingMoneyReq) throws JsonProcessingException {
        return this.objectMapper.writeValueAsString(sprinklingMoneyReq);
    }

    @ParameterizedTest
    @CsvSource({
        "232324, asd89fs8, 20000, 4, 200",
        "459384, a23hklsf, 800, 2, 200",
        "102839, zv903sdf, 0, 4, 400",
        "118402, tiow93zz, 1000, 1, 200",
        "859473, 94lxlsja, 10, 20, 400"
    })
    @DisplayName("01. 뿌리기 등록 테스트")
    public void testSprinklingMoney(String userId, String roomId, double amount, int memberCnt, int httpStatus) throws Exception {
        sprinklingMoneyRepository.removeAllByRoomId(roomId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-USER-ID", userId);
        headers.set("X-ROOM-ID", roomId);

        SprinklingMoneyReq sprinklingMoneyReq = new SprinklingMoneyReq();
        sprinklingMoneyReq.setAmount(amount);
        sprinklingMoneyReq.setMemberCnt(memberCnt);

        ResultActions actions = this.mockMvc.perform(MockMvcRequestBuilders
                .post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content(this.toJsonString(sprinklingMoneyReq)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(httpStatus));
    }

    @ParameterizedTest
    @CsvSource({
            "232324, 132322, asd89fs8, 20000, 4, 200",
            "459384, 123456, a23hklsf, 800, 2, 200",
            "118402, 889988, zv903sdf, 1000, 1, 200",
            "118402, 889988, 2342asfa, 1000, 1, 404"
    })
    @DisplayName("02. 뿌리기 등록 테스트")
    public void testRecieveSprinklingMoney(String ownerId, String userId, String roomId, double amount, int memberCnt, int httpStatus) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-USER-ID", ownerId);
        headers.set("X-ROOM-ID", roomId);

        SprinklingMoneyReq sprinklingMoneyReq = new SprinklingMoneyReq();
        sprinklingMoneyReq.setAmount(amount);
        sprinklingMoneyReq.setMemberCnt(memberCnt);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content(this.toJsonString(sprinklingMoneyReq)));

        SprinklingMoney sprinklingMoney = sprinklingMoneyRepository.findByRoomId(roomId);

        if (httpStatus == HttpStatus.NOT_FOUND.value()) {
            sprinklingMoney.setToken("aaa");
        }

        headers.set("X-USER-ID", userId);

        ResultActions actions = this.mockMvc.perform(MockMvcRequestBuilders
                .put(String.format("%s/%s", BASE_URI, sprinklingMoney.getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(httpStatus));
    }

    @ParameterizedTest
    @CsvSource({
            "232324, 132322, asd89fs8, 20000, 4, 200",
            "459384, , a23hklsf, 800, 2, 200",
            "118402, 889988, zv903sdf, 1000, 1, 200",
            "118402, , 2342asfa, 1000, 1, 404"
    })
    @DisplayName("03. 뿌리기 내용 조회 테스트")
    public void testGetSprinklingMoney(String ownerId, String userId, String roomId, double amount, int memberCnt, int httpStatus) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-USER-ID", ownerId);
        headers.set("X-ROOM-ID", roomId);

        SprinklingMoneyReq sprinklingMoneyReq = new SprinklingMoneyReq();
        sprinklingMoneyReq.setAmount(amount);
        sprinklingMoneyReq.setMemberCnt(memberCnt);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content(this.toJsonString(sprinklingMoneyReq)));

        SprinklingMoney sprinklingMoney = sprinklingMoneyRepository.findByRoomId(roomId);

        if (userId != null) {
            headers.set("X-USER-ID", userId);

            ResultActions actions = this.mockMvc.perform(MockMvcRequestBuilders
                    .put(String.format("%s/%s", BASE_URI, sprinklingMoney.getToken()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(headers));
        }

        if (httpStatus == HttpStatus.NOT_FOUND.value()) {
            sprinklingMoney.setToken("aaa");
        }

        headers.set("X-USER-ID", ownerId);

        ResultActions actions = this.mockMvc.perform(MockMvcRequestBuilders
                .get(String.format("%s/%s", BASE_URI, sprinklingMoney.getToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(httpStatus));
    }
}