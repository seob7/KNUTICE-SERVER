package com.fx.knutNotice.web.form.ResponseForm;

import com.fx.knutNotice.dto.NewsListDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MainForm {

    private List<NewsListDTO> generalNewsTopThreeTitle;
    private List<NewsListDTO> scholarshipNewsTopThreeTitle;
    private List<NewsListDTO> eventNewsTopThreeTitle;
    private List<NewsListDTO> academicNewsTopThreeTitle;

}
