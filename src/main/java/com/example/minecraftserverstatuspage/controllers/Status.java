package com.example.minecraftserverstatuspage.controllers;

import com.example.minecraftserverstatuspage.models.config.ServerConfigList;
import com.example.minecraftserverstatuspage.models.ServerConfigStatus;
import com.example.minecraftserverstatuspage.services.MinecraftQueryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        List<ServerConfigStatus> serverConfigStatusList = minecraftQueryService.getServerStatus(serverConfigList.serverList());

        model.addAttribute("servers", serverConfigStatusList);
        model.addAttribute("current_time", now());

        return "status";
    }

    private String now() {
        return new SimpleDateFormat("yyyy-MM-dd @ hh:mm a").format(new Date());
    }
}
