package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.ApiContextInitializer;
import spring.services.TelegrammService;

import java.util.Arrays;
import java.util.List;

/**
 * create time 21.02.2018
 *
 * @author nponosov
 */
@ComponentScan("spring")
@SpringBootApplication(scanBasePackages={"com.websystique.springboot"})
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        String token = getArgsByName(args, "token");
        String botUserName = getArgsByName(args, "botUserName");
        context.getBean(TelegrammService.class).initTelegrammBot(token, botUserName);
    }

    private static String getArgsByName(String[] args, String argName){
        if (args != null) {
            List<String> argsList = Arrays.asList(args);
            for (String s : argsList) {
                if (s.indexOf("-" + argName + "=") == 0) return s.substring(argName.length() + 2, s.length());
            }
        }
        return "";
    }
}
