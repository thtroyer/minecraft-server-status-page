package com.example.minecraftserverstatuspage.controllers;

import com.example.minecraftserverstatuspage.models.ServerConfigList;
import com.example.minecraftserverstatuspage.models.ServerConfigStatus;
import com.example.minecraftserverstatuspage.services.MinecraftQueryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Status {

    private final MinecraftQueryService minecraftQueryService;

    private final ServerConfigList serverConfigList;

    public Status(MinecraftQueryService minecraftQueryService, ServerConfigList serverConfigList) {
        this.minecraftQueryService = minecraftQueryService;
        this.serverConfigList = serverConfigList;
    }

    @GetMapping("/")
    public String getStatus(Model model) {
        List<ServerConfigStatus> serverConfigStatusList = new ArrayList<>();

        for (var server : serverConfigList.serverList()) {
            serverConfigStatusList.add(minecraftQueryService.getServerStatus(server));
        }

        System.out.println(serverConfigStatusList);
        model.addAttribute("servers", serverConfigStatusList);

        return "status";
    }
}
