package com.example.stockmarket;

import com.example.stockmarket.controller.BalanceController;
import com.example.stockmarket.controller.response.GetBalanceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BalanceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String GET_CURRENCY_URL = "/balance/getCurrency";
    private static final String BUY_CURRENCY_URL = "/balance/buyCurrency";
    @Test
    public void balanceBuyCurrency() throws Exception{

        mockMvc.perform(post("/balance/buyCurrency")
                        .param("traderId", "1")
                        .param("amount", "14.4")
                        .param("currency", "RUB"))
                .andExpect(status().isOk());
        getBalanceByCurrency(1,"RUB");

    }
    public double getBalanceByCurrency(long traderId, String currency) throws Exception{
        String response = mockMvc.perform(get(GET_CURRENCY_URL)
                .param("traderId", String.valueOf(traderId))
                .param("currency", currency))
//                .andExpect(jsonPath("$.currency").value(currency))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, GetBalanceResponse.class).getAmount();
    }
//    @Test
//    public void sellCurrency() throws Exception{
//
//        mockMvc.perform(post("/balance/sellCurrency")
//                        .param("traderId", "1")
//                        .param("amount", "14.4")
//                        .param("currency", "RUB"))
//                .andExpect(status().isOk());
//        getBalanceByCurrency(1,"RUB");
//
//    }

}
