package ru.helio.alfatest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import ru.helio.alfatest.exchange.RichChecker;
import ru.helio.alfatest.giphy.GifResolver;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@Controller
public class MainController {

    @Autowired
    private RichChecker richChecker;

    @Autowired
    private GifResolver gifResolver;

    String t = new String("asd");

    @GetMapping("/{targetCurrency}")
    void test(@PathVariable String targetCurrency, HttpServletResponse response) throws IOException {

        boolean areWeRich = richChecker. areWeRich(targetCurrency);
        String gifUrl = gifResolver.getGifUrl(areWeRich);

        response.sendRedirect(gifUrl);

    }


}
