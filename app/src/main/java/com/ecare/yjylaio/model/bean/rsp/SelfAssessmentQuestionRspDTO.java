package com.ecare.yjylaio.model.bean.rsp;

import java.util.List;

/**
 * NeighborhoodLongevity
 * <p>
 * Created by xuminmin on 12/10/21.
 * Email: iminminxu@gmail.com
 */
public class SelfAssessmentQuestionRspDTO {

    private String title;
    private int answer;
    private List<OptionDTO> option;

    public SelfAssessmentQuestionRspDTO(String title, List<OptionDTO> option) {
        this.title = title;
        this.option = option;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public List<OptionDTO> getOption() {
        return option;
    }

    public void setOption(List<OptionDTO> option) {
        this.option = option;
    }

    public static class OptionDTO {

        private String option;
        private String optionContent;

        public OptionDTO(String option, String optionContent) {
            this.option = option;
            this.optionContent = optionContent;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getOptionContent() {
            return optionContent;
        }

        public void setOptionContent(String optionContent) {
            this.optionContent = optionContent;
        }
    }
}
