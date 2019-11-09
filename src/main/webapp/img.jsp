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
                url: "${pageContext.request.contextPath}/img/showall",
                datatype: "json",
                autowidth: true,
                height: 300,
                pager: "#page", //开启分页的工具栏
                rowNum: "3",//决定每页展示的条数
                rowList: ["3", "6", "9", "12"],//下拉框选择每页展示的条数
                viewrecords: true, //是否展示总记录数
                editurl: "${pageContext.request.contextPath}/img/edit",
                colNames: ["id", "名称", "封面", "描述", "创建日期", "状态"],
                colModel: [
                    {name: "id", editable: true, hidden: true}, //开启可编辑状态
                    {name: "title", editable: true}, //开启可编辑状态
                    {
                        name: "image",
                        index: "file",
                        editable: true,
                        edittype: "file",
                        enditoptions: {enctype: "multipart/form-data"},
                        name: 'file',
                        formatter: function (cellvalue, options, rowObject) {
                            return "<img style=\"height: 100px;\" src='${pageContext.request.contextPath}/upload-img/" + rowObject.image + "'>";
                        }
                    },

                    {name: "description", editable: true},                                             //获取所有部门的url
                    {
                        name: "createDate", formatter: 'date',
                        formatoptions: {srcformat: 'Y-m-d', newformat: 'Y-m-d'}
                    },                                              //获取所有部门的url
                    {
                        name: "status", editable: true, edittype: 'select',
                        editoptions: {value: "1:使用;2:锁定"}, formatter: function (value, options, row) {
                            if (value == 1) {
                                return "使用";
                            } else {
                                return "锁定";
                            }
                        }
                    }                                              //获取所有部门的url

                ]
            }).jqGrid("navGrid", "#page", {edit: true}, {
                //修改的额外控制
                closeAfterEdit: close,   //修改完成关闭修改框
                afterSubmit: function (response) {   //上传修改的图片
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/img/upload",   //上传的方法
                        fileElementId: "file",   //上传文件的属性名
                        type: "post",
                        success: function () {
                            $("#table").trigger("reloadGrid");   //刷新表格
                        }
                    });
                    return "12312";   //这里返回任意数字字符串
                },
            }, {
                //添加的额外控制
                closeAfterAdd: close,    //关闭添加框
                afterSubmit: function (response) {   //上传添加的图片
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/img/upload",
                        fileElementId: "file",
                        type: "post",
                        success: function () {
                            $("#table").trigger("reloadGrid");  //刷新表格
                        }
                    });
                    return "111";
                }

            });


        })


    </script>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-heading">展示所有轮播图</div>
    <div class="panel-body">
        <table id="table">
        </table>

        <div id="page"></div>

    </div>
</div>


</body>
</html>