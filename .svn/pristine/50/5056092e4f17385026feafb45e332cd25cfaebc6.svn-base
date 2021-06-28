<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>

<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title></title>
    <!-- custom scrollbar stylesheet -->
    <link rel="stylesheet" href="static/mCustomScrollbar/jquery.mCustomScrollbar.css">
    <link rel="stylesheet" type="text/css" href="static/css/H-ui.reset.css" />
    <link rel="stylesheet" type="text/css" href="static/css/index1.css" />
    <link rel="stylesheet" href="static/css/animate.css">
    <script type="text/javascript" src="static/js/jquery-3.2.0.min.js"></script>
    <script src="static/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
    <script type="text/javascript" src="static/js/echarts.min.js"></script>

    <title>验收评价可视化后台</title>
</head>

<body>
<input type="hidden" name="companyid" id="companyid" value="${pd.company_id }"/>

<div class="star">
    <img src="static/img/star.png" alt="" class="shine shine1" style="left:338px;top:38px">
    <img src="static/img/star.png" alt="" class="shine shine2" style="left:1506px;top:170px">
    <img src="static/img/star.png" alt="" class="shine shine3" style="left:612px;top:595px">
    <img src="static/img/star.png" alt="" class="shine shine4" style="left:1470px;top:840px">
    <img src="static/img/star.png" alt="" class="shine shine5" style="left:368px;top:345px">
    <img src="static/img/star.png" alt="" class="shine shine6" style="left:2268px;top:228px">
    <img src="static/img/star.png" alt="" class="shine shine7" style="left:2830px;top:65px">
    <img src="static/img/star.png" alt="" class="shine shine8" style="left:3688px;top:51px">
    <img src="static/img/star.png" alt="" class="shine shine9" style="left:60px;top:100px">
    <img src="static/img/star.png" alt="" class="shine shine10" style="left:140px;top:150px">
    <img src="static/img/star.png" alt="" class="shine shine10" style="left:140px;top:150px">
    <img src="static/img/star.png" alt="" class="shine shine10" style="left:3255px;top:558px">
    <img src="static/img/star.png" alt="" class="shine shine11" style="left:90px;top:500px">
    <img src="static/img/star.png" alt="" class="shine shine12" style="left:390px;top:400px">
    <img src="static/img/star.png" alt="" class="shine shine13" style="left:290px;top:600px">
    <img src="static/img/star.png" alt="" class="shine shine9" style="right:60px;top:100px">
    <img src="static/img/star.png" alt="" class="shine shine10" style="right:140px;top:150px">
    <img src="static/img/star.png" alt="" class="shine shine11" style="right:90px;top:500px">
    <img src="static/img/star.png" alt="" class="shine shine12" style="right:390px;top:400px">
    <img src="static/img/star.png" alt="" class="shine shine13" style="right:290px;top:600px">
</div>
<video muted="" autoplay="autoplay" class="bg" loop="loop" src="static/img/vedio.mp4" style="object-fit: fill"></video>

<div class="main">
    <div class="headpart">
        <div class="head  animated fadeInDownBig">
            <div class="tit">
                精益化评价检查指挥中心
            </div>
        </div>
    </div>


    <div class="content">
        <div class="left">
            <div class="box">
                <div class="con1  animated fadeInDownBig">
                    <div class="con1_title">标准问题情况</div>
                    <div class="graph">
                        <div id="column_one" class="column"></div>
                        <div  class="column empty_part" id="empty_one">
                            <div class="empty">
                                <img src="static/img/empty3.png" alt="" srcset="">
                                <p>暂无数据</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="box">
                <div class="con1   animated fadeInDownBig">
                    <div class="con1_title">各单位加分情况</div>
                    <div class="graph">
                        <div id="column_two" class="column"></div>
                        <div  class="column empty_part" id="empty_two">
                            <div class="empty">
                                <img src="static/img/empty3.png" alt="" srcset="">
                                <p>暂无数据</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="box">
                <div class="con1   animated fadeInDownBig">
                    <div class="con1_title">各单位扣分情况</div>
                    <div class="graph">
                        <div id="column_three" class="column"></div>
                        <div  class="column empty_part" id="empty_three">
                            <div class="empty">
                                <img src="static/img/empty3.png" alt="" srcset="">
                                <p>暂无数据</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>


        <div class="center">

            <div class="box">
                <div class="  animated fadeInDownBig">
                    <div class="con_title"> <img src="static/img/logo.png" alt="" class="imgtop">总体情况</div>
                    <div class="graph">
                        <div class="graphbox">
                            <div class="part">
                                <div class="part_icon"><img src="static/img/company2.png" alt=""></div>
                                <div class="part_txt">单位总量</div>
                                <div class="part_num" id="unitValue">0</div>
                            </div>
                            <div class="part">
                                <div class="part_icon"><img src="static/img/group1.png" alt=""></div>
                                <div class="part_txt">团队规模</div>
                                <div class="part_num" id="userValue">0</div>
                            </div>
                            <div class="part">
                                <div class="part_icon"><img src="static/img/task3.png" alt=""></div>
                                <div class="part_txt">任务数量</div>
                                <div class="part_num" id="taskValue">0</div>
                            </div>
                            <div class="part">
                                <div class="part_icon"><img src="static/img/excel (2).png" alt=""></div>
                                <div class="part_txt">标准数量</div>
                                <div class="part_num" id="excelValue">0</div>
                            </div>
                        </div>
                        <div class="graphbox">
                            <div class="part">
                                <div class="part_icon"><img src="static/img/question2.png" alt=""></div>
                                <div class="part_txt">问题总数</div>
                                <div class="part_num" id="issueValue">0</div>
                            </div>
                            <div class="part">
                                <div class="part_icon"><img src="static/img/refiy1.png" alt=""></div>
                                <div class="part_txt">已整改</div>
                                <div class="part_num" id="totalIssueValue">0</div>
                            </div>
                            <div class="part">
                                <div class="part_icon"><img src="static/img/sub1.png" alt=""></div>
                                <div class="part_txt">总扣分</div>
                                <div class="part_num" id="etcScoreValue">0</div>
                            </div>
                            <div class="part">
                                <div class="part_icon"><img src="static/img/add1.png" alt=""></div>
                                <div class="part_txt">总加分</div>
                                <div class="part_num" id="plusScoreValue">0</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="box">
                <div class=" animated fadeInDownBig">
                    <!-- <div class="con_title">环状图</div> -->
                    <div class="graph_circle">
                        <div class="part_circle">
                            <div id="column_four" class="circle"></div>
                            <div class="column_txt">发现问题率</div>
                        </div>
                        <div class="part_circle">
                            <div id="column_five" class="circle"></div>
                            <div class="column_txt">整改问题率</div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="box">
                <div class=" animated fadeInDownBig">
                    <div class="con_title small"><img src="static/img/work.png" alt="" class="imgwork">工作量排名</div>
                    <div class="graph_table">
                        <table width="100%" border="1" cellspacing="0" cellpadding="4" id="tab">
                            <tr>
                                <th>姓名</th>
                                <th>角色</th>
                                <th>工作量</th>
                                <th>发现问题数</th>
                                <th>已整改数</th>
                            </tr>
                        </table>
                        <div  class="empty_part" id="empty_table">
                            <div class="empty">
                                <img src="static/img/empty2.png" alt="" srcset="">
                                <p class="txt">暂无数据</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="right">
            <div class="box">
                <div class="con2   animated fadeInDownBig">
                    <div class="con2_title">检查模块问题数量统计</div>
                    <div class="graph">
                        <div id="column_six" class="column"></div>
                        <div  class="column empty_part" id="empty_six">
                            <div class="empty">
                                <img src="static/img/empty3.png" alt="" srcset="">
                                <p>暂无数据</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="box">
                <div class="con2   animated fadeInDownBig">
                    <div class="con2_title">各单位问题数量统计</div>
                    <div class="graph">
                        <div id="column_seven" class="column"></div>
                        <div  class="column empty_part" id="empty_seven">
                            <div class="empty">
                                <img src="static/img/empty3.png" alt="" srcset="">
                                <p>暂无数据</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="box">
                <div class="con2   animated fadeInDownBig">
                    <div class="con3_title">
                        <p>最低数量：<input type="text" onkeyup="value=value.replace(/\D|^0/g,'')" value="2" id="question"></p>
                        <div>相同问题数量统计</div>
                    </div>
                    <div class="graph">
                        <div id="column_eight" class="column"></div>
                        <div  class="column empty_part" id="empty_eight">
                            <div class="empty">
                                <img src="static/img/empty3.png" alt="" srcset="">
                                <p >暂无数据</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="static/js/index.js"></script>
</html>
