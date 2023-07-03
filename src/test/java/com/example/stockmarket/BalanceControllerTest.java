package com.example.stockmarket;

import com.example.stockmarket.controller.BalanceController;
import com.example.stockmarket.controller.response.GetBalanceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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

    private static final String GET_CURRENCY_URL = "/balance/getBalanceByCurrency";
    private static final String BUY_CURRENCY_URL = "/balance/buyCurrency";
    private static final String SELL_CURRENCY_URL = "/balance/sellCurrency";
    private static final String ADD_BALANCE_URL = "/balance/addCurrency";
    private static final String GET_TOTAL_BALANCE_URL = "/balance/getTotalBalance";
    private static final String GET_BALANCE_BY_CURRENCY_URL = "/balance/getBalanceByCurrency";

    @Test
    public void buyCurrency() throws Exception{
        long traderId = 1;
        double amount = 14.4;
        String currency = "RUB";
        double amountBefore = getBalanceByCurrency(traderId,currency);
        mockMvc.perform(post(BUY_CURRENCY_URL)
                        .param("traderId", String.valueOf(traderId))
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency))
                .andExpect(status().isOk());
        double amountAfter = getBalanceByCurrency(traderId,currency);
        Assertions.assertEquals(amountBefore + amount,amountAfter);
    }
    private double getBalanceByCurrency(long traderId, String currency) throws Exception{
        String response = mockMvc.perform(get(GET_CURRENCY_URL)
                .param("traderId", String.valueOf(traderId))
                .param("currency", currency))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value(currency))
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, GetBalanceResponse.class).getAmount();
    }
    @Test
    public void sellCurrency() throws Exception{
        long traderId = 1;
        double amount = 14.4;
        String currency = "RUB";
        double amountBefore = getBalanceByCurrency(traderId,currency);
        mockMvc.perform(post(SELL_CURRENCY_URL)
                        .param("traderId", String.valueOf(traderId))
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency))
                .andExpect(status().isOk());
        double amountAfter = getBalanceByCurrency(traderId,currency);
        Assertions.assertEquals(amountBefore - amount,amountAfter);
    }
    @Test
    public void addCurrency() throws Exception{
        long traderId = 1;
        double amount = 112.1;
        String currency = "RUB";
        mockMvc.perform(post(ADD_BALANCE_URL)
                        .param("traderId", String.valueOf(traderId))
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency))
                .andExpect(status().isOk());
        double amountAfter = getBalanceByCurrency(traderId,currency);
        Assertions.assertEquals(amount,amountAfter);
    }
@Test
public void getTotalBalance() throws Exception{
    long traderId = 1;
    double amount;
    String currency = "RUB";
    double amountCurrency = getBalanceByCurrency(traderId,currency);
    MvcResult amountAfter = mockMvc.perform(get(GET_TOTAL_BALANCE_URL)
                    .param("traderId", String.valueOf(traderId))
                    .param("currency", currency))
            .andExpect(status().isOk())
                    .andReturn();
    Assertions.assertEquals(amountCurrency,amountAfter);
}
    @Test
    public void getBalanceByCurrency() throws Exception{
        long traderId = 1;
        String currency = "RUB";
        double amountCurrency = getBalanceByCurrency(traderId,currency);
        mockMvc.perform(get(GET_BALANCE_BY_CURRENCY_URL)
                .param("traderId", String.valueOf(traderId))
                .param("currency", currency))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(amountCurrency));
    }
}
