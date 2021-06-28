package com.fh.entity.app;

import java.util.List;

/**
 * ClassName: PageVo <br/>
 * Description: <br/>
 * CreatedTime: 2021/6/9 16:27<br/>
 *
 * @author Administrator<br />
 */
public class PageVo {
    private String page;
    private String size;
    private String uid;
    private String search;
    private String examId;
    private String companyId;
    private List<String> ids;

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
