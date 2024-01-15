package com.sefaunal.resumebuilder.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author github.com/sefaunal
 * @since 2024-01-14
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/resume")
    public String resumePage() {
        return "ResumePage";
    }
}