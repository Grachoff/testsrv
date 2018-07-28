package com.grachoffs.testservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grachoffs.testservice.ServiceApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static common.Constants.TEST_PROFILE;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
@ActiveProfiles(TEST_PROFILE)
@WebAppConfiguration
public abstract class AbstractControllerDbTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    protected ObjectMapper jsonMapper = new ObjectMapper();
}
