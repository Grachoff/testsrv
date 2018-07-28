package com.grachoffs.testservice;

import com.grachoffs.testservice.controllers.TransactionController;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerTest extends AbstractControllerDbTest {
    @Autowired
    TransactionController transactionController;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }
    @Test
    public void receiveData() throws Throwable {
        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"seller\":\"123534511\",\n" +
                        "\"customer\":\"648563524\",\n" +
                        "\"products\":[\n" +
                        "{\"name\":\"milk\",\"code\":\"2364175836135\"},\n" +
                        "{\"name\":\"coffe\",\"code\":\"4364758363546\"},\n" +
                        "{\"name\":\"melon\",\"code\":\"5364758363546\"},\n" +
                        "{\"name\":\"water\",\"code\":\"3656352437590\"}]\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result", Matchers.is("OK")));
    }

}
