<%@ page import="com.oxahex.wifinearme.service.HistoryService" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.oxahex.wifinearme.dto.HistoryDTO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>위치 히스토리 목록</title>
  <link rel="stylesheet" href="index.css" />
</head>
<body>
<h1>위치 히스토리 목록</h1>

<div class="nav">
  <a href="/">홈</a>
  <a href="/history.jsp">위치 히스토리 목록</a>
  <a href="/load-wifi">Open API 와이파이 정보 가져오기</a>
</div>

<%
  HistoryService historyService = new HistoryService();
  ArrayList<HistoryDTO> historyList = historyService.getHistory();
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
%>

<table id="default-table">
  <tr>
    <th>ID</th>
    <th>X좌표</th>
    <th>Y좌표</th>
    <th>조회 일자</th>
    <th>비고</th>
  </tr>

  <% if (historyList.isEmpty()) { %>
    <tr><td>근처 와이파이 조회 내역이 없습니다.</td></tr>
  <% } else { %>
    <% for (HistoryDTO history : historyList) { %>
      <tr>
        <td><%=history.getId()%></td>
        <td><%=history.getLat()%></td>
        <td><%=history.getLnt()%></td>
        <td><%=simpleDateFormat.format(history.getViewTimestamp())%></td>
        <td style="text-align: center"><a class="history-delete" href="/delete-history?id=<%=history.getId()%>">삭제</a></td>
      </tr>
    <% } %>
  <% } %>

</table>

</body>
</html>