<%@ page import="com.oxahex.wifinearme.service.BookMarkGroupService" %>
<%@ page import="com.oxahex.wifinearme.dto.BookmarkGroupDTO" %>
<%@ page import="java.util.ArrayList" %>
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

  <a href="/bookmark-group-add.jsp">북마크 그룹 이름 추가</a>

  <%
    BookMarkGroupService bookMarkGroupService = new BookMarkGroupService();
    ArrayList<BookmarkGroupDTO> bookmarkGroupList = bookMarkGroupService.getBookmarkGroup();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
  %>

  <table id="default-table">
    <tr>
      <th>ID</th>
      <th>북마크 이름</th>
      <th>순서</th>
      <th>등록 일자</th>
      <th>수정 일자</th>
      <th>비고</th>
    </tr>
    <% if (bookmarkGroupList.isEmpty()) { %>
      <tr><td colspan="6">현재 등록된 북마크 그룹이 없습니다. 북마크 그룹을 추가해주세요.</td></tr>
    <% } else { %>
      <% for (BookmarkGroupDTO bookmarkGroup : bookmarkGroupList) { %>
      <tr>
        <td><%=bookmarkGroup.getId()%></td>
        <td><%=bookmarkGroup.getName()%></td>
        <td><%=bookmarkGroup.getOrder()%></td>
        <td><%=simpleDateFormat.format(bookmarkGroup.getCreateTimestamp())%></td>
        <td>
          <% if (bookmarkGroup.getUpdateTimestamp() != null) { %>
            <%=simpleDateFormat.format(bookmarkGroup.getUpdateTimestamp())%>
          <% } %>
        </td>
        <td style="text-align: center">
          <a class="bookmark-group-edit" href="/bookmark-group-edit.jsp?id=<%=bookmarkGroup.getId()%>">수정</a>
          <a class="bookmark-group-delete" href="/bookmark-group-delete.jsp?id=<%=bookmarkGroup.getId()%>">삭제</a>
        </td>
      </tr>
      <% } %>
    <% } %>

  </table>

</body>
</html>