<%@ page import="com.oxahex.wifinearme.service.BookMarkGroupService" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>북마크 그룹 삭제</title>
</head>
<body>

<%
    request.setCharacterEncoding("UTF-8");
    int id = Integer.parseInt(request.getParameter("id"));

    BookMarkGroupService bookMarkGroupService = new BookMarkGroupService();
    boolean isSuccess = bookMarkGroupService.deleteBookmarkGroup(id);
%>

<% if (isSuccess) { %>
    <script>alert("북마크 그룹 정보를 삭제했습니다."); location.href = "/bookmark-group.jsp";</script>
<% } else { %>
    <script>alert("북마크 그룹 정보를 삭제하지 못했습니다. 다시 시도해주세요."); location.href = "/bookmark-group.jsp";</script>
<% } %>

</body>
</html>