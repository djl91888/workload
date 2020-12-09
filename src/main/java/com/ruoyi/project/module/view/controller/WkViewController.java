package com.ruoyi.project.module.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class WkViewController {

    @GetMapping("/editor")
    public String editor() {
        return "workload/editor";
    }

    @GetMapping("/newDemand")
    public String newDemand() {
        return "workload/newDemand";
    }

}
