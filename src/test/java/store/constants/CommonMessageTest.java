package store.constants;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.constant.CommonMessage;

public class CommonMessageTest {
    @Test
    @DisplayName("Y 문자 반환 검증")
    void 문자열_Y_반환_검증() {
        //when
        String yes = CommonMessage.YES.getCommonMessage();

        //then
        assertThat(yes).isEqualTo("Y");
    }

    @Test
    @DisplayName("N 문자 반환 검증")
    void 문자열_N_반환_검증() {
        //when
        String no = CommonMessage.NO.getCommonMessage();

        //then
        assertThat(no).isEqualTo("N");
    }

}
