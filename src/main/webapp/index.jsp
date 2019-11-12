<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <!--引入BootStrap的css样式-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css">
    <!--引入BootStrap与JQGRID整合的css样式-->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/bootstrap-jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <!--引入jquery的js文件-->
    <script src="${pageContext.request.contextPath}/login/assets/js/jquery-1.11.1.js"></script>
    <!--引入bootstrap的js文件-->
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
    <!--引入BootStrap与JQGRID整合的国际化文件-->
    <script src="${pageContext.request.contextPath}/bootstrap-jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <!--引入BootStrap与JQGRID整合过后的js文件-->
    <script src="${pageContext.request.contextPath}/bootstrap-jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${pageContext.request.contextPath}/echarts/echarts.js"></script>
    <script src="${pageContext.request.contextPath}/echarts/china.js"></script>
    <script src="http://cdn.goeasy.io/goeasy-1.0.0.js"></script>
    <script>
        $(function () {
            $("#redirect").click(function () {
                $("#index").load('${pageContext.request.contextPath}/img.jsp');
            })
            $("#redirect2").click(function () {
                $("#index").load('${pageContext.request.contextPath}/album.jsp');
            })
            $("#redirect3").click(function () {
                $("#index").load('${pageContext.request.contextPath}/article.jsp');
            })
            $("#redirect4").click(function () {
                $("#index").load('${pageContext.request.contextPath}/user.jsp');
            })
            $("#redirect5").click(function () {
                $("#index").load('${pageContext.request.contextPath}/echartsUser.jsp');
            })
            $("#redirect6").click(function () {
                $("#index").load('${pageContext.request.contextPath}/Map.jsp');
            })
        })
    </script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">持明法洲后台管理系统</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

            <ul class="nav navbar-nav navbar-right">
                <li><a class="glyphicon glyphicon-user"></a></li>
                <li><a href="#">欢迎</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">
                        <shiro:authenticated>
                        <shiro:principal></shiro:principal> <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/Admin/loginout">安全退出</a></li>
                    </ul>
                    </shiro:authenticated>
                    <shiro:notAuthenticated>
                        <a href="${pageContext.request.contextPath}/login/login.jsp">登录</a>
                    </shiro:notAuthenticated>

                </li>
            </ul>
        </div>
    </div>
</nav>

<div>
    <div class="panel-group col-md-2" id="accordion">
        <!--默认样式的面板-->
        <div class="panel panel-default">
            <!--面板头-->
            <div class="panel-heading">

                <h4 class="panel-title">
                    <!--添加手风琴的触发器--> <!--保证面板之间可以进行对应的交互-->
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                        轮播图管理
                    </a>
                </h4>
            </div>
            <!--展示的具体内容-->
            <div id="collapseOne" class="panel-collapse collapse in">
                <ul class="nav nav-tabs nav-pills nav-stacked" role="tablist">
                    <li role="presentation" class=""><a href="" id="redirect" aria-controls="home" role="tab"
                                                        data-toggle="tab" class="btn btn-default">轮播图详情</a></li>
                </ul>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title"> <!--保证面板之间可以进行对应的交互-->
                    <a class="collapsed" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseTwo">
                        专辑管理
                    </a>
                </h4>
            </div>
            <div id="collapseTwo" class="panel-collapse collapse">
                <ul class="nav nav-tabs nav-pills nav-stacked" role="tablist">
                    <li role="presentation" class=""><a class="btn btn-default" href="" id="redirect2"
                                                        aria-controls="home" role="tab" data-toggle="tab">专辑详情</a></li>
                </ul>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title"> <!--保证面板之间可以进行对应的交互-->

                    <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                        文章管理
                    </a>
                </h4>
            </div>
            <div id="collapseThree" class="panel-collapse collapse">
                <ul class="nav nav-tabs nav-pills nav-stacked" role="tablist">
                    <li role="presentation" class=""><a href="" id="redirect3" aria-controls="home" role="tab"
                                                        data-toggle="tab" class="btn btn-default">查询文章</a></li>
                </ul>

            </div>
        </div>
        <shiro:hasRole name="super">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title"> <!--保证面板之间可以进行对应的交互-->
                        <a class="collapsed active" data-toggle="collapse" data-parent="#accordion"
                           href="#collapseFour">
                            用户管理
                        </a>
                    </h4>
                </div>
                <div id="collapseFour" class="panel-collapse collapse">
                    <ul class="nav nav-tabs nav-pills nav-stacked" role="tablist">
                        <li role="presentation"><a class="btn btn-default"
                                                   href="javascript:$('#index').load('${pageContext.request.contextPath}/user.jsp')">查询用户</a>
                        </li>
                    </ul>
                </div>
            </div>
        </shiro:hasRole>

        <div class="panel panel-default">
            <!--面板头-->
            <div class="panel-heading">

                <h4 class="panel-title">
                    <!--添加手风琴的触发器--> <!--保证面板之间可以进行对应的交互-->
                    <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseFive">
                        活跃人数
                    </a>
                </h4>
            </div>
            <!--展示的具体内容-->
            <div id="collapseFive" class="panel-collapse collapse">
                <ul class="nav nav-tabs nav-pills nav-stacked" role="tablist">
                    <li role="presentation" class=""><a href="" id="redirect5" aria-controls="home" role="tab"
                                                        data-toggle="tab" class="btn btn-default">查看活跃人数</a></li>
                    <li role="presentation" class=""><a href="" id="redirect6" aria-controls="home" role="tab"
                                                        data-toggle="tab" class="btn btn-default">查看各地区人数</a></li>
                </ul>
            </div>
        </div>

    </div>
</div>
<div id="index" class="col-md-10">
    <div class="page-header">
        <h1>持明法洲后台管理系统</h1>
    </div>
    <div class="jumbotron">
        <img src="${pageContext.request.contextPath}/statics/img/shouye.png"></img>
    </div>
</div>
<div class="bc-social">
    <div class="container">
        <ul class="bc-social-buttons">
            <li class="social-forum" style="text-align: center">
                持明法洲后台管理系统@百知教育2019-08-13
            </li>
        </ul>
    </div>
</div>
</body>
</html>