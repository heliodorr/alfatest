package ru.helio.alfatest.exchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class RichChecker {


    private final ExchangeFeignClient client;
    private final String baseCurrency;
    private final String token;

    public RichChecker(
            @Autowired ExchangeFeignClient client,
            @Value("${exchange.base_currency}") String baseCurrency,
            @Value("${exchange.token}") String token
    ){
        this.client = client;
        this.baseCurrency = baseCurrency;
        this.token = token;
    }

    public boolean areWeRich(String targetCurrency) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();

        String today = currentDate
                .format(formatter);

        String yestd = currentDate
                .minusDays(1)
                .format(formatter);

        double todaysRate = client.getRatio(today, token, baseCurrency, targetCurrency);

        double yestdRate = client.getRatio(yestd, token, baseCurrency, targetCurrency);


        return todaysRate < yestdRate;
    }





}