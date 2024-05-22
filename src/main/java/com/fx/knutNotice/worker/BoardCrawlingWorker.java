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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class BoardCrawlingWorker {
    private static final byte boardLength = 4;
    // 첫 게시글의 제목을 담아둘 배열 변수
    private static String[] boardTitles = new String[boardLength];
    // 서버의 첫 실행 여부
    private static boolean boot = false;
    private final BoardUpdateWorker boardUpdateWorker;



    @Transactional
//    @Scheduled(cron = "0 0 8-20 * * MON-FRI") //월요일부터 금요일까지 매 시간 정각마다 실행되지만 8시부터 20시까지만 실행
    @Scheduled(fixedDelay = 1000 * 30)
    public void crawlBoard() throws IOException {
        if(boot == false) {
            log.info("[Server] 처음 서버 크롤링 시작");
            deliverTitlesToWorker(List.of(getBoardFromKNUT(setBoardUrls(KnutURL.ACADEMIC_NEWS.type())),
                    getBoardFromKNUT(setBoardUrls(KnutURL.GENERAL_NEWS.type())),
                    getBoardFromKNUT(setBoardUrls(KnutURL.EVENT_NEWS.type())),
                    getBoardFromKNUT(setBoardUrls(KnutURL.SCHOLARSHIP_NEWS.type()))));
            log.info("[Server] 처음 서버 크롤링 종료");
            boot = true;

        } else {
            log.info("[Server] 서버 크롤링 시작");
            deliverTitlesToWorker(List.of(getBoardFromKNUT(setBoardUrls(KnutURL.ACADEMIC_NEWS.type())),
                    getBoardFromKNUT(setBoardUrls(KnutURL.GENERAL_NEWS.type())),
                    getBoardFromKNUT(setBoardUrls(KnutURL.EVENT_NEWS.type())),
                    getBoardFromKNUT(setBoardUrls(KnutURL.SCHOLARSHIP_NEWS.type()))));
            log.info("[Server] 서버 크롤링 종료");
        }
    }




    private List<BoardDTO> getBoardFromKNUT(List<Serializable> boardUrls) throws IOException {
        // init URL & data
        String boardURL = (String)boardUrls.get(0);
        String articleURL = (String)boardUrls.get(1);
        byte boardType = (byte)boardUrls.get(2);
        List<BoardDTO> boardDTOList = new ArrayList<>();


        // get HTML document
        Document document = Jsoup.connect(boardURL).get();
        Elements contents = document.select("tbody > tr");

        // start crawling
        boolean checkFirstBoardTitle = true;
        if(boot == false) {
            crawlAtServerIsFirst(contents, checkFirstBoardTitle, boardType, articleURL, boardDTOList);
        } else {
            crawlAtServerIsNotFirst(contents, checkFirstBoardTitle, boardType, articleURL, boardDTOList);
        }
        return boardDTOList;
    }

    /**
     * 서버가 처음 실행될 때, 실행되는 크롤링
     */
    /**
     * 크롤링한 각 보드 유형의 '첫 번째' 게시글의 제목을 boardTitles 배열에 저장합니다.
     * 첫 번째 게시글을 포함해서 하위 10개의 게시글을 모두 업데이트합니다.
     *
     */

    private void crawlAtServerIsFirst(Elements contents, boolean checkFirstBoardTitle, byte boardType, String articleURL, List<BoardDTO> boardDTOList) throws IOException {
        for (Element content : contents) {
            String nttId = content.select("input[type=hidden][name=nttId]").val();
            // [공지]가 아닌 게시글이라면
            if(!nttId.isEmpty()) {
                checkFirstBoardTitle = doCrawlAtFirst(content, checkFirstBoardTitle, boardType, articleURL, nttId, boardDTOList);
            }
        }
    }

    /**
     * 서버가 처음 실행된게 아닐 때, 실행되는 크롤링
     */
    /**
     * 이미지 태그를 기준으로 새로운 게시글을 판단합니다.
     */

    private void crawlAtServerIsNotFirst(Elements contents, boolean checkFirstBoardTitle, byte boardType, String articleURL, List<BoardDTO> boardDTOList) throws IOException {
        for (Element content : contents) {
            String nttId = content.select("input[type=hidden][name=nttId]").val();
            // [공지]가 아닌 게시글이라면
            if(!nttId.isEmpty()) {
                if (extractImageTag(content)) break;
                // 게시글의 제목을 추출.
                String newTitle = content.select("td.left > div > div > form > input[type=submit]").val();
                // 'N' 이미지 태그가 있고, 처음 게시물을 검사한다면.
                if(checkFirstBoardTitle) {
                    // 'N' 이미지 태그가 있고, 기존 첫 게시글의 제목과 새로 가져온 게시글이 같다면 종료
                    if(boardTitles[boardType].equals(newTitle)) {
                        log.info("[Crawling-Worker] 'N' 이미지 태그가 있지만, 기존 첫 게시글의 제목과 새로 가져온 게시물이 같다면 종료");
                        break;
                    }
                    checkFirstBoardTitle = doCrawlAtSecond(content, boardType, newTitle, articleURL, nttId, boardDTOList, checkFirstBoardTitle);

                } else {
                    // 'N' 이미지 태그가 있고, 처음 게시물을 검사하는게 아니라면
                    // 하위 모든 게시글을 전부 추출.
                    // 기존에 10개를 가져오고 10개를 삭제하는 것이므로, 새로운 게시글을 포함해서 하위 10개를 가지고옴.
                    getNewBoard(content, articleURL, nttId, boardDTOList);
                }

            }
        }
    }

    private boolean doCrawlAtFirst(Element content, boolean checkFirstBoardTitle, byte boardType, String articleURL, String nttId, List<BoardDTO> boardDTOList) throws IOException {
        // 게시글의 제목을 추출.
        String newTitle = content.select("td.left > div > div > form > input[type=submit]").val();
        // 첫번째 게시글의 제목을 boarTitles 저장.
        if(checkFirstBoardTitle) {
            boardTitles[boardType] = newTitle;
        }
        // 첫번째 게시글을 포함해서, 하위 10개의 데이터를 전부 데이터 베이스에 업데이트.
        getNewBoard(content, articleURL, nttId, boardDTOList);
        return false;
    }

    private boolean doCrawlAtSecond(Element content, byte boardType, String newTitle, String articleURL, String nttId, List<BoardDTO> boardDTOList, boolean checkFirstBoardTitle) throws IOException {
        // 'N' 이미지 태그가 있고, 처음 게시물의 '제목'이 기존에 저장했던 처음 게시물의 '제목'과 다르다면,
        // 이전 게시물을 처음 게시물로 교체.
        // 처음 게시물을 업데이트에 포함.
        if (!boardTitles[boardType].equals(newTitle)) {
            boardTitles[boardType] = newTitle;
            getNewBoard(content, articleURL, nttId, boardDTOList);
            checkFirstBoardTitle = false;
        }
        return checkFirstBoardTitle;
    }

    /**
     * 이미지 태그를 추출합니다.
     */
    private static boolean extractImageTag(Element content) {
        // 'N' 이미지 태그를 추출.
        Elements newImageTag = content.select("td.left > div > div > form > img");
        // 'N' 이미지 태그가 없다면 기존 게시글이며 크롤링 중지.
        if(newImageTag.isEmpty()) {
            log.info("[Crawling-Worker] 새로운 게시글이 존재하지 않음");
            return true;
        }
        return false;
    }





    private void getNewBoard(Element content, String articleURL, String nttId, List<BoardDTO> boardDTOList) throws IOException {
        String title = content.select("td.left > div > div > form > input[type=submit]").val();
        String boardNumber = content.select("td.problem_number").text();

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

    private List<Serializable> setBoardUrls(final byte type) {
        return KnutURLFactory.getBoardURL(type);
    }
    private void deliverTitlesToWorker(List<List<BoardDTO>> newsTitles) {
        for (byte i = 0; i < boardLength; i++) {
            byte boardServiceType = i;
            log.info("게시글 타입 : " + boardServiceType + ", " + "\n" + "게시글이 비어있는지 여부 : " + newsTitles.get(i).isEmpty());
            // 각 게시글이 비어있지 않다면 게시글을 updateWorker 에서 처리.
            if(!newsTitles.get(i).isEmpty())
                boardUpdateWorker.updateBoardTitles(newsTitles.get(i), boardServiceType);
        }
    }
}
