<%@page contentType="text/html; utf-8" isELIgnored="false" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script charset="utf-8" src="kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="kindeditor/lang/zh-CN.js"></script>
    <script>
        KindEditor.ready(function (K) {
            window.editor = K.create('#editor_id', {
                width: '800px', height: '300px',
                allowFileManager: true,
                fileManagerJson: "${pageContext.request.contextPath}/img/showimg",
                uploadJson: "${pageContext.request.contextPath}/img/uploadimg",
            });
        });
    </script>
</head>
<body>
<textarea id="editor_id" name="content">
        &lt;strong&gt;HTML内容&lt;/strong&gt;
    </textarea>
</body>
</html>