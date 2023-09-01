        <%@ page import="com.oxahex.wifinearme.service.WifiService" %>
<%@ page import="com.oxahex.wifinearme.dto.WifiDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>와이파이 정보 구하기</title>
  <link rel="stylesheet" href="index.css" />
</head>
<body>
<h1>와이파이 정보 구하기</h1>

<div class="nav">
  <a href="/">홈</a>
  <a href="/history.jsp">위치 히스토리 목록</a>
  <a href="/load-wifi">Open API 와이파이 정보 가져오기</a>
</div>

<%
  String userLat = request.getParameter("lat");
  String userLnt = request.getParameter("lnt");
  double lat = userLat != null ? Double.parseDouble(userLat) : 0.0;
  double lnt = userLnt != null ? Double.parseDouble(userLnt) : 0.0;
%>

<div id="location-form" >
  <form action="/save-history" name="getWifiForm">
    <label>
      <input id="lat" type="text" value=<%=lat%> name="lat"/>
      <input id="lnt" type="text" value=<%=lnt%> name="lnt"/>
    </label>
    <button type="button" id="getLocationBtn">내 위치 가져오기</button>
    <button type="submit" id="getNearestWifi">근처 WIFI 정보 가져오기</button>
  </form>
</div>

<table id="wifi">
  <tr>
    <th>거리(Km)</th>
    <th>관리번호</th>
    <th>자치구</th>
    <th>와이파이명</th>
    <th>도로명주소</th>
    <th>상세주소</th>
    <th>설치위치(층)</th>
    <th>설치유형</th>
    <th>설치기관</th>
    <th>서비스구분</th>
    <th>망종류</th>
    <th>설치년도</th>
    <th>실내외구분</th>
    <th>WIFI접속환경</th>
    <th>X좌표</th>
    <th>Y좌표</th>
    <th>작업일자</th>
  </tr>

  <%
      WifiService wifiService = new WifiService();
      ArrayList<WifiDTO> wifiList = wifiService.getWifi(20, lat, lnt);
      int listSize = wifiList.size();
  %>

  <% if (listSize == 0) { %>
    <tr><td colspan="17">와이파이 정보를 가져온 후 실행해주세요.</td></tr>
  <% } else if (lat == 0.0 && lnt == 0.0) { %>
    <tr><td colspan="17">내 위치 가져오기를 실행 한 후 검색해주세요.</td></tr>
  <% } else { %>
    <% for (WifiDTO wifi : wifiList) { %>
      <tr>
        <td><%=wifi.getDistance()%></td>
        <td><%=wifi.getManageNo()%></td>
        <td><%=wifi.getDistrict()%></td>
        <td><a href="/detail.jsp?manageNo=<%=wifi.getManageNo()%>"><%=wifi.getWifiName()%></a></td>
        <td><%=wifi.getAddress()%></td>
        <td><%=wifi.getAddressDetail()%></td>
        <td><%=wifi.getInstallFloor()%></td>
        <td><%=wifi.getInstallType()%></td>
        <td><%=wifi.getInstallAgency()%></td>
        <td><%=wifi.getServiceType()%></td>
        <td><%=wifi.getNetType()%></td>
        <td><%=wifi.getInstallYear()%></td>
        <td><%=wifi.getInstallPlace()%></td>
        <td><%=wifi.getNetEnv()%></td>
        <td><%=wifi.getLnt()%></td>
        <td><%=wifi.getLat()%></td>
        <td><%=wifi.getWorkDate()%></td>
      </tr>
    <% } %>
 <% } %>

</table>

<script type="text/javascript" src="index.js"></script>
</body>
</html>