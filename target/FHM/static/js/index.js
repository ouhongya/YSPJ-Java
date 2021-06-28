//自定义柱体颜色库
var colorList = [
    '#a0cfff',
    '#8abeb2',
    '#fab6b6',
    '#899dc0',
    '#fae371',
    '#3ebcca',
    '#c6ede8',
    '#aed7ed',
    '#94e1ff',
    '#b9e3d9',
    '#71d5a1',
    '#fc9d9a',
    '#f9cdad',
    '#ECF0F1',
    '#c8c8a9',
    '#83af9b',
    '#b6c29a',
    '#8a977b',
    '#c9ba83',
    '#ddd38c',
    '#a0bf7c',
    '#65934a',
    '#e0a09e',
    '#e1e9dc',
    '#269d81',
    '#adc3c0',
    '#608f9f',
    '#dacfcb',
    '#a6887d'
];


function getData() {
    var companyId = $("#companyid").val();
    $.post("/api/v1/visualization.do", {
        companyId: companyId,
        issueNum: document.getElementById("question").value
    }, function (res) {
        //总体情况
        let companyData = res.data.companyData
        document.getElementById("unitValue").innerHTML = companyData.unitValue
        document.getElementById("userValue").innerHTML = companyData.userValue
        document.getElementById("taskValue").innerHTML = companyData.taskValue
        document.getElementById("excelValue").innerHTML = companyData.excelValue
        document.getElementById("issueValue").innerHTML = companyData.issueValue
        document.getElementById("totalIssueValue").innerHTML = companyData.totalIssueValue
        document.getElementById("etcScoreValue").innerHTML = companyData.etcScoreValue
        document.getElementById("plusScoreValue").innerHTML = companyData.plusScoreValue
        //发现问题率
        console.log(Math.round(parseFloat(companyData.issueValue)))
        console.log(Math.round(parseFloat(companyData.totalCheckValue)))
        var e = parseFloat(companyData.totalCheckValue) <= 0 ? 0 : (Math.round(parseFloat(companyData.issueValue) / parseFloat(companyData.totalCheckValue) * 10000) / 100)
        //问题整改率
        var ee = parseFloat(companyData.checkIssueValue) <= 0 ? 0 : (Math.round(parseFloat(companyData.totalIssueValue) / parseFloat(companyData.checkIssueValue) * 10000) / 100)
        //准则问题情况
        let excelIssueChart = res.data.excelIssueChart
        //各单位问题情况
        let unitIssueChart = res.data.unitIssueChart

        //各单位扣分排名
        let unitEtcIssueChart = res.data.unitEtcIssueChart
        //各单位加分情况
        let unitPlusIssueChart = res.data.unitPlusIssueChart
        //检查模块问题统计图
        let checkModelIssueChart = res.data.checkModelIssueChart
        //工作量排名
        let userWorkLoadChart = res.data.userWorkLoadChart

        if (userWorkLoadChart.length == 0) {

            $("#empty_table").show()
        } else {

            $("#empty_table").hide()
            $("#tab  tr:not(:first)").html("");
            for (var i = 0; i < userWorkLoadChart.length; i++) {
                console.log(userWorkLoadChart[i])
                var $tr = $("<tr />");
                var $td = $("<td></td>");
                $td.text(userWorkLoadChart[i].userName);
                $tr.append($td);
                var $td1 = $("<td></td>");
                $td1.text(userWorkLoadChart[i].roleName);
                $tr.append($td1);
                var $td2 = $("<td></td>");
                $td2.text(userWorkLoadChart[i].totalCheck);
                $tr.append($td2);
                var $td3 = $("<td></td>");
                $td3.text(userWorkLoadChart[i].totalQuestion);
                $tr.append($td3);
                var $td4 = $("<td></td>");
                $td4.text(userWorkLoadChart[i].totalIssueCheck);
                $tr.append($td4);
                $("#tab").append($tr);
            }

        }


        // 准则问题情况
        var chartDom = document.getElementById('column_one');
        var myChart = echarts.init(chartDom);
        var showSlider1;
        var percent1;
        var data1 = [];
        var data12 = [];
        for (var i = 0; i < excelIssueChart.length; i++) {
            data1.push(excelIssueChart[i].name);
            data12.push(excelIssueChart[i].ratio)
        }
        if (data1.length == 0) {
            $("#column_one").hide();
            $("#empty_one").show();
        } else {
            $("#column_one").show();
            $("#empty_one").hide();
        }
        var tabletype = 'line';
        var colorsingle;
        if (data1.length <= 1) {
            tabletype = 'bar';
            colorsingle='#a0cfff';
        } else {

            tabletype = 'line';
           colorsingle=new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: '#81befd' // 0% 处的颜色
            }, {
                offset: 0.8,
                color: '#89b6df' // 80% 处的颜色
            }, {
                offset: 1,
                color: '#e4f2ff' // 100% 处的颜色
            }])//背景渐变色
        }
        if (data1.length > 10) {
            showSlider1 = true;
            percent1 = Math.floor((10 / data1.length) * 100);
        } else {
            showSlider1 = false;
            percent1 = 100;
        }
        var yAxisMax1 = 5;
        if (Math.max(...data12) >= 5) {
            yAxisMax1 = Math.max(...data12) + Math.ceil(Math.max(...data12) / 5);

        } else {
            yAxisMax1 = 5;
        }
        var option;
        option = {
            tooltip: {
                trigger: 'axis',
            },
            dataZoom: [{
                type: 'slider',
                // 数据窗口范围的起始百分比
                start: 0,
                // 数据窗口范围的结束百分比
                end: percent1,
                show: showSlider1,
                zoomLock: true,
                height: 12,
                textStyle: false,
            }],
            legend: {
                show: false
            },
            color: ["#869ad7"],
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [{
                axisTick: {
                    show: false
                },
                type: 'category',
                boundaryGap: false,
                axisLine: {
                    lineStyle: {
                        color: '#fff'
                    }
                },
                axisLabel: {
                    formatter: function (value) {
                        let valueTxt = value.substring(0, 5) + '...'
                        return value.length > 6 ? valueTxt : value
                    }
                },
                data: data1
            }],
            yAxis: [{
                type: 'value',
                axisTick: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#fff'
                    }
                },
                minInterval: 1,
                max: yAxisMax1

            }],
            series: [{
                    name: '问题数量',
                    type: tabletype,
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: '#ffffff'
                        }
                    }
                },
                    barWidth: data1.length < 5 ? (data1.length * 10 + '%') : '50%',
                    smooth: true,
                    itemStyle: {
                        normal: { //颜色渐变函数 前四个参数分别表示四个位置依次为左、下、右、上
                            color: colorsingle,
                            lineStyle: { // 系列级个性化折线样式
                                width: 3,
                                type: 'solid',
                                color: "#0180ff" //折线的颜色
                            }
                        },
                        // emphasis: {
                        //     color: '#0180ff',
                        //     lineStyle: { // 系列级个性化折线样式
                        //         width: 2,
                        //         type: 'dotted',
                        //         color: "0180ff"
                        //     }
                        // },
                    },
                    symbolSize: 5, //折线点的大小
                    areaStyle: {
                        normal: {}
                    },
                    data: data12
                }

            ],
        }

        option && myChart.setOption(option);


        //各单位加分情况
        var chartDom2 = document.getElementById('column_two');
        var myChart2 = echarts.init(chartDom2);
        var showSlider2;
        var percent2;
        var data2 = [];
        var data13 = [];
        for (var i = 0; i < unitPlusIssueChart.length; i++) {
            data2.push(unitPlusIssueChart[i].unit_name);
            data13.push(unitPlusIssueChart[i].score)
        }
        if (data2.length == 0) {
            $("#column_two").hide();
            $("#empty_two").show();
        } else {
            $("#column_two").show();
            $("#empty_two").hide();
        }
        if (data2.length > 10) {
            showSlider2 = true;
            percent2 = Math.floor((10 / data2.length) * 100);
        } else {
            showSlider2 = false;
            percent2 = 100;
        }
        var yAxisMax2 = 5;
        if (Math.max(...data12) >= 5) {
            yAxisMax2 = Math.max(...data13) + Math.ceil(Math.max(...data13) / 5);

        } else {
            yAxisMax2 = 5;
        }
        var option2;
        option2 = {
            dataZoom: [{
                type: 'slider',
                // 数据窗口范围的起始百分比
                start: 0,
                // 数据窗口范围的结束百分比
                end: percent2,
                show: showSlider2,
                zoomLock: true,
                height: 12,
                textStyle: false,
            }],
            tooltip: {
                trigger: 'axis',
                formatter: '{b}<br />加分 : {c}'
            },
            xAxis: {
                axisTick: {
                    show: false
                },
                type: 'category',
                data: data2,
                axisLine: {
                    lineStyle: {
                        color: '#fff'
                    }
                },
                axisLabel: {
                    formatter: function (value) {
                        let valueTxt = value.substring(0, 5) + '...'
                        return value.length > 6 ? valueTxt : value
                    }
                },

            },
            yAxis: {
                type: 'value',
                axisTick: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#fff'
                    }
                },
                minInterval: 1,
                max: yAxisMax2

            },
            series: [{
                data: data13,
                type: 'bar',
                barWidth: data2.length < 5 ? (data2.length * 10 + '%') : '50%',
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: '#ffffff'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: function (params) {
                            return colorList[params.dataIndex % colorList.length];
                        }
                    }
                }
            }]

        };

        option2 && myChart2.setOption(option2);


        //各单位扣分情况
        var chartDom3 = document.getElementById('column_three');
        var myChart3 = echarts.init(chartDom3);
        var showSlider3;
        var percent3;
        var data3 = [];
        var data13 = [];
        var data14 = [];
        var totlescore = 0;
        for (var i = 0; i < unitEtcIssueChart.length; i++) {
            data3.push(unitEtcIssueChart[i].unit_name)
            data13.push(unitEtcIssueChart[i].score)
            totlescore = totlescore + Number(unitEtcIssueChart[i].score)
        }
        for (var i = 0; i < unitEtcIssueChart.length; i++) {

            num = parseFloat(unitEtcIssueChart[i].score);
            total = parseFloat(totlescore);
            var atole = Math.round(num / total * 100);
            data14.push(atole)
        }

        if (data3.length == 0) {
            $("#column_three").hide();
            $("#empty_three").show();
        } else {
            $("#column_three").show();
            $("#empty_three").hide();
        }

        if (data3.length > 10) {
            showSlider3 = true;
            percent3 = Math.floor((10 / data3.length) * 100);
        } else {
            showSlider3 = false;
            percent3 = 100;
        }
        var yAxisMax3 = 5;
        if (Math.max(...data13) >= 5) {
            yAxisMax3 = Math.max(...data13) + Math.ceil(Math.max(...data13) / 5);

        } else {
            yAxisMax3 = 5;
        }
        var option3;
        option3 = {
            tooltip: {
                trigger: 'axis',
                formatter: '{b0}<br />{a0}: {c0}<br />{a1}: {c1}%'
            },
            dataZoom: [{
                type: 'slider',
                // 数据窗口范围的起始百分比
                start: 0,
                // 数据窗口范围的结束百分比
                end: percent3,
                show: showSlider3,
                zoomLock: true,
                height: 12,
                textStyle: false,
            }],
            legend: {
                show: false
            },
            xAxis: {
                axisTick: {
                    show: false
                },
                data: data3,
                axisLine: {
                    lineStyle: {
                        color: '#ffffff',

                    }
                },
                axisLabel: {
                    formatter: function (value) {
                        let valueTxt = value.substring(0, 5) + '...'
                        return value.length > 6 ? valueTxt : value
                    }
                },
            },
            yAxis: [{
                type: 'value',
                name: '扣分',
                // show:false,
                // interval: 10,
                axisTick: {
                    show: false
                },
                splitLine: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#ffffff',

                    }
                },
                minInterval: 1,
                max: yAxisMax3

            }, {
                axisTick: {
                    show: false
                },
                type: 'value',
                name: '占比',
                min: 0,
                max: 100,
                axisLabel: {
                    formatter: '{value} %'
                },
                axisLine: {
                    lineStyle: {
                        color: '#ffffff',

                    }
                }
            }],
            series: [{
                name: '扣分',
                type: 'bar',
                barWidth: data3.length < 5 ? (data3.length * 10 + '%') : '50%',
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: '#ffffff'
                        }
                    }
                },
                data: data13,
                itemStyle: {
                    normal: {
                        color: function (params) {
                            return colorList[params.dataIndex % colorList.length];
                        }
                    }
                }
            }, {
                name: '占比',
                type: 'line',
                smooth: true,
                yAxisIndex: 1,
                data: data14,
                itemStyle: {
                    normal: {
                        color: '#0180ff',
                        lineStyle: {
                            width: 3,
                            type: 'solid',
                            color: "#0180ff" //折线的颜色
                        }
                    },
                    emphasis: {
                        color: '#0180ff',
                        lineStyle: {
                            width: 2,
                            type: 'dotted',
                            color: "0180ff"
                        }
                    },
                },
                symbolSize: 5, //折线点的大小
            }]
        };


        option3 && myChart3.setOption(option3);

        // 检查模块问题数量统计
        var chartDom6 = document.getElementById('column_six');
        var myChart6 = echarts.init(chartDom6);
        var showSlider6;
        var percent6;
        var data6 = [];
        var data15 = [];
        for (var i = 0; i < checkModelIssueChart.length; i++) {
            data6.push(checkModelIssueChart[i].modelName)
            data15.push(checkModelIssueChart[i].value)
        }
        if (data6.length == 0) {
            $("#column_six").hide();
            $("#empty_six").show();
        } else {
            $("#column_six").show();
            $("#empty_six").hide();
        }
        if (data6.length > 10) {
            showSlider6 = true;
            percent6 = Math.floor((10 / data6.length) * 100);
        } else {
            showSlider6 = false;
            percent6 = 100;
        }
        var yAxisMax4 = 5;
        if (Math.max(...data15) >= 5) {
            yAxisMax4 = Math.max(...data15) + Math.ceil(Math.max(...data15) / 5);

        } else {
            yAxisMax4 = 5;
        }
        var option6;

        option6 = {
            tooltip: {
                trigger: 'axis',
                formatter: '{b}<br />问题数量 : {c}'
            },
            dataZoom: [{
                type: 'slider',
                // 数据窗口范围的起始百分比
                start: 0,
                // 数据窗口范围的结束百分比
                end: percent6,
                show: showSlider6,
                zoomLock: true,
                height: 12,
                textStyle: false,
            }],
            xAxis: {
                axisTick: {
                    show: false
                },
                type: 'category',
                data: data6,
                axisLine: {
                    lineStyle: {
                        color: '#fff'
                    }
                },
                axisLabel: {
                    formatter: function (value) {
                        let valueTxt = value.substring(0, 5) + '...'
                        return value.length > 6 ? valueTxt : value
                    }
                },
            },
            yAxis: {
                type: 'value',
                axisTick: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#fff'
                    }
                },
                minInterval: 1,
                max: yAxisMax4

            },
            series: [{
                data: data15,
                type: 'bar',
                barWidth: data6.length < 5 ? (data6.length * 10 + '%') : '50%',
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: '#ffffff'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: function (params) {
                            return colorList[params.dataIndex % colorList.length];
                        }
                    }
                }
            }]
        };

        option6 && myChart6.setOption(option6);


        //各单位问题数量统计
        var chartDom7 = document.getElementById('column_seven');
        var myChart7 = echarts.init(chartDom7);
        var showSlider7;
        var percent7;
        var data7 = [];
        var data16 = [];
        var data17 = [];
        var arr = []
        for (var i = 0; i < unitIssueChart.length; i++) {
            data7.push(unitIssueChart[i].unit_name)
            data16.push(unitIssueChart[i].issueValue)
            data17.push(unitIssueChart[i].value)
            arr.push(unitIssueChart[i].issueValue + unitIssueChart[i].value)
        }
        if (data7.length == 0) {
            $("#column_seven").hide();
            $("#empty_seven").show();
        } else {
            $("#column_seven").show();
            $("#empty_seven").hide();
        }
        if (data7.length > 10) {
            showSlider7 = true;
            percent7 = Math.floor((10 / data7.length) * 100);
        } else {
            showSlider7 = false;
            percent7 = 100;
        }
        var yAxisMax5 = 5;
        if (Math.max(...arr) >= 5) {
            yAxisMax5 = Math.max(...arr) + Math.ceil(Math.max(...arr) / 5);

        } else {
            yAxisMax5 = 5;
        }

        var option7;
        option7 = {
            tooltip: {
                trigger: "axis",
            },
            dataZoom: [{
                type: 'slider',
                // 数据窗口范围的起始百分比
                start: 0,
                // 数据窗口范围的结束百分比
                end: percent7,
                show: showSlider7,
                zoomLock: true,
                height: 12,
                textStyle: false,
            }],
            legend: {

                top: 20,
                left: 30,
                textStyle: {
                    color: '#fff'
                },

                show: true
            },
            xAxis: {
                axisTick: {
                    show: false
                },
                data: data7,
                axisLine: {
                    lineStyle: {
                        color: '#fff'
                    }
                },
                axisLabel: {
                    formatter: function (value) {
                        let valueTxt = value.substring(0, 5) + '...'
                        return value.length > 6 ? valueTxt : value
                    }
                },
            },
            yAxis: {
                axisTick: {
                    show: false
                },
                type: 'value',
                axisLine: {
                    lineStyle: {
                        color: '#fff'
                    }
                },
                minInterval: 1,
                max: yAxisMax5
            },
            series: [{
                name: '问题数量',
                type: 'bar',
                barWidth: data7.length < 5 ? (data7.length * 10 + '%') : '50%',
                label: {
                    normal: {
                        show: true,
                        textStyle: {
                            color: '#ffffff'
                        }
                    }
                },
                stack: '数量',
                data: data17,
                itemStyle: {
                    normal: {
                        color: "#4185f4"
                    },
                }

            }, {
                name: '已整改数量',
                type: 'bar',
                label: {
                    normal: {
                        show: true,
                        formatter: function(params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return ' ';
                            }
                        },
                        textStyle: {
                            color: '#ffffff'
                        }
                    }
                },
                barWidth: data7.length < 5 ? (data7.length * 10 + '%') : '50%',
                stack: '数量',
                data: data16,
                itemStyle: {
                    normal: {
                        color: "#8ec3ec"
                    },
                }
            }]
        };


        option7 && myChart7.setOption(option7);

        // 发现问题率
        var chartDom4 = document.getElementById('column_four');
        var myChart4 = echarts.init(chartDom4);
        var option4;
        option4 = {
            color: ['#dd6161', '#d6dfe4'],
            title: {
                show: true,
                text: e + '%',
                x: 'center',
                y: 'center',
                textStyle: {
                    fontSize: '30',
                    color: 'white',
                    fontWeight: 'normal'
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: "{d}%",
                show: false
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                show: false
            },
            series: {
                name: '',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: true,
                hoverAnimation: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: false
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [{
                        value: e,
                        name: ''
                    },
                    {
                        value: 100 - e,
                        name: ''
                    }
                ]
            }
        };
        option4 && myChart4.setOption(option4);


        //问题整改率
        var chartDom5 = document.getElementById('column_five');
        var myChart5 = echarts.init(chartDom5);
        var option5;
        option5 = {
            color: ['#18b566', '#d6dfe4'],
            title: {
                show: true,
                text: ee + '%',
                x: 'center',
                y: 'center',
                textStyle: {
                    fontSize: '30',
                    color: 'white',
                    fontWeight: 'normal'
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: "{d}%",
                show: false
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                show: false
            },
            series: {
                name: '',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: true,
                hoverAnimation: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: false
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [{
                        value: ee,
                        name: ''
                    },
                    {
                        value: 100 - ee,
                        name: ''
                    }
                ]
            }

        };

        option5 && myChart5.setOption(option5);

    });
}





// 相同问题数量统计
function getSmliar() {
    var companyId = $("#companyid").val();
    var question = $("#question").val();
    $.post("/api/v1/visualizationIssue.do", {
        companyId: companyId,
        issueNum: question
    }, function (res) {
        //相同问题数量统计图
        let sameQuestionChart = res.data;
        var chartDom8 = document.getElementById('column_eight');
        var myChart8 = echarts.init(chartDom8);
        var showSlider8;
        var percent8;
        var data8 = [];
        var data18 = [];
        for (var i = 0; i < sameQuestionChart.length; i++) {
            data8.push(sameQuestionChart[i].content)
            data18.push(sameQuestionChart[i].ratio)
        }
        if (data8.length == 0) {
            $("#column_eight").hide();
            $("#empty_eight").show();
        } else {
            $("#column_eight").show();
            $("#empty_eight").hide();
        }
        if (data8.length > 10) {
            showSlider8 = true;
            percent8 = Math.floor((10 / data1.length) * 100);
        } else {
            showSlider8 = false;
            percent8 = 100;
        }
        var yAxisMax6 = 5;
        if (Math.max(...data18) >= 5) {
            yAxisMax6 = Math.max(...data18) + Math.ceil(Math.max(...data18) / 5);

        } else {
            yAxisMax6 = 5;
        }
        var option8;

        option8 = {
            tooltip: {
                trigger: 'axis',
                formatter: '{b}<br />相同问题数 : {c}'
            },
            dataZoom: [{
                type: 'slider',
                // 数据窗口范围的起始百分比
                start: 0,
                // 数据窗口范围的结束百分比
                end: percent8,
                show: showSlider8,
                zoomLock: true,
                height: 12,
                textStyle: false,
            }],
            xAxis: {
                axisTick: {
                    show: false
                },
                type: 'category',
                data: data8,
                axisLine: {
                    lineStyle: {
                        color: '#fff'
                    }
                },
                axisLabel: {
                    formatter: function (value) {
                        let valueTxt = value.substring(0, 5) + '...'
                        return value.length > 6 ? valueTxt : value
                    }
                },
            },
            yAxis: {
                axisTick: {
                    show: false
                },
                type: 'value',
                axisLine: {
                    lineStyle: {
                        color: '#fff'
                    }
                },
                minInterval: 1,
                max: yAxisMax6

            },
            series: [{
                data: data18,
                type: 'bar',
                barWidth: data8.length < 5 ? (data8.length * 10 + '%') : '50%',
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: '#ffffff'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: function (params) {
                            return colorList[params.dataIndex % colorList.length];
                        }
                    }
                }
            }]
        };

        option8 && myChart8.setOption(option8);

    })
}

//调用函数
getData();
getSmliar();

//间隔一段时间调用函数
setInterval(function () {
    getSmliar();
    getData();
}, 20000)


//相同问题失去焦点使用
var question = document.getElementById('question');
question.onblur = function () {
    if (question.value < 2 && question.value.length == 1) {
        question.value = 2;
    }
    getSmliar();
};