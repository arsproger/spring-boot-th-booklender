package com.arsen.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bob")
public class Boba {
    @GetMapping
    @ResponseBody
    public String getName() {
        return "<h1>MyNameIsBob :)</h1>";
    }

    // Ну тут нечего сказать

}
