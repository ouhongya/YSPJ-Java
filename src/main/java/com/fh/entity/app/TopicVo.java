package com.fh.entity.app;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TopicVo {
    private String id;
    private String exam_category_id;
    private String stem;
    private String quote;
    private Integer type;
    private String exam_batch_id;
    private Integer status;
    private String user_id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private String created_time;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private String last_update_time;

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExam_category_id() {
        return exam_category_id;
    }

    public void setExam_category_id(String exam_category_id) {
        this.exam_category_id = exam_category_id;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExam_batch_id() {
        return exam_batch_id;
    }

    public void setExam_batch_id(String exam_batch_id) {
        this.exam_batch_id = exam_batch_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(String last_update_time) {
        this.last_update_time = last_update_time;
    }
}
