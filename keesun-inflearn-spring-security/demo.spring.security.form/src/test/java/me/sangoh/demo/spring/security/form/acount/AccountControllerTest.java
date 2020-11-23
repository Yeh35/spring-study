package me.sangoh.demo.spring.security.form.acount;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @Test
    @WithAnonymousUser
    public void index_anonymous() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void index_user() throws Exception {
        mockMvc.perform(get("/").with(user("sangoh").roles("USER")))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithUser
    public void index_user_anotation() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void admin_user() throws Exception {
        mockMvc.perform(get("/admin").with(user("sangoh").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden())
        ;
    }

    @Test
    public void admin_admin() throws Exception {
        mockMvc.perform(get("/admin").with(user("sangoh").roles("ADMIN")))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @Transactional
    public void login() throws Exception {

        String username = "sangoh";
        String password = "123";

        accountService.createNew(new Account(
                username,
                password,
                "USER"
        ));

        mockMvc.perform(formLogin().user(username).password(password))
                .andExpect(authenticated())
            ;
    }

    @Test
    @Transactional
    public void login_fail() throws Exception {

        String username = "sangoh";
        String password = "123";

        accountService.createNew(new Account(
                username,
                password,
                "USER"
        ));

        mockMvc.perform(formLogin().user(username).password("dd"))
                .andExpect(unauthenticated())
        ;
    }

}