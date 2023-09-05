<%@ page import="com.oxahex.wifinearme.service.BookmarkService" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>북마크 추가</title>
</head>
<body>

<%
    request.setCharacterEncoding("UTF-8");

    int bookmarkGroupId = Integer.parseInt(request.getParameter("bookmark-group-id"));
    String wifiManageNo = request.getParameter("wifi-manage-no");

    BookmarkService bookmarkService = new BookmarkService();
    boolean isSuccess = bookmarkService.registerBookmark(bookmarkGroupId, wifiManageNo);
%>

<% if (isSuccess) { %>
    <script>alert("북마크 정보를 추가했습니다."); location.href = "/bookmark-list.jsp";</script>
<% } else { %>
    <script>alert("북마크 정보를 추가하지 못했습니다. 다시 시도해주세요."); location.href = "/detail.jsp?manageNo=<%=wifiManageNo%>";</script>
<% } %>

</body>
</html>