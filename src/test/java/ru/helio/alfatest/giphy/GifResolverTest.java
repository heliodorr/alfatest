package ru.helio.alfatest.giphy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest(
    classes = GifResolver.class
)
public class GifResolverTest {

    private final String EXPECTED = "expected";

    @MockBean
    private GiphyFeignClient fc;

    @Autowired
    private GifResolver gifResolver;


    @Test
    void if_rich(){

        when(fc.getGifUrl(anyString(), anyString(), anyInt(), anyInt())).
            thenReturn(EXPECTED);

        String url = gifResolver.getGifUrl(true);
        verify(fc, times(1))
            .getGifUrl(anyString(), eq("rich"), eq(1), anyInt());

        assertThat(url).isEqualTo(EXPECTED);

    }

    @Test
    void if_broke(){

        when(fc.getGifUrl(anyString(), anyString(), anyInt(), anyInt())).
            thenReturn(EXPECTED);

        String url = gifResolver.getGifUrl(false);
        verify(fc, times(1))
            .getGifUrl(anyString(), eq("broke"), eq(1), anyInt());

        assertThat(url).isEqualTo(EXPECTED);

    }



}
