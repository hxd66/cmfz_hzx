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
                url: "${pageContext.request.contextPath}/album/showall",
                datatype: "json",
                autowidth: true,
                height: 300,
                pager: "#page", //开启分页的工具栏
                rowList: ["3", "6", "9", "12"],//下拉框选择每页展示的条数
                viewrecords: true, //是否展示总记录数
                editurl: "${pageContext.request.contextPath}/album/edit",
                colNames: ["id", "标题", "作者", "封面", "集数", "简介", "创建日期"],
                colModel: [
                    {name: "id", editable: true, hidden: true}, //开启可编辑状态
                    {name: "title", editable: true}, //开启可编辑状态
                    {name: "author", editable: true}, //开启可编辑状态
                    {
                        name: "cover",
                        index: "file",
                        editable: true,
                        edittype: "file",
                        enditoptions: {enctype: "multipart/form-data"},
                        name: 'file',
                        formatter: function (cellvalue, options, rowObject) {
                            return "<img style=\"height: 60px;\" src='${pageContext.request.contextPath}/upload-img/" + rowObject.cover + "'>";
                        }
                    },

                    {name: "chapterCount"},                                             //获取所有部门的url
                    {name: "brief", editable: true},                                           //获取所有部门的url,
                    {
                        name: "createDate", formatter: 'date',
                        formatoptions: {srcformat: 'Y-m-d', newformat: 'Y-m-d'}
                    },                                            //获取所有部门的url


                ],
                rowNum: "3",
                subGrid: true,
                subGridRowExpanded: function (subgrid_id, row_id) {
                    var subgrid_table_id = subgrid_id + "_t";
                    var pager_id = "p_" + subgrid_table_id;
                    $("#" + subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table><div id='" + pager_id + "' class='scroll'></div>");
                    $("#" + subgrid_table_id).jqGrid({
                        styleUI: "Bootstrap",
                        url: "${pageContext.request.contextPath}/chapter/showall?id=" + row_id,
                        datatype: "json",
                        autowidth: true,
                        editurl: "${pageContext.request.contextPath}/chapter/edit?albumid=" + row_id,
                        colNames: ['编号', '名称', '大小', '时长', '创建日期', '章节音频'],
                        colModel: [
                            {name: "id", width: 80, key: true},
                            {name: "title", width: 130, editable: true},
                            {name: "size", width: 70, align: "right"},
                            {name: "duration", width: 70, align: "right"},
                            {name: "createDate", width: 70, align: "right", sortable: false},
                            {
                                name: "url", editable: true, edittype: "file",
                                formatter: function (cellvalue, options, rowObject) {
                                    return "<audio style=\"width:1600px;\" controls=\"controls\" src='${pageContext.request.contextPath}/upload-mp3/" + rowObject.url + "'></audio>";
                                }
                            }
                        ],
                        rowNum: 3,
                        pager: pager_id,
                        sortname: 'num',
                    });
                    $("#" + subgrid_table_id).jqGrid('navGrid', "#" + pager_id, {edit: true, add: true, del: true}, {
                        //修改的额外控制
                        closeAfterEdit: close,   //修改完成关闭修改框
                        afterSubmit: function (response) {   //上传修改的图片
                            var code = response.responseJSON.code;
                            var id = response.responseJSON.date;
                            if (code == 200) {
                                $.ajaxFileUpload({
                                    url: "${pageContext.request.contextPath}/chapter/upload",   //上传的方法
                                    fileElementId: "url",   //上传文件的属性名
                                    data: {id: id},  //要修改的banner的id
                                    type: "post",
                                    success: function () {

                                        $("#" + subgrid_table_id).trigger("reloadGrid");   //刷新表格
                                        $("#table").trigger("reloadGrid");
                                    }
                                });
                            }
                            return "12312";   //这里返回任意数字字符串
                        },
                    }, {
                        //添加的额外控制
                        closeAfterAdd: close,    //关闭添加框
                        afterSubmit: function (response) {   //上传添加的图片
                            var code = response.responseJSON.code;
                            var id = response.responseJSON.data;
                            if (code == 200) {
                                $.ajaxFileUpload({
                                    url: "${pageContext.request.contextPath}/chapter/upload",
                                    fileElementId: "url",
                                    data: {id: id},
                                    type: "post",
                                    success: function () {
                                        $("#" + subgrid_table_id).trigger("reloadGrid");  //刷新表格
                                        $("#table").trigger("reloadGrid");
                                    }
                                });
                                return "111";
                            }

                        }
                    }, {
                        afterSubmit: function (response) {   //上传添加的图片
                            $("#table").trigger("reloadGrid");

                        }
                    })
                }
            }).jqGrid("navGrid", "#page", {edit: true}, {
                //修改的额外控制
                closeAfterEdit: close,   //修改完成关闭修改框
                afterSubmit: function (response) {   //上传修改的图片
                    var code = response.responseJSON.code;
                    var id = response.responseJSON.data;
                    if (code == 200) {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/album/upload",   //上传的方法
                            fileElementId: "file",   //上传文件的属性名
                            data: {id: id},  //要修改的banner的id
                            type: "post",
                            success: function () {
                                $("#table").trigger("reloadGrid");   //刷新表格
                            }
                        });
                    }
                    return "12312";   //这里返回任意数字字符串
                },
            }, {
                //添加的额外控制
                closeAfterAdd: close,    //关闭添加框
                afterSubmit: function (response) {   //上传添加的图片
                    var code = response.responseJSON.code;
                    var id = response.responseJSON.data;
                    if (code == 200) {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/album/upload",
                            fileElementId: "file",
                            data: {id: id},
                            type: "post",
                            success: function () {
                                $("#table").trigger("reloadGrid");  //刷新表格
                            }
                        });
                        return "111";
                    }

                }
            });


        })

        function exportAlbum() {
            location.href = "${pageContext.request.contextPath}/album/exportAlbum";
        }

    </script>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-heading">展示所有轮播图</div>

    <div class="panel-body">
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">所有专辑</a>
            </li>
            <li role="presentation"><a href="" onclick="exportAlbum()" aria-controls="profile" role="tab"
                                       data-toggle="tab">导出专辑</a></li>
        </ul>
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="home">
                <table id="table"></table>
                <div id="page"></div>
            </div>
        </div>
    </div>
</div>


</body>
</html>