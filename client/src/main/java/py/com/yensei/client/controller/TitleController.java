package py.com.yensei.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TitleController {

    @Value("${title:Default Title}")
    private String title;


    @GetMapping("/title")
    public String getTitle() {
        return this.title;
    }
}
