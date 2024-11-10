package store.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileLoaderTest {

    @Test
    @DisplayName("파일의 각 라인 읽기 검증")
    void 파일_라인_읽기_검증() {
        //given
        List<String> fileContents = new ArrayList<>();
        fileContents.add("탄산2+1,2,1,2024-01-01,2024-12-31");
        fileContents.add("MD추천상품,1,1,2024-01-01,2024-12-31");
        fileContents.add("반짝할인,1,1,2024-11-01,2024-11-30");

        //when
        List<String> fileLines;
        try {
            fileLines = FileLoader.fileReadLine("promotions.md");
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

        //then
        assertThat(fileLines).containsExactlyElementsOf(fileContents);
    }

    @Test
    @DisplayName("파일이 없으면 에러 메시지 검증")
    void 파일_없으면_에러_검증() throws IOException {
        //given
        String fileName = "bowook";

        //when & then
        List<String> fileLines;
        try {
            fileLines = FileLoader.fileReadLine(fileName);
        } catch (IllegalArgumentException e) {
            assertThat("[ERROR] 파일이 존재하지 않습니다.").isEqualTo(e.getMessage());
        }
    }
    
}
