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
    <script charset="utf-8" src="kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="kindeditor/lang/zh-CN.js"></script>

    <script type="application/javascript">
        $(function () {
            $("#table").jqGrid({
                styleUI: "Bootstrap",
                url: "${pageContext.request.contextPath}/article/showall",
                datatype: "json",
                autowidth: true,
                height: 300,
                rowNum: "3",
                pager: "#page", //开启分页的工具栏
                rowList: ["3", "6", "9", "12"],//下拉框选择每页展示的条数
                viewrecords: true, //是否展示总记录数
                editurl: "${pageContext.request.contextPath}/article/edit",
                colNames: ["id", "标题", "作者", "内容", "创建日期", "操作"],
                colModel: [
                    {name: "id", editable: true, hidden: true}, //开启可编辑状态
                    {name: "title", editable: true}, //开启可编辑状态
                    {name: "author", editable: true}, //开启可编辑状态
                    {name: "content", editable: true},
                    {
                        name: "createDate", formatter: 'date',
                        formatoptions: {srcformat: 'Y-m-d', newformat: 'Y-m-d'}
                    },//开启可编辑状态
                    {
                        name: "caozuo",
                        formatter: function (cellvalue, options, rowObject) {
                            return "<a onclick=\"openModal('edit','" + rowObject.id + "')\" class='btn btn-info'>修改</a>";
                        }
                    },

                    //获取所有部门的url

                ],

            }).jqGrid("navGrid", "#page", {edit: false, add: false, search: false, del: true});


        })

        function openModal(oper, id) {
            KindEditor.html("#editor_id", "");
            var gr = $("#table").jqGrid('getRowData', id);
            $("#Modal").modal({
                show: true,
            })
            $("#oper").val(oper);
            $("#id").val(gr.id);
            $("#author").val(gr.author);
            $("#title").val(gr.title);
            KindEditor.html("#editor_id", gr.content);
        }

        KindEditor.create('#editor_id', {
            width: '100%',
            height: '100%',
            resizeType: 1,
            allowFileManager: true,
            fileManagerJson: "${pageContext.request.contextPath}/img/showimg",
            uploadJson: "${pageContext.request.contextPath}/img/uploadimg",
            //将编辑器中的内容进行格式转换
            afterBlur: function () {
                this.sync();
            },
            afterChange: function () {
                this.sync();
            }
        });

        function save() {
            $.ajax({
                url: "${pageContext.request.contextPath}/article/edit",
                dataTypr: "json",
                data: $("#form").serialize(),
                type: "POST",
                success: function () {
                    //关闭模态框
                    $("#Modal").modal('hide');
                    //刷新表格
                    $("#table").trigger('reloadGrid');
                }
            })
        }

        function exportArticle() {
            location.href = "${pageContext.request.contextPath}/article/exportExcel";
        }

        function close1() {
            $('#Modal').modal('hide');
        }


    </script>
</head>
<body>
<div class="panel panel-default">

    <div class="panel-body">
        <div>

            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                          data-toggle="tab">所有文章</a></li>
                <li role="presentation"><a href="" onclick="openModal('add')" aria-controls="profile" role="tab"
                                           data-toggle="tab">添加文章</a></li>
                <li role="presentation"><a href="" onclick="exportArticle()" aria-controls="profile" role="tab"
                                           data-toggle="tab">导出文章</a></li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="home">
                    <table id="table"></table>
                    <div id="page"></div>
                </div>
                <div id="Modal" class="modal fade">
                    <!--模态框的主题内容-->
                    <div class="modal-dialog">
                        <!--模态框的主题内容-->
                        <div class="modal-content">
                            <!--模态框的头部-->
                            <div class="modal-header">
                                <!--添加关闭的样式--> <!--给模态框添加关闭的触发器-->
                                <button type="button" class="close" data-dismiss="modal"><span>&times;</span>
                                </button>
                                <!--模态框的标题-->
                            </div>
                            <!--模态框的身体-->
                            <div class="modal-body">
                                <form class="form-inline" id="form">
                                    <input type="hidden" name="oper" id="oper">
                                    <input type="hidden" name="id" id="id">
                                    <div class="form-group">
                                        <label for="author">作者</label>
                                        <input type="text" class="form-control" name="author" id="author"
                                               placeholder="请输入作者...">
                                    </div>
                                    <div class="form-group">
                                        <label for="title">标题</label>
                                        <input type="email" class="form-control" name="title" id="title"
                                               placeholder="请输入标题...">
                                    </div>
                                    <div class="form-group">
                                        <textarea id="editor_id" name="content">
                                        </textarea>
                                    </div>

                                </form>

                                <div class="modal-footer">
                                    <!--添加关闭模态框的触发器-->
                                    <button type="button" class="btn btn-info" onclick="save()">保存</button>
                                    <button type="button" class="btn btn-default" onclick="close1()">取消</button>
                                </div>

                                </form>
                            </div>
                            <!--模态框的脚-->

                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div><!-- /.modal -->
            </div>

        </div>

    </div>
</div>


</body>
</html>