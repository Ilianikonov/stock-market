package com.ilianikonov.stockmarket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilianikonov.stockmarket.controller.request.transaction.MakeDepositingRequest;
import com.ilianikonov.stockmarket.controller.request.transaction.WithdrawCurrencyRequest;
import com.ilianikonov.stockmarket.controller.response.GetBalanceResponse;
import com.ilianikonov.stockmarket.entity.Trader;
import com.ilianikonov.stockmarket.service.TraderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
public class BalanceControllerImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    TraderService traderService;
    private static final String GET_CURRENCY_URL = "/balance/getBalanceByCurrency";
    private static final String MAKE_DEPOSITING_URL = "/transaction/makeDepositing";
    private static final String WITHDRAW_CURRENCY_URL = "/transaction/withdrawCurrency";
    private static final String GET_TOTAL_BALANCE_BY_CURRENCY_URL = "/balance/getTotalBalanceByCurreny";
    private static final String GET_TOTAL_BALANCE_URL = "/balance/getTotalBalance";


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
    public void getTotalBalanceByCurreny() throws Exception{ // todo: дописать тест
        String currency = "RUB";
        Trader trader = createTrader("aefs1veqq");
        double count = 1244.12;
        MakeDepositingRequest makeDepositingRequest = new MakeDepositingRequest();
        makeDepositingRequest.setTraderId(trader.getId());
        makeDepositingRequest.setReceivedCurrency(currency);
        makeDepositingRequest.setReceivedAmount(124400);
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(makeDepositingRequest)))
                .andExpect(status().isOk());
        WithdrawCurrencyRequest withdrawCurrencyRequest = new WithdrawCurrencyRequest();
        withdrawCurrencyRequest.setTraderId(trader.getId());
        withdrawCurrencyRequest.setGivenCurrency(currency);
        withdrawCurrencyRequest.setGivenAmount(1000);
        mockMvc.perform(post(WITHDRAW_CURRENCY_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawCurrencyRequest)))
                .andExpect(status().isOk());
        makeDepositingRequest.setReceivedAmount(31);
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(makeDepositingRequest)))
                .andExpect(status().isOk());
        mockMvc.perform(get(GET_TOTAL_BALANCE_BY_CURRENCY_URL)
                        .param("traderId", String.valueOf(trader.getId()))
                        .param("currency", currency))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.amount").isNumber());
    }
    @Test
    public void getTotalBalance() throws Exception {
        Trader trader = createTrader("fawf");
        MakeDepositingRequest makeDepositingRequest = new MakeDepositingRequest();
        makeDepositingRequest.setTraderId(trader.getId());
        makeDepositingRequest.setReceivedCurrency("USD");
        makeDepositingRequest.setReceivedAmount(124400);
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(makeDepositingRequest)))
                .andExpect(status().isOk());
        makeDepositingRequest.setReceivedCurrency("RUB");
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(makeDepositingRequest)))
                .andExpect(status().isOk());
        mockMvc.perform(get(GET_TOTAL_BALANCE_URL)
                        .param("traderId", String.valueOf(trader.getId())))
                .andExpect(status().isOk());
        Assertions.assertTrue(traderService.getTraderById(trader.getId()).getTotalBalance().size() == 2);
    }
    @Test
    public void getBalanceByCurrency() throws Exception{
        Trader trader = createTrader("ae1s14q12");
        double amount = 10.4;
        String currency = "RUB";
        MakeDepositingRequest makeDepositingRequest = new MakeDepositingRequest();
        makeDepositingRequest.setTraderId(trader.getId());
        makeDepositingRequest.setReceivedCurrency(currency);
        makeDepositingRequest.setReceivedAmount(amount);
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(makeDepositingRequest)))
                .andExpect(status().isOk());
        double amountCurrency = getBalanceByCurrency(trader.getId(),currency);
        Assertions.assertTrue(amountCurrency >= 0.0);
    }
    @Test
    public void getBalanceByCurrency2() throws Exception{
        Trader trader = createTrader("aefseqq");
        String currency = "FWA";
        mockMvc.perform(get(GET_CURRENCY_URL)
                        .param("traderId", String.valueOf(trader.getId()))
                        .param("currency", currency))
                .andExpect(status().isNotFound());
    }
    public Trader createTrader (String name) {
        Trader trader = new Trader();
        trader.setName(name);
        trader.setPassword("passworin");
        trader.setId(traderService.createTrader(trader).getId());
        return trader;
    }
}
