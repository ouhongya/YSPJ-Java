package com.fh.entity.app;

import java.util.Date;

/**
 * ClassName: UserTopic <br/>
 * Description: <br/>
 * CreatedTime: 2021/6/23 15:00<br/>
 *
 * @author Administrator<br />
 */
public class UserTopic {
    private String id;
    private String exam_user_id;
    private String exam_topic_id;
    private String answer;
    private Date update_time;
    private Integer status;
    private Double score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExam_user_id() {
        return exam_user_id;
    }

    public void setExam_user_id(String exam_user_id) {
        this.exam_user_id = exam_user_id;
    }

    public String getExam_topic_id() {
        return exam_topic_id;
    }

    public void setExam_topic_id(String exam_topic_id) {
        this.exam_topic_id = exam_topic_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
