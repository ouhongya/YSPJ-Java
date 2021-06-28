package com.fh.controller.app;

import com.fh.controller.base.BaseController;
import com.fh.service.app.ImageService;
import com.fh.service.app.sysmenu.SysmenuService;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.ResultModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "图片同步接口")
public class ImageController extends BaseController {


    @Resource(name = "ImageService")
    private ImageService imageService;

    @RequestMapping("/updateImage")
    @ApiOperation("同步图片")
    public ResultModel<List<PageData>> updateImage(MultipartFile image, HttpServletRequest request) {
        String localAddr = request.getLocalAddr();
        int serverPort = request.getServerPort();
        List<PageData> resultList = new ArrayList<>();
        List<String> fileNames = imageFilter(request);
        String filename = image.getOriginalFilename();
        if (!fileNames.contains(filename)) {
            saveImage(request, image, Const.ISSUEIMAGEPATH);
        }
        for (String fileName : fileNames) {
            String priUrl = "http://" + localAddr + ":" + serverPort;
            PageData pageData = new PageData();
            pageData.put("imageName", fileName);
            priUrl += "/" + Const.ISSUEIMAGEPATH + fileName;
            pageData.put("imageUrl", priUrl);
            resultList.add(pageData);
        }
        return ResultModel.success(resultList);
    }

    @RequestMapping("/updateImageNull")
    @ApiOperation("同步图片没数据时")
    public ResultModel<List<PageData>> updateImageNull(HttpServletRequest request) {
        String localAddr = request.getLocalAddr();
        int serverPort = request.getServerPort();
        List<PageData> resultList = new ArrayList<>();
        String fileUrl = request.getSession().getServletContext().getRealPath(Const.ISSUEIMAGEPATH);
        //本地图片集
        List<String> fileNames = getFileNames(fileUrl);
        for (String fileName : fileNames) {
            String priUrl =  Const.ISSUEIMAGEPATH + fileName;
            PageData pageData = new PageData();
            pageData.put("imageName", fileName);
            pageData.put("imageUrl", priUrl);
            resultList.add(pageData);
        }
        return ResultModel.success(resultList);
    }

    /**
     * 用户图片过滤
     *
     * @param request
     * @return
     */
    public List<String> imageFilter(HttpServletRequest request) {
        String fileUrl = request.getSession().getServletContext().getRealPath(Const.ISSUEIMAGEPATH);
        //本地图片集
        List<String> fileNames = getFileNames(fileUrl);
        String uid = request.getParameter("uid");
        String functionId = request.getParameter("functionId");
        List<String> dbImage = new ArrayList<>();
        try {
            dbImage = imageService.RoleToUserByImages(uid, functionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(dbImage.size()!=0){
            List<String> returnImage = new ArrayList<>();
            for (String s : dbImage) {
                if (fileNames.contains(s)){
                    returnImage.add(s);
                }
            }
            return returnImage;
        }

        return fileNames;
    }


    /**
     * 读取指定文件下的图片名称
     *
     * @param path
     * @return
     */
    private List<String> getFileNames(String path) {
        List<String> fileName = new ArrayList<>();
        File file = new File(path);
        File[] array = file.listFiles();
        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                fileName.add(array[i].getName());
            } else if (array[i].isDirectory()) {
                this.getFileNames(array[i].getPath());
            }
        }
        return fileName;
    }

    /**
     * 图片保存到指定位置
     *
     * @param file
     * @param path
     * @return
     */
    public String saveImage(HttpServletRequest request, MultipartFile file, String path) {
        String url = request.getSession().getServletContext().getRealPath(path) + file.getOriginalFilename();
        String imagePath = path + file.getOriginalFilename();
        try {
            File uploadDir = new File(url);
            if (!uploadDir.exists()) {
                uploadDir.getParentFile().mkdir();
                try {
                    uploadDir.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileOutputStream out = new FileOutputStream(url);
            byte[] bytes = file.getBytes();
            out.write(bytes);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagePath;
    }
}
