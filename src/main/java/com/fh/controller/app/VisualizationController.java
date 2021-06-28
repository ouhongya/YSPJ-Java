package com.fh.controller.app;

import com.fh.service.app.VisualizationService;
import com.fh.util.PageData;
import com.fh.util.ResultModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: visualization
 * Description: 可视化接口
 * CreatedTime: 2021/5/10 10:04
 * @author oh
 */
@RestController
@RequestMapping("/api/v1")
@Api(tags = "oh可视化接口")
public class VisualizationController {

    @Autowired
    VisualizationService visualizationService;

    @RequestMapping("/visualization")
    @ApiOperation("可视化数据")
    public ResultModel<PageData> visualization(String companyId,String issueNum) {
        return ResultModel.success(visualizationService.queryCompanyVisualization(companyId,issueNum));
    }

    @RequestMapping("/visualizationIssue")
    @ApiOperation("可视化数据")
    public ResultModel<List<PageData>> visualizationIssue(String companyId, String issueNum) {
        return ResultModel.success(visualizationService.querySameQuestionChart(companyId,issueNum));
    }

}
