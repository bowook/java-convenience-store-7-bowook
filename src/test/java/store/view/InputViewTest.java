package store.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.exception.ConvenienceStoreException;
import store.exception.ErrorMessage;
import store.view.input.InputView;

public class InputViewTest {

    private InputView inputView;

    @BeforeEach
    void setUp() {
        inputView = new InputView();
    }

    @AfterEach
    void closeConsole() {
        inputView.closeConsole();
    }

    @Test
    @DisplayName("아이템 입력 정상")
    void 아이템_입력_정상() {
        //given
        String input = "[사이다-2],[감자칩-1]";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //when
        List<String> result = inputView.readItems();

        //then
        assertThat(result).containsExactly("사이다-2", "감자칩-1");
    }

    @Test
    @DisplayName("이름과 수량이 (-)로 연결되어있는지 검증")
    void 이름_수량_사이_기호_검증() {
        //given
        String input = "[사이다.2]";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //when
        try {
            inputView.readItems();
        } catch (ConvenienceStoreException e) {
            assertThat(e.getMessage()).isEqualTo(ErrorMessage.INCORRECT_FORMAT.getErrorMessage());
        }
    }

    @Test
    @DisplayName("이름과 수량이 (-)로 연결되어 있는데 이름이 비어있을 경우 검증")
    void 이름_비어있는_아이템_검증() {
        //given
        String input = "[-2]";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //when
        try {
            inputView.readItems();
        } catch (ConvenienceStoreException e) {
            assertThat(e.getMessage()).isEqualTo(ErrorMessage.INCORRECT_FORMAT.getErrorMessage());
        }
    }

    @Test
    @DisplayName("이름과 수량이 (-)로 연결되어 있는데 이름이 공백인 경우 검증")
    void 이름_공백_문자_아이템_검증() {
        //given
        String input = "[ -2]";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //when
        try {
            inputView.readItems();
        } catch (ConvenienceStoreException e) {
            assertThat(e.getMessage()).isEqualTo(ErrorMessage.INCORRECT_FORMAT.getErrorMessage());
        }
    }

    @Test
    @DisplayName("각 아이템이 쉼표(,)로 연결되어있는지 검증")
    void 아이템_사이_쉼표_기호_검증() {
        //given
        String input = "[사이다-2]|[콜라-10]";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //when
        try {
            inputView.readItems();
        } catch (ConvenienceStoreException e) {
            assertThat(e.getMessage()).isEqualTo(ErrorMessage.INCORRECT_FORMAT.getErrorMessage());
        }
    }

    @Test
    @DisplayName("대괄호 입력 검증")
    void 대괄호_입력_검증() {
        //given
        String input = "[사이다]-2";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //when & then
        try {
            inputView.readItems();
        } catch (ConvenienceStoreException e) {
            assertThat(e.getMessage()).isEqualTo(ErrorMessage.INCORRECT_FORMAT.getErrorMessage());
        }
    }

    @Test
    @DisplayName("아이템 입력 포멧 검증")
    void 아이템_입력_포멧_검증() {
        //given
        String input = "[사이다-2,[감자칩-1]";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //when & then
        try {
            inputView.readItems();
        } catch (ConvenienceStoreException e) {
            assertThat(e.getMessage()).isEqualTo(ErrorMessage.INCORRECT_FORMAT.getErrorMessage());
        }
    }

    @Test
    @DisplayName("아이템 수량 형식 검증")
    void 아이템_수량_형식_검증() {
        //given
        String input = "[사이다-두개],[감자칩-1]";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //when & then
        try {
            inputView.readItems();
        } catch (ConvenienceStoreException e) {
            assertThat(e.getMessage()).isEqualTo(ErrorMessage.INCORRECT_FORMAT.getErrorMessage());
        }
    }

    @Test
    @DisplayName("잘못된 사용자 응답 검증")
    void 잘못된_사용자_응답_검증() {
        //given
        String input = "잘못된입력";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //when & then
        try {
            inputView.readNoDiscountAnswer("사이다", 2);
        } catch (ConvenienceStoreException e) {
            assertThat(e.getMessage()).isEqualTo(ErrorMessage.WRONG_ANSWER.getErrorMessage());
        }
    }

    @Test
    @DisplayName("프로모션 적용 여부 확인에서 잘못된 응답 검증")
    void 프로모션_응답_잘못된_응답_검증() {
        //given
        String input = "잘못된입력";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //when & then
        try {
            inputView.readOneMoreFree("감자칩");
        } catch (ConvenienceStoreException e) {
            assertThat(e.getMessage()).isEqualTo(ErrorMessage.WRONG_ANSWER.getErrorMessage());
        }
    }

    @Test
    @DisplayName("멤버쉽 회원 여부 확인에서 잘못된 응답 검증")
    void 멤버쉽_회원_여부_응답_잘못된_응답_검증() {
        //given
        String input = "잘못된입력";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //when & then
        try {
            inputView.readMembership();
        } catch (ConvenienceStoreException e) {
            assertThat(e.getMessage()).isEqualTo(ErrorMessage.WRONG_ANSWER.getErrorMessage());
        }
    }

    @Test
    @DisplayName("재시도 여부 확인에서 잘못된 응답 검증")
    void 재시도_응답_잘못된_응답_검증() {
        //given
        String input = "잘못된입력";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //when & then
        try {
            inputView.readRetry();
        } catch (ConvenienceStoreException e) {
            assertThat(e.getMessage()).isEqualTo(ErrorMessage.WRONG_ANSWER.getErrorMessage());
        }
    }
}
