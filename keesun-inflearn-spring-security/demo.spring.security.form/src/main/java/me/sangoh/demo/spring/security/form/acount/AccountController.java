package me.sangoh.demo.spring.security.form.acount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @GetMapping("/account/{role}/{username}/{password}")
    public Account createAccount(
            @PathVariable String role,
            @PathVariable String username,
            @PathVariable String password
    ) {
        return accountService.createNew(new Account(username, password, role));
    }

}
