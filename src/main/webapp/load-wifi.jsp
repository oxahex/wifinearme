<%@ page import="com.oxahex.wifinearme.service.OpenApiService" %>
<%@ page import="com.oxahex.wifinearme.dto.WifiDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.oxahex.wifinearme.service.WifiService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 가져오기</title>
</head>
<body style="text-align: center">

<%
    OpenApiService openApiService = new OpenApiService();
    ArrayList<WifiDTO> wifiList = openApiService.getOpenAPI();

    WifiService wifiService = new WifiService();
    int affected = 0;
    affected = wifiService.registerWifi(wifiList);
%>

<% if (affected == 0) { %>
    <h1>와이파이 정보를 저장하지 못했습니다. 다시 시도해주세요.</h1>
<% } else { %>
    <h1><%=affected%>개의 와이파이 정보를 성공적으로 저장했습니다.</h1>
<% } %>

<a href="/">홈으로 가기</a>

</body>
</html>