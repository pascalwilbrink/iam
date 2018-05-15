package nl.wilbrink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import nl.wilbrink.config.ApplicationConfiguration;
import nl.wilbrink.config.Oauth2AuthorizationServerConfig;
import nl.wilbrink.config.ResourceServerConfig;
import nl.wilbrink.config.ServerSecurityConfig;

@SpringBootApplication
@Import({
    ApplicationConfiguration.class,
    Oauth2AuthorizationServerConfig.class,
    ResourceServerConfig.class,
    ServerSecurityConfig.class
})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
