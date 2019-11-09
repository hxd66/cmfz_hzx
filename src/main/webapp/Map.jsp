<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/login/assets/js/jquery-1.11.1.js"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/echarts/echarts.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/echarts/china.js"></script>
    <script>
        $(function () {
            $.ajax({
                url: "${pageContext.request.contextPath}/user/showMap",
                dataType: "json",
                type: "post",
                success: function (result) {
                    myChart.setOption({
                        series: [
                            {
                                name: '男',
                                data: result.man
                            },
                            {
                                name: '女',
                                data: result.men
                            }]
                    });
                }
            })
            var goEasy = new GoEasy({
                host: 'hangzhou.goeasy.io',//应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
                appkey: 'BC-c746f63108c046c7be3f3a8ccc021179',
            });
            //接收消息
            goEasy.subscribe({
                channel: "my_channel",
                onMessage: function (message) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/user/showMap",
                        dataType: "json",
                        type: "post",
                        success: function (result) {
                            myChart.setOption({
                                series: [
                                    {
                                        name: '男',
                                        data: result.man
                                    },
                                    {
                                        name: '女',
                                        data: result.men
                                    }]
                            });
                        }
                    })
                }
            });

            var myChart = echarts.init(document.getElementById('statistics_china'));

            function randomData() {
                return Math.round(Math.random() * 1000);
            }

            option = {
                title: {
                    text: '持名法州APP用户分布图',
                    subtext: '2017年6月15日 最新数据',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                // 说明
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['男', '女']
                },
                visualMap: {
                    min: 0,
                    max: 2500,
                    left: 'left',
                    top: 'bottom',
                    text: ['高', '低'],           // 文本，默认为数值文本
                    calculable: true
                },
                // 工具箱
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        dataView: {readOnly: false},
                        restore: {},
                        saveAsImage: {}
                    }
                },
                series: [
                    {
                        name: '男',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: true
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        //data: []
                    },
                    {
                        name: '女',
                        type: 'map',
                        mapType: 'china',
                        label: {
                            normal: {
                                show: true
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        //data: []
                    }
                ]
            };
            myChart.setOption(option);


        })

    </script>
</head>
<body>
<div id="statistics_china" style="width: 600px;height:400px;"></div>
</body>
</html>