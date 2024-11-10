package store.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    @DisplayName("정수 변환 검증")
    void 정수_변환_검증() {
        //given
        String beforeConverted = "1000";

        //when
        int afterConverted = Parser.parse(beforeConverted);

        //then
        assertThat(afterConverted).isEqualTo(1000);
    }
}
