package ru.helio.alfatest.exchange;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(
    classes = RichChecker.class
)
class RichCheckerTest {

    @MockBean
    private ExchangeFeignClient fc;
    @Autowired
    private RichChecker richChecker;

    @Value("${exchange.base_currency}")
    private String BASE_CURRENCY;

    private final String TARGET_CURRENCY  = "RUB";

    @Test
    void yesterdays_rate_is_bigger() {

        when(fc.getRatio(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(10.0)
                .thenReturn(20.0);

        assertThat(richChecker.areWeRich(TARGET_CURRENCY))
                .isTrue();

    }

    @Test
    void todays_rate_is_bigger() {

        when(fc.getRatio(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(20.0)
                .thenReturn(10.0);

        assertThat(richChecker.areWeRich(TARGET_CURRENCY))
                .isFalse();

    }

    @Test
    void correct_params_and_invocations(){

        richChecker.areWeRich(TARGET_CURRENCY);

        verify(fc, times(2))
                .getRatio(matches("\\d{4}-\\d{2}-\\d{2}"), anyString(),
                    eq(BASE_CURRENCY), eq(TARGET_CURRENCY));

    }


}
