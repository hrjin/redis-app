<%--
  Created by IntelliJ IDEA.
  author: hrjin
  version: 1.0
  Date: 2021.01.12
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Redis Sample App Main</title>
    <link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/sample.css'/>"/>
    <script type="text/javaScript" language="javascript" defer="defer">
        function goValue() {
            var keyName = document.getElementById("keyName").value;
            console.log(keyName);
            procMovePage("/setSession/" + keyName);

        }

        function procMovePage(pageUrl) {
            if (pageUrl === null || pageUrl.length < 1) {
                return false;
            }

            if ((!!pageUrl && typeof pageUrl === 'number') && -1 === pageUrl) {
                history.back();
            } else {
                location.href = pageUrl;
            }
        }
    </script>
</head>
<body style="background-color: lightblue">
<h1 style="text-align:center;color:navy; margin-top: 100px; margin-bottom: 50px;">Redis Sample Java App</h1>
<div class="container">
    <div style="text-align: center;">
        <input style="width: 300px; height: 70px; border-radius: 10px;" type="text" id="keyName" placeholder="key를 입력하세요.">
        <button onclick="goValue()">조회</button>
    </div>
</div>

</body>
</html>