<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
  <title>북마크 그룹 추가</title>
  <link rel="stylesheet" href="index.css" />
</head>
<body>
<h1>북마크 그룹 추가</h1>

<div class="nav">
  <a href="/">홈</a>
  <a href="/history.jsp">위치 히스토리 목록</a>
  <a href="/load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
  <a href="/bookmark-list.jsp">즐겨찾기 보기</a>
  <a href="/bookmark-group.jsp">즐겨찾기 그룹 관리</a>
</div>

<form action="/add-bookmark-group" method="post">
  <table id="default-table">
    <tr>
      <th><label for="name">북마크 이름</label></th>
      <td><input type="text" id="name" name="name" /></td>
    </tr>
    <tr>
      <th><label for="order">순서</label></th>
      <td><input type="number" id="order" name="order" /></td>
    </tr>
    <tr>
      <td style="text-align: center" colspan="2"><button type="submit">추가</button></td>
    </tr>
  </table>
</form>

</body>
</html>