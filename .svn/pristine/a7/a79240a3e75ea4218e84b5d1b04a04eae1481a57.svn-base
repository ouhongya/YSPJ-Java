package com.fh.service.app;

import com.alibaba.excel.util.StringUtils;
import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("ImageService")
public class ImageService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    /**
     * 获取数据库图片儿
     * @param userId
     * @param functionId
     * @return
     */
    public List<String> RoleToUserByImages(String userId,String functionId) throws Exception {
        List<PageData> pageData = new ArrayList<>();
        List<String> returnImage = new ArrayList<>();
        if(functionId!=null&&functionId!=""){
            switch (functionId){
                case "1":
                    pageData = (List<PageData>) dao.findForList("ImageMapper.Role1ToUserByImages", userId);
                    break;
                case "2":
                    pageData = (List<PageData>) dao.findForList("ImageMapper.Role2ToUserByImages", userId);
                    break;
                default:
                    pageData = (List<PageData>) dao.findForList("ImageMapper.Role3ToUserByImages", userId);
                    break;
            }
            List<String> image = new ArrayList<>();
            if(pageData.size()==0){
                return returnImage;
            }
            for (PageData pageDatum : pageData) {
                if(pageDatum!=null){
                    if(pageDatum.getString("problempicture")!=null&&!"".equals(pageDatum.getString("problempicture"))){
                        image.add(pageDatum.getString("problempicture"));
                    }
                    if(pageDatum.getString("correctpicture")!=null&&!"".equals(pageDatum.getString("correctpicture"))){
                        image.add(pageDatum.getString("correctpicture"));
                    }
                }

            }
            for (String s : image) {
                if(s!=null&&!s.equals("")){
                    String[] split = s.split(",");
                    for (String s1 : split) {
                        returnImage.add(s1.split("/")[2]);
                    }
                }
            }
        }

        return returnImage;
    }
}
