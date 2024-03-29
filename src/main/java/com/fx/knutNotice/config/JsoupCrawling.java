package com.fx.knutNotice.config;

import com.fx.knutNotice.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JsoupCrawling {

    public List<BoardDTO> crawlBoard(String board_url) throws IOException {
        log.info("실행중...");

        Document document = Jsoup.connect(board_url).get();
        Elements contents = document.select("tbody > tr");

        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (Element content : contents) {
            String nttId = content.select("input[type=hidden][name=nttId]").val();
            String boardNumber = content.select("td.problem_number").text();
            String title = content.select("td.left > div > div > form > input[type=submit]").val();


            //nttId가 empty인 경우 [공지]로 분류되어 있으므로 제외함
            if (!nttId.isEmpty()) {
                boardDTOList.add(BoardDTO.builder()
                        .nttId(Long.valueOf(nttId))
                        .boardNumber(Long.valueOf(boardNumber))
                        .title(title)
                        .build());
            }
        }
        return boardDTOList;
    }
}
