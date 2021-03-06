package thtroyer.minecraftserverstatuspage.controllers;

import thtroyer.minecraftserverstatuspage.models.config.ServerConfigList;
import thtroyer.minecraftserverstatuspage.models.ServerConfigStatus;
import thtroyer.minecraftserverstatuspage.services.MinecraftQueryService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class Status {

    private final MinecraftQueryService minecraftQueryService;

    private final ServerConfigList serverConfigList;

    private final Environment env;

    public Status(MinecraftQueryService minecraftQueryService, ServerConfigList serverConfigList, Environment env) {
        this.minecraftQueryService = minecraftQueryService;
        this.serverConfigList = serverConfigList;
        this.env = env;
    }

    @GetMapping("/")
    public String getStatus(Model model) {
        List<ServerConfigStatus> serverConfigStatusList = minecraftQueryService.getServerStatus(serverConfigList.serverList());

        model.addAttribute("servers", serverConfigStatusList);
        model.addAttribute("current_time", now());
        model.addAttribute("site_title", env.getProperty("site.title", "Minecraft Server Status Page"));

        return "status";
    }

    private String now() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd @ hh:mm a").format(LocalDateTime.now().atZone(ZoneId.systemDefault()));
    }
}
