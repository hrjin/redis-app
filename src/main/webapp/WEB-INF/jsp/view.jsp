<%--
  Created by IntelliJ IDEA.
  author: hrjin
  version: 1.0
  Date: 2021.01.14
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Get Value with Key</title>
    <script type="text/javaScript" language="javascript" defer="defer">
        function goMain() {
            procMovePage("/");

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
    <div class="container">
        <h1 style="text-align:center;color:green; margin-top: 100px; margin-bottom: 50px;">Redis Data</h1>
        <table class="table table-hover centerTable">
            <thead class="thead-dark">
                <tr style="height: 50px; background-color: darkseagreen">
                    <th>key</th>
                    <th>value</th>
                </tr>
            </thead>
            <tbody>
                <tr style="height: 80px; background-color: lightgrey">
                    <td>${key}</td>
                    <td>${value}</td>
                </tr>

            </tbody>
        </table>
        <div class="centerBtn">
            <button onclick="goMain()">메인으로</button>
        </div>

    </div>
</body>
</html>
<style type="text/css">
    .centerTable {
        border: 1px solid;
        border-collapse: collapse;
        width: 60%;
        margin: auto;
        text-align: center
    }
    th, td {
        border: 1px solid #444444;
    }

    .centerBtn {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 200px;
    }
</style>