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

  <a href="/bookmark-group-add.jsp">북마크 그룹 이름 추가</a>

  <table id="default-table">
    <tr>
      <th>ID</th>
      <th>북마크 이름</th>
      <th>순서</th>
      <th>등록 일자</th>
      <th>수정 일자</th>
      <th>비고</th>
    </tr>
    <tr><td colspan="6">현재 등록된 북마크 그룹이 없습니다. 북마크 그룹을 추가해주세요.</td></tr>

  </table>

</body>
</html>