package me.sangoh.demoinflearnrestapi.configs;

import me.sangoh.demoinflearnrestapi.accounts.Account;
import me.sangoh.demoinflearnrestapi.accounts.AccountRole;
import me.sangoh.demoinflearnrestapi.accounts.AccountService;
import me.sangoh.demoinflearnrestapi.common.BaseControlerTest;
import me.sangoh.demoinflearnrestapi.common.TestDescription;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthServerConfigTest extends BaseControlerTest {

    @Autowired
    AccountService accountService;

    @Test
    @TestDescription("인증 토큰을 발급 받는 테스트")
    public void getAuthToken() throws Exception {
        //Given
        String userName = "sangoh@email.com";
        String password = "sangoh";
        Account account = Account.builder()
                .email(userName)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();
        this.accountService.saveAccount(account);

        String clientId = "myApp";
        String clientSecret = "pass";

        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .param("username", userName)
                .param("password", password)
                .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists())
                .andExpect(jsonPath("refresh_token").exists())
                .andExpect(jsonPath("expires_in").isNumber())
                .andExpect(jsonPath("scope").value("read write"))
                .andExpect(jsonPath("token_type").value("base"))
        ;
    }

}