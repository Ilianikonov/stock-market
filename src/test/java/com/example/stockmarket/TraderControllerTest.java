package com.example.stockmarket;

import com.example.stockmarket.controller.request.CreateTraderRequest;
import com.example.stockmarket.controller.request.UpdateTraderRequest;
import com.example.stockmarket.controller.response.TraderResponse;
import com.example.stockmarket.entity.Trader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    private static final String DELETE_TRADER_BY_ID_URL = "/trader/deleteTraderById/{Id}";
    private static final String GET_TRADER_BY_ID_URL = "/trader/getTraderById/{id}";

       @Test
    public void createTraderTest() throws Exception{
        CreateTraderRequest createTraderRequest = new CreateTraderRequest();
        createTraderRequest.setName("ilia134");
        createTraderRequest.setPassword("Nikonov1997!".toCharArray());
        String jsonTrader = mockMvc.perform(post(CREATE_TRADER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTraderRequest)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value("ilia134"))
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsString();
        TraderResponse traderResponse = objectMapper.readValue(jsonTrader,TraderResponse.class);
        Trader trader = getTraderById(traderResponse.getId());
           Assertions.assertEquals(traderResponse.getId(),trader.getId());
           Assertions.assertEquals(traderResponse.getName(),trader.getName());
    }

    @Test
    public void updateTraderTest() throws Exception{
           long id = createTrader("efewf","wefef").getId();
        UpdateTraderRequest updateTraderRequest = new UpdateTraderRequest();
        updateTraderRequest.setId(id);
        updateTraderRequest.setName("sgsgd");
        updateTraderRequest.setPassword("sdgdsg".toCharArray());
        mockMvc.perform(post(UP_DATE_TRADER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateTraderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("sgsgd"));
    }

    @Test
    public void deleteTraderByIdTest() throws Exception {
        Trader trader = createTrader("IliaNikonov","Nikonov1997!");
        mockMvc.perform(delete(DELETE_TRADER_BY_ID_URL,trader.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get(GET_TRADER_BY_ID_URL,trader.getId()))
                .andExpect(status().isNotFound());
    }

    private Trader getTraderById (long id) throws Exception {
        String response = mockMvc.perform(get(GET_TRADER_BY_ID_URL,id))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            return objectMapper.readValue(response, Trader.class);
    }

    private Trader createTrader(String name, String password) throws Exception {
           CreateTraderRequest createTraderRequest = new CreateTraderRequest();
        createTraderRequest.setName(name);
        createTraderRequest.setPassword(password.toCharArray());
        String jsonTrader = mockMvc.perform(post(CREATE_TRADER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTraderRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        TraderResponse traderResponse = objectMapper.readValue(jsonTrader, TraderResponse.class);
        return convertToTrader(traderResponse);
    }

    private Trader convertToTrader(TraderResponse traderResponse){
        Trader trader = new Trader();
        trader.setId(traderResponse.getId());
        trader.setName(traderResponse.getName());
        return trader;
    }
}
