package app.spring.plugin;


import com.oath.micro.server.application.registry.Job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistryController {

    private final Job job;

    @Autowired
    public RegistryController(Job job){
        this.job = job;
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot with Microserver!";
    }

    @RequestMapping("/scheduled")
    public String scheduled() {
        return ""+job.getScheduled();
    }

}
