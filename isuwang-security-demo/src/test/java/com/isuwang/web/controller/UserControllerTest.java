package com.isuwang.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.awt.*;

@RunWith(SpringRunner.class) //这个注解开启测试案例
@SpringBootTest
public class UserControllerTest  {
    @Autowired
    private WebApplicationContext wac;

     private MockMvc mockMvc;

     @Before
    public void setup() {
         mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
     }

     @Test
    public  void whenQuerySuccess() throws Exception {
        String result =  mockMvc.perform(MockMvcRequestBuilders.get("/user")
         .contentType(MediaType.APPLICATION_JSON_UTF8))
                 .andExpect(MockMvcResultMatchers.status().isOk()) //期待请求返回的状态码isOk
                 .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString(); //将返回结果以字符串形式返回
         System.out.println(result);

     }

     @Test
    public void whenGetInfoSuccess() throws Exception {
         String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
         .contentType(MediaType.APPLICATION_JSON_UTF8))
                 .andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("tom")) // 期待返回的json字符串里面有username=tom
                 .andReturn().getResponse().getContentAsString();
         System.out.println(result);
     }

    @Test
    public void whenGetInfoFail() throws Exception {
         mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
     }
}
