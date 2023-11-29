package com.ilianikonov.stockmarket;

import com.fasterxml.jackson.core.JsonParser;
import com.ilianikonov.stockmarket.controller.request.transaction.CurrencyExchangeRequest;
import com.ilianikonov.stockmarket.controller.request.transaction.MakeDepositingRequest;
import com.ilianikonov.stockmarket.controller.request.transaction.WithdrawCurrencyRequest;
import com.ilianikonov.stockmarket.controller.response.GetBalanceResponse;
import com.ilianikonov.stockmarket.entity.Trader;
import com.ilianikonov.stockmarket.service.TraderService;
import com.ilianikonov.stockmarket.service.currency.WebCurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionControllerImplTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    TraderService traderService;

    private static final String GET_CURRENCY_URL = "/balance/getBalanceByCurrency";
    private static final String MAKE_DEPOSITING_URL = "/transaction/makeDepositing";
    private static final String WITHDRAW_CURRENCY_URL = "/transaction/withdrawCurrency";
    private static final String EXCHANGE_CURRENCY_URL = "/transaction/currencyExchange";
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
    public void makeDepositing() throws Exception{
        Trader trader = createTrader("awefweeqq");
        double amount = 14.5;
        String currency = "RUB";
        MakeDepositingRequest makeDepositingRequest = new MakeDepositingRequest();
        makeDepositingRequest.setTraderId(trader.getId());
        makeDepositingRequest.setReceivedCurrency(currency);
        makeDepositingRequest.setReceivedAmount(amount);
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(makeDepositingRequest)))
                .andExpect(status().isOk());
        double amountBefore = getBalanceByCurrency(trader.getId(),currency);
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(makeDepositingRequest)))
                .andExpect(status().isOk());
        double amountAfter = getBalanceByCurrency(trader.getId(),currency);
        Assertions.assertEquals(amountBefore + amount,amountAfter);
    }
    @Test
    public void withdrawCurrency() throws Exception{
        Trader trader = createTrader("wefweqq");
        double amount = 14.5;
        String currency = "RUB";
        MakeDepositingRequest makeDepositingRequest = new MakeDepositingRequest();
        makeDepositingRequest.setTraderId(trader.getId());
        makeDepositingRequest.setReceivedCurrency(currency);
        makeDepositingRequest.setReceivedAmount(100000);
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(makeDepositingRequest)))
                .andExpect(status().isOk());
        WithdrawCurrencyRequest withdrawCurrencyRequest = new WithdrawCurrencyRequest();
        withdrawCurrencyRequest.setTraderId(trader.getId());
        withdrawCurrencyRequest.setGivenCurrency(currency);
        withdrawCurrencyRequest.setGivenAmount(amount);
        mockMvc.perform(post(WITHDRAW_CURRENCY_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawCurrencyRequest)))
                .andExpect(status().isOk());
        double amountBefore = getBalanceByCurrency(trader.getId(),currency);
        mockMvc.perform(post(WITHDRAW_CURRENCY_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawCurrencyRequest)))
                .andExpect(status().isOk());
        double amountAfter = getBalanceByCurrency(trader.getId(),currency);
        Assertions.assertEquals(amountBefore - amount,amountAfter);
    }
    @Test
    public void withdrawCurrency2() throws Exception{
        Trader trader = createTrader("aqeqfqq");
        double amount = 14.5;
        String currency = "FEG";
        WithdrawCurrencyRequest withdrawCurrencyRequest = new WithdrawCurrencyRequest();
        withdrawCurrencyRequest.setTraderId(trader.getId());
        withdrawCurrencyRequest.setGivenCurrency(currency);
        withdrawCurrencyRequest.setGivenAmount(amount);
        mockMvc.perform(post(WITHDRAW_CURRENCY_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawCurrencyRequest)))
                .andExpect(status().isNotFound());
    }
    @Test
    public void currencyExchangeTest() throws Exception{
        Trader trader = createTrader("argtneqq");
        String givenCurrency = "USD";
        String receivedCurrency = "RUB";
        double count = 124.12;
        CurrencyExchangeRequest currencyExchangeRequest = new CurrencyExchangeRequest();
        currencyExchangeRequest.setTraderId(trader.getId());
        currencyExchangeRequest.setReceivedCurrency(receivedCurrency);
        currencyExchangeRequest.setGivenCurrency(givenCurrency);
        currencyExchangeRequest.setGivenAmount(count);

        MakeDepositingRequest makeDepositingRequest = new MakeDepositingRequest();
        makeDepositingRequest.setTraderId(trader.getId());
        makeDepositingRequest.setReceivedCurrency("USD");
        makeDepositingRequest.setReceivedAmount(121333);
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(makeDepositingRequest)))
                .andExpect(status().isOk());
        double amountReduceCurrency = getBalanceByCurrency(trader.getId(), givenCurrency);

        mockMvc.perform(post(EXCHANGE_CURRENCY_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currencyExchangeRequest)))
                .andExpect(status().isOk());
        double amountReduceCurrencyAfter = getBalanceByCurrency(trader.getId(),givenCurrency);
        Assertions.assertTrue(amountReduceCurrency > amountReduceCurrencyAfter);
    }
    @Test
    public void currencyExchangeTest2() throws Exception{
        Trader trader = createTrader("awewveqq");
        String givenCurrency = "USD";
        String receivedCurrency = "RUB";
        double count = 1300;
        CurrencyExchangeRequest currencyExchangeRequest = new CurrencyExchangeRequest();
        currencyExchangeRequest.setTraderId(trader.getId());
        currencyExchangeRequest.setReceivedCurrency(receivedCurrency);
        currencyExchangeRequest.setGivenCurrency(givenCurrency);
        currencyExchangeRequest.setGivenAmount(count);

        MakeDepositingRequest makeDepositingRequest = new MakeDepositingRequest();
        makeDepositingRequest.setTraderId(trader.getId());
        makeDepositingRequest.setReceivedCurrency("USD");
        makeDepositingRequest.setReceivedAmount(1241.12);
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(makeDepositingRequest)))
                .andExpect(status().isOk());
        double amountAddCurrency = getBalanceByCurrency(trader.getId(),givenCurrency);
        mockMvc.perform(post(EXCHANGE_CURRENCY_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currencyExchangeRequest)))
                .andExpect(status().isOk());
        double amountAddCurrencyAfter = getBalanceByCurrency(trader.getId(),givenCurrency);
        Assertions.assertTrue(amountAddCurrency > amountAddCurrencyAfter);
    }
    public Trader createTrader (String name) {
        Trader trader = new Trader();
        trader.setName(name);
        trader.setPassword("passworin".toCharArray());
        trader.setId(traderService.createTrader(trader).getId());
        return trader;
    }
}
