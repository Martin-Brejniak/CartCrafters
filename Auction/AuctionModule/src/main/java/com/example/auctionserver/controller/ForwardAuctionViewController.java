package com.example.auctionserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardAuctionViewController {

    @GetMapping("/forward-auction")
    public String forwardAuctionPage() {
        return "forward-auction"; // Name of the JSP file
    }
}
