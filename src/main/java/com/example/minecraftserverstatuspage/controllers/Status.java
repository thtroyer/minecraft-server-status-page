package com.example.minecraftserverstatuspage.controllers;

import com.example.minecraftserverstatuspage.services.MinecraftQueryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Status {

    private final MinecraftQueryService minecraftQueryService;

    public Status(MinecraftQueryService minecraftQueryService) {
        this.minecraftQueryService = minecraftQueryService;
    }

    @GetMapping("/")
    public String getStatus() {
        System.out.println(minecraftQueryService.getServerStatus("...");
        return "status";
    }
}
