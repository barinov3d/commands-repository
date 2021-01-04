package org.barino3d;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class Demo {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Demo.class);
    }
}
