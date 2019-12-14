package com.team.ghana.businessUnit;

import com.team.ghana.MainApplication;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MainApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BusinessUnitFeature {

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void BusinessUnits() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/businessUnits")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andExpect(content().json(BusinessUnitJson.json));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void BusinessUnitsById() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/businessUnits/{businessUnitId}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                        .andExpect(status().isOk())
                        .andExpect(content().json(BusinessUnitJson.json));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
