package com.team.ghana.department;

import com.team.ghana.MainApplication;
import com.team.ghana.businessUnit.BusinessUnitJson;
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
public class DepartmentFeature {

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void Departments() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/departments")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isOk())
                    .andExpect(content().json(DepartmentJson.json));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void DepartmentsById() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/departments/{departmentId}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                        .andExpect(status().isOk())
                        .andExpect(content().json(DepartmentJson.json));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
