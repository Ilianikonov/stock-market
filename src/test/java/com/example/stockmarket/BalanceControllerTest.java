package com.example.stockmarket;

import com.example.stockmarket.controller.response.GetBalanceResponse;
import com.example.stockmarket.entity.Trader;
import com.example.stockmarket.service.TraderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BalanceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    TraderService traderService;
    private static final String GET_CURRENCY_URL = "/balance/getBalanceByCurrency";
    private static final String MAKE_DEPOSITING_URL = "/balance/makeDepositing";
    private static final String WITHDRAW_CURRENCY_URL = "/balance/withdrawCurrency";
    private static final String EXCHANGE_CURRENCY_URL = "/balance/currencyExchange";
    private static final String GET_TOTAL_BALANCE_URL = "/balance/getTotalBalance";

    @Test
    public void makeDepositing() throws Exception{
        double amount = 14.5;
        String currency = "RUB";
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .param("traderId", String.valueOf(traderId))
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency))
                .andExpect(status().isOk());
        double amountBefore = getBalanceByCurrency(traderId,currency);
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .param("traderId", String.valueOf(traderId))
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency))
                .andExpect(status().isOk());
        double amountAfter = getBalanceByCurrency(traderId,currency);
        Assertions.assertEquals(amountBefore + amount,amountAfter);
    }
    public double getBalanceByCurrency(long traderId, String currency) throws Exception{
        String response = mockMvc.perform(get(GET_CURRENCY_URL)
                .param("traderId", String.valueOf(traderId))
                .param("currency", currency))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value(currency))
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, GetBalanceResponse.class).getAmount();
    }
    @Test
    public void withdrawCurrency() throws Exception{
        double amount = 14.5;
        String currency = "RUB";
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .param("traderId", String.valueOf(traderId))
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency))
                .andExpect(status().isOk());
        double amountBefore = getBalanceByCurrency(traderId,currency);
        mockMvc.perform(post(WITHDRAW_CURRENCY_URL)
                        .param("traderId", String.valueOf(traderId))
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency))
                .andExpect(status().isOk());
        double amountAfter = getBalanceByCurrency(traderId,currency);
        Assertions.assertEquals(amountBefore - amount,amountAfter);
    }
    @Test
    public void withdrawCurrency2() throws Exception{
        double amount = 14.5;
        String currency = "FEG";
        mockMvc.perform(post(WITHDRAW_CURRENCY_URL)
                        .param("traderId", String.valueOf(traderId))
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency))
                .andExpect(status().isNotFound());
    }
    @Test
    public void currencyExchangeTest() throws Exception{
        String currencyTo = "USD";
        String reduceCurrency = "RUB";
        double count = 124.12;
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .param("traderId", String.valueOf(traderId))
                        .param("amount", String.valueOf(121333))
                        .param("currency", reduceCurrency))
                .andExpect(status().isOk());
        double amountReduceCurrency = getBalanceByCurrency(traderId, reduceCurrency);
        System.out.println(amountReduceCurrency);
        mockMvc.perform(post(EXCHANGE_CURRENCY_URL)
                        .param("traderId", String.valueOf(traderId))
                        .param("count", String.valueOf(count))
                        .param("addCurrency", currencyTo)
                        .param("reduceCurrency", reduceCurrency))
                .andExpect(status().isOk());
        double amountReduceCurrencyAfter = getBalanceByCurrency(traderId,reduceCurrency);
        Assertions.assertTrue(amountReduceCurrency > amountReduceCurrencyAfter);
    }
    @Test
    public void currencyExchangeTest2() throws Exception{
        String addCurrency = "USD";
        String reduceCurrency = "RUB";
        double count = 1241.12;
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .param("traderId", String.valueOf(traderId))
                        .param("amount", String.valueOf(count))
                        .param("currency", reduceCurrency))
                .andExpect(status().isOk());
        double amountAddCurrency = getBalanceByCurrency(traderId,reduceCurrency);
        mockMvc.perform(post(EXCHANGE_CURRENCY_URL)
                        .param("traderId", String.valueOf(traderId))
                        .param("count", String.valueOf(13))
                        .param("addCurrency", addCurrency)
                        .param("reduceCurrency", reduceCurrency))
                .andExpect(status().isOk());
        double amountAddCurrencyAfter = getBalanceByCurrency(traderId,reduceCurrency);
        Assertions.assertTrue(amountAddCurrency > amountAddCurrencyAfter);
    }
@Test
public void getTotalBalance() throws Exception{ // todo: дописать тест
    String currency = "RUB";
    double count = 1244.12;
    mockMvc.perform(post(MAKE_DEPOSITING_URL)
                    .param("traderId", String.valueOf(traderId))
                    .param("amount", String.valueOf(124400))
                    .param("currency", currency))
            .andExpect(status().isOk());
    mockMvc.perform(post(WITHDRAW_CURRENCY_URL)
                    .param("traderId", String.valueOf(traderId))
                    .param("amount", String.valueOf(1000))
                    .param("currency", currency))
            .andExpect(status().isOk());
    mockMvc.perform(post(MAKE_DEPOSITING_URL)
                    .param("traderId", String.valueOf(traderId))
                    .param("amount", String.valueOf(31))
                    .param("currency", currency))
            .andExpect(status().isOk());
    mockMvc.perform(get(GET_TOTAL_BALANCE_URL)
                    .param("traderId", String.valueOf(traderId))
                    .param("currency", currency))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.amount").isNumber());
}
    @Test
    public void getBalanceByCurrency() throws Exception{
        double amount = 10.4;
        String currency = "RUB";
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .param("traderId", String.valueOf(traderId))
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency))
                .andExpect(status().isOk());
        double amountCurrency = getBalanceByCurrency(traderId,currency);
        Assertions.assertTrue(amountCurrency >= 0.0);
    }
    @Test
    public void getBalanceByCurrency2() throws Exception{
        String currency = "FWA";
        mockMvc.perform(get(GET_CURRENCY_URL)
                        .param("traderId", String.valueOf(traderId))
                        .param("currency", currency))
                .andExpect(status().isNotFound());
    }
    public Trader createTrader (String name) {
        Trader trader = new Trader();
        trader.setName(name);
        trader.setPassword("passworin".toCharArray());
        trader.setId(traderService.createTrader(trader).getId());
        return trader;
    }
}
