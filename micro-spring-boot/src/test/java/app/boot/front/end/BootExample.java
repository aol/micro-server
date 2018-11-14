package app.boot.front.end;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BootExample extends SpringBootServletInitializer {

    public static void  main(String[] args){
        SpringApplication.run(BootExample.class, args);
   /**     new BootExample()
            .configure(new SpringApplicationBuilder(BootExample.class))
            .run(args);
    **/

    }
}
