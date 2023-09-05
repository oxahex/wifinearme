<%@ page import="com.oxahex.wifinearme.service.BookmarkService" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>북마크 삭제</title>
</head>
<body>

<%
  request.setCharacterEncoding("UTF-8");
  int bookmarkId = Integer.parseInt(request.getParameter("id"));

  BookmarkService bookmarkService = new BookmarkService();
  boolean isSuccess = bookmarkService.deleteBookmark(bookmarkId);
%>

<% if (isSuccess) { %>
  <script>alert("북마크 정보를 삭제했습니다."); location.href = "/bookmark-list.jsp";</script>
<% } else { %>
  <script>alert("북마크 정보를 삭제하지 못했습니다. 다시 시도해주세요."); location.href = "/bookmark-list.jsp";</script>
<% } %>

</body>
</html>