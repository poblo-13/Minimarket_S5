package com.minimarket.controller;

import com.minimarket.security.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AutenticacionUsuarioTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testLogin_CredencialesInvalidas_DebeSerRechazado() throws Exception {
        mockMvc.perform(formLogin().user("hacker").password("claveFalsa123"))
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error")); 
    }

    @Test
    public void testLogin_CredencialesValidas_DebeSerAceptado() throws Exception {
        UserDetails mockUser = User.withUsername("admin")
                .password(passwordEncoder.encode("claveSegura123"))
                .roles("ADMIN")
                .build();
                
        when(customUserDetailsService.loadUserByUsername("admin")).thenReturn(mockUser);

        mockMvc.perform(formLogin().user("admin").password("claveSegura123"))
                .andExpect(authenticated());
    }
}