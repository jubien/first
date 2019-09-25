package fr.smacl.gcl.git.GitManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages={"fr.smacl"
		})
//@PropertySources({
//	@PropertySource("classpath:application-conf.properties"),
//	@PropertySource("classpath:application-env.properties"),
//	@PropertySource("classpath:application-secrets.properties")
//})
@Slf4j
public class App {
	
    public static void main(String[] args) {
    	SpringApplication.run(App.class, args);
    }


}