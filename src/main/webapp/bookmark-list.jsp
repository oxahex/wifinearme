<%@ page import="com.oxahex.wifinearme.service.BookmarkService" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.oxahex.wifinearme.dto.BookmarkDTO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
  <title>북마크 그룹 목록</title>
  <link rel="stylesheet" href="index.css" />
</head>
<body>
<h1>북마크 그룹 목록</h1>

<div class="nav">
  <a href="/">홈</a>
  <a href="/history.jsp">위치 히스토리 목록</a>
  <a href="/load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
  <a href="/bookmark-list.jsp">즐겨찾기 보기</a>
  <a href="/bookmark-group.jsp">즐겨찾기 그룹 관리</a>
</div>

<%
  BookmarkService bookmarkService = new BookmarkService();
  ArrayList<BookmarkDTO> bookmarkList = bookmarkService.getBookmark();
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
%>

<table id="default-table">
  <tr>
    <th>ID</th>
    <th>북마크 이름</th>
    <th>와이파이명</th>
    <th>등록 일자</th>
    <th>비고</th>
  </tr>

  <% if (bookmarkList.isEmpty()) { %>
    <tr><td colspan="5">정보가 존재하지 않습니다.</td></tr>
  <% } else { %>
    <% for (BookmarkDTO bookmark : bookmarkList) { %>
      <tr>
        <td><%=bookmark.getId()%></td>
        <td><%=bookmark.getBookmarkGroupName()%></td>
        <td><a href="/detail.jsp?manageNo=<%=bookmark.getWifiManageNo()%>"><%=bookmark.getWifiName()%></a></td>
        <td><%=simpleDateFormat.format(bookmark.getCreateTimestamp())%></td>
        <td style="text-align: center"><a href="/bookmark-delete.jsp?id=<%=bookmark.getId()%>">삭제</a></td>
      </tr>
    <% } %>
  <% } %>

</table>

</body>
</html>