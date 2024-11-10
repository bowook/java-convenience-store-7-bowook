package store.constants;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.constant.SignMessage;

public class SignMessageTest {

    @Test
    @DisplayName("쉼표(,) 반환 테스트")
    void 쉼표_반환_테스트() {
        //when
        String comma = SignMessage.COMMA.getSign();

        //then
        assertThat(comma).isEqualTo(",");
    }

    @Test
    @DisplayName("왼쪽 대괄호([) 반환 테스트")
    void 왼쪽_대괄호_반환_테스트() {
        //when
        String leftSquareBracket = SignMessage.LEFT_SQUARE_BRACKET.getSign();

        //then
        assertThat(leftSquareBracket).isEqualTo("[");
    }

    @Test
    @DisplayName("오른쪽 대괄호(]) 반환 테스트")
    void 오른쪽_대괄호_반환_테스트() {
        //when
        String rightSquareBracket = SignMessage.RIGHT_SQUARE_BRACKET.getSign();

        //then
        assertThat(rightSquareBracket).isEqualTo("]");
    }

    @Test
    @DisplayName("붙임(-) 반환 테스트")
    void 붙임_반환_테스트() {
        //when
        String hyphen = SignMessage.HYPHEN.getSign();

        //then
        assertThat(hyphen).isEqualTo("-");
    }
}
