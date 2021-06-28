package com.fh.service.app;

import com.alibaba.excel.util.StringUtils;
import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: VisualizationService
 * Description: ���ӻ��ӿ�
 * CreatedTime: 2021/5/10 10:04
 *
 * @author oh
 */
@Service("VisualizationService")
public class VisualizationService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    /**
     * ��ѯ��ǰ��˾�µ�����
     *
     * @param companyId
     * @param issueNum
     * @return
     */
    public PageData queryCompanyVisualization(String companyId, String issueNum) {
        PageData pageData = new PageData();
        //�������
        PageData companyData = queryCompanyData(companyId);
        pageData.put("companyData", companyData);
        //׼���������
        List<PageData> excelIssueChart = queryExcelIssueChart(companyId);
        pageData.put("excelIssueChart", excelIssueChart);
        //����λ�������
        List<PageData> unitIssueChart = queryUnitIssueChart(companyId);
        pageData.put("unitIssueChart", unitIssueChart);
        //����λ�۷�����
        List<PageData> unitEtcIssueChart = queryUnitEtcIssueChart(companyId);
        pageData.put("unitEtcIssueChart", unitEtcIssueChart);
        //����λ�ӷ����
        List<PageData> unitPlusIssueChart = queryUnitPlusIssueChart(companyId);
        pageData.put("unitPlusIssueChart", unitPlusIssueChart);
        //���ģ������ͳ��ͼ
        List<PageData> checkModelIssueChart = queryCheckModelIssueChart(companyId);
        pageData.put("checkModelIssueChart", checkModelIssueChart);
        //����������
        List<PageData> userWorkLoadChart = queryUserWorkLoadChart(companyId);
        pageData.put("userWorkLoadChart", userWorkLoadChart);
        //��ͬ��������ͳ��ͼ
        List<PageData> sameQuestionChart = querySameQuestionChart(companyId, issueNum);
        pageData.put("sameQuestionChart", sameQuestionChart);
        return pageData;
    }

    /**
     * ��ͬ��������ͳ��ͼ
     *
     * @param companyId
     * @return
     */
    public List<PageData> querySameQuestionChart(String companyId, String issueNum) {
        List<PageData> pageData = new ArrayList<>();
        try {
            if(StringUtils.isEmpty(issueNum)&&issueNum.matches("\\d+")){
                issueNum="1";
            }
            PageData pd = new PageData();
            pd.put("companyId", companyId);
            pd.put("issueNum", issueNum);
            pageData = (List<PageData>) dao.findForList("VisualizationMapper.sameQuestionChart", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData;
    }

    /**
     * ����������
     *
     * @param companyId
     * @return
     */
    public List<PageData> queryUserWorkLoadChart(String companyId) {
        List<PageData> pageData = new ArrayList<>();
        try {
            pageData = (List<PageData>) dao.findForList("VisualizationMapper.userWorkLoadChart", companyId);
            if(pageData.size()!=0){
                for (PageData data : pageData) {
                    double value = Double.valueOf(data.get("totalCheck").toString()).doubleValue();
                    int totalCheck = new Double(value).intValue();
                    data.put("totalCheck",totalCheck);
                    double issueValue = Double.valueOf(data.get("totalQuestion").toString()).doubleValue();
                    int totalQuestion = new Double(issueValue).intValue();
                    data.put("totalQuestion",totalQuestion);
                    double totalIssueChecks = Double.valueOf(data.get("totalIssueCheck").toString()).doubleValue();
                    int totalIssueCheck = new Double(totalIssueChecks).intValue();
                    data.put("totalIssueCheck",totalIssueCheck);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData;
    }

    /**
     * ���ģ������ͳ��ͼ
     *
     * @param companyId
     * @return
     */
    public List<PageData> queryCheckModelIssueChart(String companyId) {
        List<PageData> pageData = new ArrayList<>();
        try {
            pageData = (List<PageData>) dao.findForList("VisualizationMapper.checkModelIssueChart", companyId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData;
    }

    /**
     * ����λ�ӷ����
     *
     * @param companyId
     * @return
     */
    public List<PageData> queryUnitPlusIssueChart(String companyId) {
        List<PageData> pageData = new ArrayList<>();
        try {
            pageData = (List<PageData>) dao.findForList("VisualizationMapper.unitPlusIssueChart", companyId);
            if(pageData.size()!=0){
                for (PageData data : pageData) {
                    double issueValue = Double.valueOf(data.get("issueValue").toString()).doubleValue();
                    int issue = new Double(issueValue).intValue();
                    data.put("issueValue",issue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData;
    }


    /**
     * ����λ�۷�����
     *
     * @param companyId
     * @return
     */
    public List<PageData> queryUnitEtcIssueChart(String companyId) {
        List<PageData> pageData = new ArrayList<>();
        try {
            pageData = (List<PageData>) dao.findForList("VisualizationMapper.unitEtcIssueChart", companyId);
            if(pageData.size()!=0){
                for (PageData data : pageData) {
                    double issueValue = Double.valueOf(data.get("issueValue").toString()).doubleValue();
                    int issue = new Double(issueValue).intValue();
                    data.put("issueValue",issue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData;
    }


    /**
     * ����λ�������
     *
     * @param companyId
     * @return
     */
    public List<PageData> queryUnitIssueChart(String companyId) {
        List<PageData> pageData = new ArrayList<>();
        try {
            pageData = (List<PageData>) dao.findForList("VisualizationMapper.unitIssueChart", companyId);
            if(pageData.size()!=0){
                for (PageData data : pageData) {
                    double issueValue = Double.valueOf(data.get("issueValue").toString()).doubleValue();
                    int issue = new Double(issueValue).intValue();
                    data.put("issueValue",issue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData;
    }

    /**
     * ׼���������
     *
     * @param companyId
     * @return
     */
    public List<PageData> queryExcelIssueChart(String companyId) {
        List<PageData> pageData = new ArrayList<>();
        try {
            pageData = (List<PageData>) dao.findForList("VisualizationMapper.excelIssueChart", companyId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageData;
    }

    /**
     * ��ѯ��ǰ��˾�µ���������
     *
     * @param companyId
     * @return
     */
    public PageData queryCompanyData(String companyId) {
        PageData generalTo = new PageData();
        try {
            List<PageData> general = (List<PageData>) dao.findForList("VisualizationMapper.general", companyId);
            for (PageData data : general) {
                switch (data.getString("alias")) {
                    case "unit":
                        if(data.size()==1){
                            
                            generalTo.put("unitValue", 0);
                        }else{
                            generalTo.put("unitValue", new Double(data.get("value").toString()).intValue());
                        }
                        break;
                    case "user":
                        if(data.size()==1){
                            generalTo.put("userValue", 0);
                        }else{
                            generalTo.put("userValue", new Double(data.get("value").toString()).intValue());
                        }
                        break;
                    case "excel":
                        if(data.size()==1){
                            generalTo.put("excelValue", 0);
                        }else{
                            generalTo.put("excelValue", new Double(data.get("value").toString()).intValue());
                        }
                        break;
                    case "task":
                        if(data.size()==1){
                            generalTo.put("taskValue", 0);
                        }else{
                            generalTo.put("taskValue", new Double(data.get("value").toString()).intValue());
                        }
                        break;
                    //��������
                    case "issue":
                        if(data.size()==1){
                            generalTo.put("issueValue", 0);
                        }else{
                            generalTo.put("issueValue", new Double(data.get("value").toString()).intValue());
                        }
                        break;
                    //�ܼ����
                    case "totalCheck":
                        if(data.size()==1){
                            generalTo.put("totalCheckValue", 0);
                        }else{
                            generalTo.put("totalCheckValue", new Double(data.get("value").toString()).intValue());
                        }
                        break;
                    //�ܼ���
                    case "etcScore":
                        if(data.size()==1){
                            generalTo.put("etcScoreValue", 0);
                        }else{
                            generalTo.put("etcScoreValue", data.get("value").toString());
                        }
                        break;
                    //�ܼӷ�
                    case "plusScore":
                        if(data.size()==1){
                            generalTo.put("plusScoreValue", 0);
                        }else{
                            generalTo.put("plusScoreValue", data.get("value").toString());
                        }
                        break;
                    //���������ܼ����
                    case "checkIssue":
                        if(data.size()==1){
                            generalTo.put("checkIssueValue", 0);
                        }else{
                            generalTo.put("checkIssueValue", new Double(data.get("value").toString()).intValue());
                        }
                        break;
                    //��������������
                    case "totalIssue":
                        if(data.size()==1){
                            generalTo.put("totalIssueValue", 0);
                        }else{
                            generalTo.put("totalIssueValue", new Double(data.get("value").toString()).intValue());
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generalTo;
    }
}
