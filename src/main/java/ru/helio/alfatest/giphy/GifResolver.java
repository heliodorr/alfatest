package ru.helio.alfatest.giphy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GifResolver {

    private final int LIMIT_OF_RESPONSE = 1;
    private final int OFFSET_BOUND = 50;


    private final GiphyFeignClient client;
    private final String token;

    private final Random randomGen = new Random(System.currentTimeMillis());

    public GifResolver(
            @Autowired GiphyFeignClient client,
            @Value("${giphy.token}") String token
    ){
        this.client = client;
        this.token = token;
    }

    public String getGifUrl(boolean areWeRich) {
        String query = areWeRich ? "rich" : "broke";

        int offset = randomGen.nextInt(OFFSET_BOUND);
        return client.getGifUrl(token, query, LIMIT_OF_RESPONSE, offset);
    }


}