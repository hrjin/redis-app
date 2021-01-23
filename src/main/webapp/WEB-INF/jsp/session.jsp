<%--
  Created by IntelliJ IDEA.
  author: hrjin
  version: 1.0
  Date: 2021.01.14
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Get Session</title>
</head>
<body style="background-color: lightblue">
    <div class="container">
        <h1 style="text-align:center;color:hotpink; margin-top: 100px; margin-bottom: 50px;">Get Redis Session</h1>
        <table class="table table-hover centerTable">
            <thead class="thead-dark">
                <tr style="height: 50px; background-color: darkseagreen">
                    <th>SESSION</th>
                </tr>
            </thead>
            <tbody>
                <% pageContext.setAttribute("newLineChar", "\n"); %>
                <tr style="height: 80px; background-color: lightgrey">
                    <td style="text-align: left">${fn:replace(result, newLineChar, "<br/>")}</td>
                </tr>

            </tbody>
        </table>
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

</style>