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
    <script src="${pageContext.request.contextPath}/echarts/echarts.js"></script>
    <script src="${pageContext.request.contextPath}/echarts/china.js"></script>
    <script src="http://cdn.goeasy.io/goeasy-1.0.0.js"></script>
    <script>
        $.ajax({
            url: "${pageContext.request.contextPath}/user/showDate",
            dataType: "json",
            type: "POST",
            success: function (result) {
                var myChart = echarts.init(document.getElementById('main'));

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '持明法洲活跃人数'
                    },
                    tooltip: {},
                    legend: {
                        data: ['人数']
                    },
                    xAxis: {
                        data: ['近一周', '进俩周', '近三周']
                    },
                    yAxis: {},
                    series: [{
                        name: '女',
                        type: 'bar',
                        data: result.men
                    }, {
                        name: '男',
                        type: 'bar',
                        data: result.man
                    }]

                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        })
        var goEasy = new GoEasy({
            host: 'hangzhou.goeasy.io',
            appkey: 'BC-c746f63108c046c7be3f3a8ccc021179',
        });
        //接收消息
        goEasy.subscribe({
            channel: "my_channel",
            onMessage: function (message) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/user/showDate",
                    dataType: "json",
                    type: "POST",
                    success: function (result) {
                        var myChart = echarts.init(document.getElementById('main'));

                        // 指定图表的配置项和数据
                        var option = {
                            title: {
                                text: '持明法洲活跃人数'
                            },
                            tooltip: {},
                            legend: {
                                data: ['人数']
                            },
                            xAxis: {
                                data: ['近一周', '进俩周', '近三周']
                            },
                            yAxis: {},
                            series: [{
                                name: '女',
                                type: 'bar',
                                data: result.men
                            }, {
                                name: '男',
                                type: 'bar',
                                data: result.man
                            }]
                        };

                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);
                    }
                })
            }
        });


    </script>
</head>
<body>
<div id="main" style="width: 600px;height:400px;"></div>
</body>
</html>