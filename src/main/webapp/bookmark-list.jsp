<%--
  Created by IntelliJ IDEA.
  User: hyesech
  Date: 2023/09/02
  Time: 3:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>북마크 그룹 목록</title>
  <link rel="stylesheet" href="index.css" />
</head>
<body>
<h1>북마크 그룹 목록</h1>

<div class="nav">
  <a href="/">홈</a>
  <a href="/history">위치 히스토리 목록</a>
  <a href="/load-wifi">Open API 와이파이 정보 가져오기</a>
  <a href="/bookmark-list.jsp">즐겨찾기 보기</a>
  <a href="/bookmark-group.jsp">즐겨찾기 그룹 관리</a>
</div>

<table id="default-table">
  <tr>
    <th>ID</th>
    <th>북마크 이름</th>
    <th>와이파이명</th>
    <th>등록 일자</th>
    <th>비고</th>
  </tr>
  <tr><td colspan="5">정보가 존재하지 않습니다.</td></tr>

</table>

</body>
</html>