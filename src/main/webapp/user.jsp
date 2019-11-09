<%@page contentType="text/html; UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
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
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${pageContext.request.contextPath}/statics/jqgrid/js/ajaxfileupload.js"></script>

    <script type="application/javascript">
        $(function () {
            $("#table").jqGrid({
                styleUI: "Bootstrap",
                url: "${pageContext.request.contextPath}/user/showall",
                datatype: "json",
                autowidth: true,
                height: 300,
                rowNum: "3",
                pager: "#page", //开启分页的工具栏
                rowList: ["3", "6", "9", "12"],//下拉框选择每页展示的条数
                viewrecords: true, //是否展示总记录数
                editurl: "${pageContext.request.contextPath}/user/edit",
                colNames: ["id", "法名", "姓名", "性别", "省份", "签名", "创建日期", "操作"],
                colModel: [
                    {name: "id", editable: true, hidden: true}, //开启可编辑状态
                    {name: "dharma", editable: true}, //开启可编辑状态
                    {name: "username", editable: true}, //开启可编辑状态
                    {name: "sex", editable: true}, //开启可编辑状态
                    {name: "province", editable: true}, //开启可编辑状态
                    {name: "sign", editable: true},
                    {
                        name: "createDate", formatter: 'date',
                        formatoptions: {srcformat: 'Y-m-d', newformat: 'Y-m-d'}
                    },//开启可编辑状态
                    {
                        name: "caozuo",
                        formatter: function (cellvalue, options, rowObject) {
                            return "<a onclick=\"update('" + rowObject.id + "')\" class='btn btn-info'>修改</a>";
                        }
                    },

                    //获取所有部门的url

                ],

            }).jqGrid("navGrid", "#page", {edit: false, add: false, search: false, del: true});


        })

        function update(gr) {
            if (gr != null)
                $("#table").jqGrid('editGridRow', gr, {
                    reloadAfterSubmit: true,
                    closeAfterEdit: true
                });
            else
                alert("请选中一行");
        }

        $("#add").click(function () {

            $("#table").jqGrid('editGridRow', "new", {
                reloadAfterSubmit: true,
                closeAfterAdd: true
            })
        })

    </script>
</head>
<body>
<div class="panel panel-default">

    <div class="panel-body">
        <div>

            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                          data-toggle="tab">所有用户</a></li>
                <li role="presentation"><a href="#" id="add" aria-controls="profile" role="tab"
                                           data-toggle="tab">添加用户</a></li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="home">
                    <table id="table">
                    </table>

                    <div id="page"></div>
                </div>
            </div>

        </div>

    </div>
</div>


</body>
</html>