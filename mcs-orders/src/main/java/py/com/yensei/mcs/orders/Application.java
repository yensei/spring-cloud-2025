package py.com.yensei.mcs.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // <-- Habilita el Service Discovery
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
