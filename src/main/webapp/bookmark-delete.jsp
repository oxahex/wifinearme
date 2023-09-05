<%@ page import="com.oxahex.wifinearme.service.BookmarkService" %>
<%@ page import="com.oxahex.wifinearme.dto.BookmarkDTO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>북마크 삭제</title>
    <link rel="stylesheet" href="index.css" />
</head>
<body>
<h1>북마크 삭제</h1>

<div class="nav">
    <a href="/">홈</a>
    <a href="/history.jsp">위치 히스토리 목록</a>
    <a href="/load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
    <a href="/bookmark-list.jsp">즐겨찾기 보기</a>
    <a href="/bookmark-group.jsp">즐겨찾기 그룹 관리</a>
</div>

<p>북마크를 삭제하시겠습니까?</p>

<%
    BookmarkService bookmarkService = new BookmarkService();
    int bookmarkId = Integer.parseInt(request.getParameter("id"));
    BookmarkDTO bookmark = bookmarkService.getBookmark(bookmarkId);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
%>

<% if (bookmark == null) { %>
<h2>존재하지 않는 북마크입니다.</h2>
<% } else { %>
<form action="/bookmark-delete-submit.jsp" method="post" accept-charset="UTF-8">
    <input type="hidden" id="bookmark-id" name="id" value="<%=bookmark.getId()%>" readonly />
    <table id="default-table">
        <tr>
            <th>북마크 이름</th>
            <td><%=bookmark.getBookmarkGroupName()%></td>
        </tr>
        <tr>
            <th>와이파이명</th>
            <td><%=bookmark.getWifiName()%></td>
        </tr>
        <tr>
            <th>등록일자</th>
            <td><%=simpleDateFormat.format(bookmark.getCreateTimestamp())%></td>
        </tr>
        <tr>
            <td style="text-align: center" colspan="2">
                <a href="/bookmark-list.jsp">돌아가기</a>
                <span>|</span>
                <button type="submit">삭제</button>
            </td>
        </tr>
    </table>
</form>
<% } %>


</body>
</html>