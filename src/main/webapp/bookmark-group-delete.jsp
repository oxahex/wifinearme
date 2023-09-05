<%@ page import="com.oxahex.wifinearme.service.BookMarkGroupService" %>
<%@ page import="com.oxahex.wifinearme.dto.BookmarkGroupDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>북마크 그룹 삭제</title>
    <link rel="stylesheet" href="index.css" />
</head>
<body>
<h1>북마크 그룹 삭제</h1>

<div class="nav">
    <a href="/">홈</a>
    <a href="/history.jsp">위치 히스토리 목록</a>
    <a href="/load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
    <a href="/bookmark-list.jsp">즐겨찾기 보기</a>
    <a href="/bookmark-group.jsp">즐겨찾기 그룹 관리</a>
</div>

<%
    BookMarkGroupService bookMarkGroupService = new BookMarkGroupService();
    int id = Integer.parseInt(request.getParameter("id"));
    BookmarkGroupDTO bookmarkGroup = bookMarkGroupService.getBookmarkGroup(id);
%>

<% if (bookmarkGroup == null) { %>
<h2>존재하지 않는 북마크 그룹입니다.</h2>
<% } else { %>
<form action="/bookmark-group-delete-submit.jsp" method="post" accept-charset="UTF-8">
    <input type="hidden" id="id" name="id" value="<%=bookmarkGroup.getId()%>" readonly />
    <table id="default-table">
        <tr>
            <th><label for="name">북마크 이름</label></th>
            <td><input type="text" id="name" name="name" value="<%=bookmarkGroup.getName()%>" disabled/></td>
        </tr>
        <tr>
            <th><label for="order">순서</label></th>
            <td><input type="number" id="order" name="order" value="<%=bookmarkGroup.getOrder()%>" disabled/></td>
        </tr>
        <tr>
            <td style="text-align: center" colspan="2">
                <p>북마크 그룹을 삭제하는 경우 북마크 정보가 함께 삭제됩니다.</p>
                <a href="/bookmark-group.jsp">돌아가기</a>
                <span>|</span>
                <button type="submit">삭제</button>
            </td>
        </tr>
    </table>
</form>
<% } %>

</body>
</html>