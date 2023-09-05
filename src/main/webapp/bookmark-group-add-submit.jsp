<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.oxahex.wifinearme.dto.BookmarkGroupDTO" %>
<%@ page import="com.oxahex.wifinearme.service.BookMarkGroupService" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>북마크 그룹 추가</title>
</head>
<body>

<%
  request.setCharacterEncoding("UTF-8");
  String name = request.getParameter("name");
  int order = Integer.parseInt(request.getParameter("order"));
  Timestamp createTimeStamp = new Timestamp(System.currentTimeMillis());

  BookmarkGroupDTO bookmarkGroup = new BookmarkGroupDTO(null, name, order, createTimeStamp, null);

  BookMarkGroupService bookMarkGroupService = new BookMarkGroupService();
  boolean isSuccess = bookMarkGroupService.registerBookmarkGroup(bookmarkGroup);
%>

<% if (isSuccess) { %>
  <script>alert("북마크 그룹 정보를 추가했습니다."); location.href = "/bookmark-group.jsp";</script>
<% } else { %>
  <script>alert("북마크 그룹 정보를 추가하지 못했습니다. 다시 시도해주세요."); location.href = "/bookmark-group.jsp";</script>
<% } %>

</body>
</html>