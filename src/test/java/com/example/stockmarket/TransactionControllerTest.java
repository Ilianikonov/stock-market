package com.example.stockmarket;

import com.example.stockmarket.controller.response.GetBalanceResponse;
import com.example.stockmarket.entity.Trader;
import com.example.stockmarket.service.TraderService;
import com.example.stockmarket.service.TransactionService;
import com.example.stockmarket.service.currency.WebCurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private WebCurrencyService webCurrencyService;
    @Mock
    private WebCurrencyService mockWebCurrencyService;
    @Autowired
    private TraderService traderService;
    private static final String GET_CURRENCY_URL = "/balance/getBalanceByCurrency";
    private static final String MAKE_DEPOSITING_URL = "/balance/makeDepositing";
    private static final String WITHDRAW_CURRENCY_URL = "/balance/withdrawCurrency";
    private static final String EXCHANGE_CURRENCY_URL = "/balance/currencyExchange";
    private static final String GET_TOTAL_BALANCE_URL = "/balance/getTotalBalance";
    @BeforeEach
    public void setup(){
        ReflectionTestUtils.setField(transactionService, "webCurrencyService",mockWebCurrencyService);
    }
    @AfterEach
    public void cleanUp(){
        ReflectionTestUtils.setField(transactionService, "webCurrencyService", webCurrencyService);
    }
    @Test
    public void makeDepositing() throws Exception{
        Trader trader = createTrader("awefweeqq");
        double amount = 14.5;
        String currency = "RUB";
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .param("traderId", String.valueOf(trader.getId()))
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency))
                .andExpect(status().isOk());
        double amountBefore = getBalanceByCurrency(trader.getId(),currency);
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .param("traderId", String.valueOf(trader.getId()))
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency))
                .andExpect(status().isOk());
        double amountAfter = getBalanceByCurrency(trader.getId(),currency);
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
        Trader trader = createTrader("wefweqq");
        double amount = 14.5;
        String currency = "RUB";
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .param("traderId", String.valueOf(trader.getId()))
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency))
                .andExpect(status().isOk());
        double amountBefore = getBalanceByCurrency(trader.getId(),currency);
        mockMvc.perform(post(WITHDRAW_CURRENCY_URL)
                        .param("traderId", String.valueOf(trader.getId()))
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency))
                .andExpect(status().isOk());
        double amountAfter = getBalanceByCurrency(trader.getId(),currency);
        Assertions.assertEquals(amountBefore - amount,amountAfter);
    }
    @Test
    public void withdrawCurrency2() throws Exception{
        Trader trader = createTrader("aqeqfqq");
        double amount = 14.5;
        String currency = "FEG";
        mockMvc.perform(post(WITHDRAW_CURRENCY_URL)
                        .param("traderId", String.valueOf(trader.getId()))
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency))
                .andExpect(status().isNotFound());
    }
    @Test
    public void currencyExchangeTest() throws Exception{
        Trader trader = createTrader("argtneqq");
        String currencyTo = "USD";
        String currencyFrom = "RUB";
        double count = 100;
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .param("traderId", String.valueOf(trader.getId()))
                        .param("amount", String.valueOf(100000))
                        .param("currency", currencyFrom))
                .andExpect(status().isOk());
        double amountReduceCurrency = getBalanceByCurrency(trader.getId(), currencyFrom);
        System.out.println(amountReduceCurrency);
        Mockito.when(mockWebCurrencyService.convert(Mockito.eq(currencyFrom), Mockito.eq(currencyTo))).thenReturn(0.01);
        mockMvc.perform(post(EXCHANGE_CURRENCY_URL)
                        .param("traderId", String.valueOf(trader.getId()))
                        .param("count", String.valueOf(count))
                        .param("addCurrency", currencyTo)
                        .param("reduceCurrency", currencyFrom))
                .andExpect(status().isOk());
        double amountReduceCurrencyAfter = getBalanceByCurrency(trader.getId(),currencyFrom);
        Assertions.assertEquals(amountReduceCurrencyAfter,90000);
    }
    @Test
    public void currencyExchangeTest2() throws Exception{
        Trader trader = createTrader("awewveqq");
        String addCurrency = "USD";
        String reduceCurrency = "RUB";
        double count = 1241.12;
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .param("traderId", String.valueOf(trader.getId()))
                        .param("amount", String.valueOf(count))
                        .param("currency", reduceCurrency))
                .andExpect(status().isOk());
        double amountAddCurrency = getBalanceByCurrency(trader.getId(),reduceCurrency);
        mockMvc.perform(post(EXCHANGE_CURRENCY_URL)
                        .param("traderId", String.valueOf(trader.getId()))
                        .param("count", String.valueOf(13))
                        .param("addCurrency", addCurrency)
                        .param("reduceCurrency", reduceCurrency))
                .andExpect(status().isOk());
        double amountAddCurrencyAfter = getBalanceByCurrency(trader.getId(),reduceCurrency);
        Assertions.assertTrue(amountAddCurrency > amountAddCurrencyAfter);
    }
@Test
public void getTotalBalance() throws Exception{ // todo: дописать тест
    String currency = "RUB";
    Trader trader = createTrader("aefsweveqq");
    double count = 1244.12;
    mockMvc.perform(post(MAKE_DEPOSITING_URL)
                    .param("traderId", String.valueOf(trader.getId()))
                    .param("amount", String.valueOf(124400))
                    .param("currency", currency))
            .andExpect(status().isOk());
    mockMvc.perform(get(GET_TOTAL_BALANCE_URL)
                    .param("traderId", String.valueOf(trader.getId()))
                    .param("currency", currency))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.amount").value(124400));
    mockMvc.perform(post(MAKE_DEPOSITING_URL)
                    .param("traderId", String.valueOf(trader.getId()))
                    .param("amount", String.valueOf(1222))
                    .param("currency", "USD"))
            .andExpect(status().isOk());
    mockMvc.perform(get(GET_TOTAL_BALANCE_URL)
                    .param("traderId", String.valueOf(trader.getId()))
                    .param("currency", currency))
                    .andExpect(status().isOk());
}
    @Test
    public void getBalanceByCurrency() throws Exception{
        Trader trader = createTrader("aefsweeqq");
        double amount = 10.4;
        String currency = "RUB";
        mockMvc.perform(post(MAKE_DEPOSITING_URL)
                        .param("traderId", String.valueOf(trader.getId()))
                        .param("amount", String.valueOf(amount))
                        .param("currency", currency))
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
        trader.setPassword("passworin".toCharArray());
        trader.setId(traderService.createTrader(trader).getId());
        return trader;
    }
}
