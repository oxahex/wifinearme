<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.oxahex.wifinearme.service.BookMarkGroupService" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>북마크 그룹 수정</title>
</head>
<body>

<%
  request.setCharacterEncoding("UTF-8");

  int id = Integer.parseInt(request.getParameter("id"));
  String name = request.getParameter("name");
  int order = Integer.parseInt(request.getParameter("order"));
  Timestamp updateTimestamp = new Timestamp(System.currentTimeMillis());

  BookMarkGroupService bookMarkGroupService = new BookMarkGroupService();
  boolean isSuccess = bookMarkGroupService.updateBookmarkGroup(id, name, order, updateTimestamp);
%>

<% if (isSuccess) { %>
  <script>alert("북마크 그룹 정보를 수정했습니다."); location.href = "/bookmark-group.jsp";</script>
<% } else { %>
  <script>alert("북마크 그룹 정보를 수정하지 못했습니다."); location.href = "/bookmark-group.jsp";</script>
<% } %>

</body>
</html>