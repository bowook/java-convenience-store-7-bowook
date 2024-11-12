package store.constants;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.constant.CommonValue;

public class CommonValueTest {

    @Test
    @DisplayName("숫자 0 반환 검증")
    void 숫자_0_반환_검증() {
        //when
        int zero = CommonValue.ZERO.getValue();

        //then
        assertThat(zero).isEqualTo(0);
    }

    @Test
    @DisplayName("숫자 1 반환 검증")
    void 숫자_1_반환_검증() {
        //when
        int one = CommonValue.ONE.getValue();

        //then
        assertThat(one).isEqualTo(1);
    }

    @Test
    @DisplayName("숫자 2 반환 검증")
    void 숫자_2_반환_검증() {
        //when
        int two = CommonValue.TWO.getValue();

        //then
        assertThat(two).isEqualTo(2);
    }

    @Test
    @DisplayName("숫자 8000 반환 검증")
    void 숫자_8000_반환_검증() {
        //when
        int eightThousand = CommonValue.EIGHT_THOUSAND.getValue();

        //then
        assertThat(eightThousand).isEqualTo(8000);
    }

}
