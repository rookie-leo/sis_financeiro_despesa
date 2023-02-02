package com.financeiro.despesa.http.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DespesaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGet200ForListar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/despesas"))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200));
    }

    @Test
    public void shouldGet200ForBuscarDespesa() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/despesas/{id}", 1))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(200)
                );
    }

    @Test
    public void shouldGet200ForAValidDespesa() throws Exception {
        String json = "{ \"descricao\":\"Despesa teste\", \"valor\":\"100.50\" }";

        mockMvc.perform((MockMvcRequestBuilders
                        .post("/despesas/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(201));
    }

    @Test
    public void shouldGet400ForAInvalidDespesa() throws Exception {
        String json = "{ \"descricao\":\"\", \"valor\":\"100.50\" }";

        mockMvc.perform((MockMvcRequestBuilders
                        .post("/despesas/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(400));
    }

}