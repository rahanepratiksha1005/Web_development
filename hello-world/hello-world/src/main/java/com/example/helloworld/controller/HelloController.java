package com.example.helloworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class HelloController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Hello World Welcome to Spring Boot");
        return "index";
    }

    @GetMapping("/api/features")
    @ResponseBody
    public List<Feature> getFeatures() {
        return Arrays.asList(
            new Feature("Faculty Module", "Manage professors and courses", "👨‍🏫"),
            new Feature("Student Portal", "View results and attendance", "🎓"),
            new Feature("Admin Panel", "System settings and control", "⚙️")
        );
    }

    static class Feature {
        public String title;
        public String description;
        public String icon;

        public Feature(String title, String description, String icon) {
            this.title = title;
            this.description = description;
            this.icon = icon;
        }
    }
}