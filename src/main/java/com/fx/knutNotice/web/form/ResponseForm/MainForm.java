package com.fx.knutNotice.web.form.ResponseForm;

import com.fx.knutNotice.dto.NewsListDTO;
import com.fx.knutNotice.dto.NewsMainDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MainForm {

    private List<NewsMainDTO> generalNewsTopThreeTitle;
    private List<NewsMainDTO> scholarshipNewsTopThreeTitle;
    private List<NewsMainDTO> eventNewsTopThreeTitle;
    private List<NewsMainDTO> academicNewsTopThreeTitle;

}
