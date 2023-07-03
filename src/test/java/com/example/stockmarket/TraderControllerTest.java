package com.example.stockmarket;

import com.example.stockmarket.controller.TraderController;
import com.example.stockmarket.controller.request.CreateTraderRequest;
import com.example.stockmarket.controller.response.GetBalanceResponse;
import com.example.stockmarket.dao.DatabaseTraderRepository;
import com.example.stockmarket.dao.TraderRepository;
import com.example.stockmarket.dao.mapper.TraderMapper;
import com.example.stockmarket.entity.Trader;
import com.example.stockmarket.service.TraderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TraderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String CREATE_TRADER_URL = "/trader/createTrader";
    private static final String UP_DATE_TRADER_URL = "/trader/updateTrader";
    private static final String DELETE_TRADER_BY_ID_URL = "/trader/deleteTraderById";
    private static final String GET_TRADER_BY_ID_URL = "/trader/getTraderById/{id}";

       @Test
    public void createTraderTest() throws Exception{
        CreateTraderRequest createTraderRequest = new CreateTraderRequest();
        createTraderRequest.setName("Ilia");
        createTraderRequest.setPassword("Nikonov1997!".toCharArray());
        mockMvc.perform(post(CREATE_TRADER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTraderRequest)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value("Ilia"))
                        .andExpect(jsonPath("$.password").value("Nikonov1997!"));
    }
    @Test
    public void updateTraderTest() throws Exception{
        CreateTraderRequest createTraderRequest = new CreateTraderRequest();
        createTraderRequest.setId(10);
        createTraderRequest.setName("Ilia");
        createTraderRequest.setPassword("Nikonov1997!".toCharArray());
        mockMvc.perform(post(UP_DATE_TRADER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTraderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Ilia"))
                .andExpect(jsonPath("$.password").value("Nikonov1997!"));
    }
    @Test
    public void deleteTraderByIdTest() throws Exception {
        long id = 1;
        mockMvc.perform(delete(DELETE_TRADER_BY_ID_URL, id))
                .andExpect(status().isOk());
        Assertions.assertEquals(getTraderById(id),status().isAccepted());
    }
    private Trader getTraderById (long id) throws Exception {
        String response = mockMvc.perform(get(GET_TRADER_BY_ID_URL,id))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            return objectMapper.readValue(response, Trader.class);
    }
}
