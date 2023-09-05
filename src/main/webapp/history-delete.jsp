<%@ page import="com.oxahex.wifinearme.service.HistoryService" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>히스토리 삭제</title>
</head>
<body>

<%
    int historyId = Integer.parseInt(request.getParameter("id"));

    HistoryService historyService = new HistoryService();
    boolean historyDeleted = historyService.deleteHistory(historyId);
%>

<% if (historyDeleted) { %>
    <script>alert("조회 내역을 성공적으로 삭제했습니다."); location.href = "/history.jsp";</script>
<% } else { %>
    <script>alert("조회 내역을 삭제하지 못했습니다."); location.href = "/history.jsp";</script>
<% } %>

</body>
</html>