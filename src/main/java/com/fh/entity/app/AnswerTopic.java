package com.fh.entity.app;

/**
 * ClassName: AnswerTopic <br/>
 * Description: <br/>
 * CreatedTime: 2021/6/17 14:33<br/>
 *
 * @author Administrator<br />
 */
public class AnswerTopic {
    private String answerId;
    private String content;
    private String forder;
    private Integer isFlag;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getForder() {
        return forder;
    }

    public void setForder(String forder) {
        this.forder = forder;
    }

    public Integer getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(Integer isFlag) {
        this.isFlag = isFlag;
    }
}
