package com.fx.knutNotice.worker;


import com.fx.knutNotice.common.KnutURL;
import com.fx.knutNotice.dto.BoardDTO;
import com.fx.knutNotice.factory.KnutURLFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class BoardCrawlingWorker {
    private static final byte boardLength = 4;
    private final BoardUpdateWorker boardUpdateWorker;


    @Transactional
    @Scheduled(cron = "0 0 8-20 * * MON-FRI") //월요일부터 금요일까지 매 시간 정각마다 실행되지만 8시부터 20시까지만 실행
    public void crawlBoard() throws IOException {
        deliverTitlesToWorker(List.of(getBoardFromKNUT(setBoardUrls(KnutURL.ACADEMIC_NEWS.type())),
                                    getBoardFromKNUT(setBoardUrls(KnutURL.GENERAL_NEWS.type())),
                                    getBoardFromKNUT(setBoardUrls(KnutURL.EVENT_NEWS.type())),
                                    getBoardFromKNUT(setBoardUrls(KnutURL.SCHOLARSHIP_NEWS.type()))));
    }

    private List<BoardDTO> getBoardFromKNUT(List<String> boardUrls) throws IOException {
        log.info("실행중...");

        String boardURL = boardUrls.get(0);
        String articleURL = boardUrls.get(1);
        System.out.println("??? " + boardURL + "\n" + "???" + articleURL);

        Document document = Jsoup.connect(boardURL).get();
        Elements contents = document.select("tbody > tr");

        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (Element content : contents) {
            String nttId = content.select("input[type=hidden][name=nttId]").val();

            //nttId가 empty인 경우 [공지]로 분류되어 있으므로 제외함
            if (!nttId.isEmpty()) {
                String boardNumber = content.select("td.problem_number").text();
                String title = content.select("td.left > div > div > form > input[type=submit]").val();
                String departName = content.select("td.problem_name").text();
                String registrationDate = content.select("td.date").text();


                //nttId에 대한 내용 크롤링
                //nttId에 해당하는 게시글 URL
                String contentURL = articleURL + "&nttId=" + nttId + "&bbsTyCode=&bbsAttrbCode=&mno=sitemap_12&kind=&pageIndex=1";
                Document articleDocument = Jsoup.connect(contentURL).get();
                Elements articleContent = articleDocument.select("div.bbs_detail_content");
                String detailContent = articleContent.html();
                String contentImage = articleContent.select("div.bbs_detail_content img").attr("src"); //이미지 추출

                // contentImage가 빈 문자열이면 null로 설정
                if (contentImage.isEmpty()) {
                    contentImage = null;
                }

                boardDTOList.add(BoardDTO.builder()
                        .nttId(Long.valueOf(nttId))
                        .boardNumber(Long.valueOf(boardNumber))
                        .title(title)
                        .contentURL(contentURL)
                        .content(detailContent)
                        .contentImage(contentImage)
                        .departName(departName)
                        .registrationDate(registrationDate)
                        .build());
            }
        }
        return boardDTOList;
    }

    private List<String> setBoardUrls(final byte type) {
        return KnutURLFactory.getBoardURL(type);
    }
    private void deliverTitlesToWorker(List<List<BoardDTO>> newsTitles) {
        for (byte i = 0; i < boardLength; i++) {
            byte boardServiceType = i;
            boardUpdateWorker.updateBoardTitles(newsTitles.get(i), boardServiceType);
        }
    }
}
