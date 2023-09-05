<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.oxahex.wifinearme.dto.HistoryDTO" %>
<%@ page import="com.oxahex.wifinearme.service.HistoryService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>히스토리 저장</title>
</head>
<body>

<%
  double lat = Double.parseDouble(request.getParameter("lat"));
  double lnt = Double.parseDouble(request.getParameter("lnt"));
  Timestamp now = new Timestamp(System.currentTimeMillis());

  HistoryDTO history = new HistoryDTO(null, lat, lnt, now);

  HistoryService historyService = new HistoryService();
  historyService.registerHistory(history);

  response.sendRedirect("/?lat=" + lat + "&lnt=" + lnt);
%>

</body>
</html>