package com.hicc.tutorking.controller;


import com.hicc.tutorking.dto.AccountFormDto;
import com.hicc.tutorking.entity.Account;
import com.hicc.tutorking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/auth")
@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value="/signup")
    public String accountForm(Model model){
        model.addAttribute("accountFormDto",new AccountFormDto());
        return "signup/signup";//이부분 수정해야 함 경로 수정 필요함
    }

    @PostMapping(value="/signup")
    public String newAccount(@Valid AccountFormDto accountFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "signup/signup";
        }

        try {
            Account account = Account.createAccount(accountFormDto, passwordEncoder);
            accountService.saveAccount(account);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "signup/signup";
        }

        return "redirect:/";
    }


}