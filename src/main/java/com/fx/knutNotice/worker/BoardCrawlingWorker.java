package com.fx.knutNotice.worker;


import com.fx.knutNotice.common.KnutURL;
import com.fx.knutNotice.dto.BoardDTO;
import com.fx.knutNotice.factory.KnutURLFactory;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class BoardCrawlingWorker {
    // 서버의 첫 실행 여부
    private static boolean boot = true;

    // 보드 유형 개수
    private static final byte boardLength = 4;

    // 첫 게시글의 제목을 담아둘 배열 변수
    private static String[] boardTitles = new String[boardLength];


    private final BoardUpdateWorker boardUpdateWorker;


    @Getter
    @Builder
    static class HtmlDocDTO {
        private String boardURL;
        private String articleURL;
        private byte boardType;
    }


    @Transactional
    @Scheduled(cron = "0 0 8-20 * * MON-FRI") //월요일부터 금요일까지 매 시간 정각마다 실행되지만 8시부터 20시까지만 실행
    public void scheduledBoardCrawl() throws IOException, FirebaseMessagingException {
        doCrawling();
        if (boot) {
            boot = false;
        }
    }

    /**
     * Crawling Method 통합.
     */
    private void doCrawling() throws FirebaseMessagingException {

        Collection<List<BoardDTO>> eachCrawledBoardPosts = Stream.of(
                        KnutURL.GENERAL_NEWS,
                        KnutURL.SCHOLARSHIP_NEWS,
                        KnutURL.EVENT_NEWS,
                        KnutURL.ACADEMIC_NEWS
                )
                .parallel()
                .map(KnutURL::type)
                .map(this::setBoardUrls)
                .map(boardUrls -> {
                    try {
                        return getBoardFromKNUT(boardUrls);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());



        if(eachCrawledBoardPosts.size() > 0) {
          deliverTitlesToWorker(eachCrawledBoardPosts);
        }

    }

    private List<BoardDTO> getBoardFromKNUT(List<Serializable> boardUrls) throws IOException{
        HtmlDocDTO htmlDocDTO = HtmlDocDTO.builder()
                .boardURL((String)boardUrls.get(0))
                .articleURL((String)boardUrls.get(1))
                .boardType((byte)boardUrls.get(2))
                .build();

        List<BoardDTO> result = searchDocumentation(getHtmlDocumentation(htmlDocDTO));
        return result;
    }
    
    private List<BoardDTO> searchDocumentation(List<Object> crawledData) throws IOException {
        boolean checkFirstBoardTitle = true;

        Elements contents = (Elements) crawledData.get(0);
        HtmlDocDTO htmlDocDTO = (HtmlDocDTO) crawledData.get(1);
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (Element content : contents) {
            String nttId = content.select("input[type=hidden][name=nttId]").val();
            // [공지]가 아닌 게시글이라면
            if(!nttId.isEmpty()) {
                // 게시글의 제목을 추출.
                String newTitle = content.select("td.left > div > div > form > input[type=submit]").val();


                // 서버가 처음 실행 중인 상태라면
                if(boot) {
                    // 첫번째 게시글의 제목을 boarTitles 저장.
                    if(checkFirstBoardTitle) {
                        boardTitles[htmlDocDTO.getBoardType()] = newTitle;
                        checkFirstBoardTitle = false;

                    }
                } else {
                    // 서버가 시작중인 상태가 아니고, 이미지가 없다면, 읽어올 필요가 없으므로 break
                    if(extractImageTag(content)) break;

                    if(checkFirstBoardTitle) {
                        // 'N' 이미지 태그가 있고, 기존 첫 게시글의 제목과 새로 가져온 게시글이 같다면 종료
                        if(boardTitles[htmlDocDTO.boardType].equals(newTitle)) {
                            log.info("[Crawling-Worker] 'N' 이미지 태그가 있지만, 기존 첫 게시글의 제목과 새로 가져온 게시물이 같다면 종료");
                            break;
                        }
                        // 'N' 이미지 태그가 있고, 기존 첫 게시글의 제목과 새로 가져온 게시글이 같지 않다면
                        boardTitles[htmlDocDTO.getBoardType()] = newTitle;
                        checkFirstBoardTitle = false;
                    }
                }
                // 1. 서버가 처음 실행 될 때
                // 2. 'N' 이미지 태그가 있고, 기존 첫 게시글의 제목과 새로 가져온 게시글이 같지 않다면
                // 3. 'N' 이미지 태그가 있고, 기존 첫 게시글의 제목과 새로 가져온 게시글이 같지 않을때, 하위 게시글을 전부 가지고 옴.
                getNewBoard(content, htmlDocDTO.getArticleURL(), nttId, boardDTOList, htmlDocDTO.getBoardType());
            }
        }
        return boardDTOList;
    }

    public List<Object> getHtmlDocumentation(final HtmlDocDTO htmlDocDTO) throws IOException{
        Document document = Jsoup.connect(htmlDocDTO.getBoardURL()).get();
        Elements contents = document.select("tbody > tr");
        return List.of(contents, htmlDocDTO);
    }


    /**
     * 이미지 태그를 추출합니다.
     */
    private boolean extractImageTag(Element content) {
        // 'N' 이미지 태그를 추출.
        Elements newImageTag = content.select("td.left > div > div > form > img");
        // 'N' 이미지 태그가 없다면 기존 게시글이며 크롤링 중지.
        if(newImageTag.isEmpty()) {
            log.info("[Crawling-Worker] 새로운 게시글이 존재하지 않음");
            return true;
        }
        return false;
    }





    private void getNewBoard(Element content, String articleURL, String nttId, List<BoardDTO> boardDTOList, byte boardType) throws IOException {

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
                .boardType(boardType)
                .build());

    }

    private List<Serializable> setBoardUrls(final byte type) {
        return KnutURLFactory.getBoardURL(type);
    }
    private void deliverTitlesToWorker(Collection<List<BoardDTO>> newPosts)
        throws FirebaseMessagingException {
        for (List<BoardDTO> eachBoards : newPosts) {
            if(!eachBoards.isEmpty()) {
                boardUpdateWorker.updateBoardTitles(eachBoards, eachBoards.get(0).getBoardType());
            }
        }
    }
}
