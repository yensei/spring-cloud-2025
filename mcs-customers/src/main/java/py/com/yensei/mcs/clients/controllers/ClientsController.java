package py.com.yensei.mcs.clients.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientsController {

    @Value("${app.welcome-message:Default message2}")
    private String welcomeMessage;


    @GetMapping("/message")
    public String getMessage() {
        return this.welcomeMessage;
    }
}
