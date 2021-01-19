//package com.clauzewitz.sprinklingmoneydemo;
//
//import com.clauzewitz.sprinklingmoneydemo.repository.SprinklingMoneyRepository;
//import com.clauzewitz.sprinklingmoneydemo.req.SprinklingMoneyReq;
//import com.clauzewitz.sprinklingmoneydemo.serviceImpl.SprinklingMoneyServiceImpl;
//import com.clauzewitz.sprinklingmoneydemo.vo.SprinklingMoneyVO;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.http.*;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MockMvcBuilder;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
////@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Repository.class, Service.class, Configuration.class}))
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Transactional
//@AutoConfigureMockMvc
//@ActiveProfiles("dev")
//public class SprinklingMoneyTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext ctx;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private SprinklingMoneyServiceImpl sprinklingMoneyServiceImpl;
//
//    @Test
//    public void sprinklingMoney() throws Exception {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("X-USER-ID", "232324");
//        headers.set("X-ROOM-ID", "asd89fs8");
//
//        SprinklingMoneyReq sprinklingMoneyReq = new SprinklingMoneyReq();
//        sprinklingMoneyReq.setAmount(20000.0D);
//        sprinklingMoneyReq.setMemberCnt(4);
//
//        HttpEntity<SprinklingMoneyReq> request = new HttpEntity<SprinklingMoneyReq>(sprinklingMoneyReq, headers);
//
//        ResponseEntity<SprinklingMoneyVO> response = restTemplate.postForEntity("/sprinkling", request, SprinklingMoneyVO.class);
//
//        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
//
//        this.mockMvc.perform()
//
////        sprinklingMoneyServiceImpl.sprinklingMoney(sprinklingMoneyReq);
//    }
//}
