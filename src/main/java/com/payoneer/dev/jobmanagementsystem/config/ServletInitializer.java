package com.payoneer.dev.jobmanagementsystem.config;

import com.payoneer.dev.jobmanagementsystem.JobManagementSystemApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
// new defualt config
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JobManagementSystemApplication.class);
    }

}
