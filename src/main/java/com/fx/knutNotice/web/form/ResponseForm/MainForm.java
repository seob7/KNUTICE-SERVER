package com.fx.knutNotice.web.form.ResponseForm;

import com.fx.knutNotice.dto.RecentThreeTitleDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MainForm {

    private List<RecentThreeTitleDTO> generalNewsTopThreeTitle;
    private List<RecentThreeTitleDTO> scholarshipNewsTopThreeTitle;
    private List<RecentThreeTitleDTO> eventNewsTopThreeTitle;
    private List<RecentThreeTitleDTO> academicNewsTopThreeTitle;

}
