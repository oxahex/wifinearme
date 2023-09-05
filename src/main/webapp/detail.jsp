<%@ page import="com.oxahex.wifinearme.service.WifiService" %>
<%@ page import="com.oxahex.wifinearme.dto.WifiDTO" %>
<%@ page import="com.oxahex.wifinearme.util.EarthDistanceCalculator" %>
<%@ page import="java.util.Objects" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.oxahex.wifinearme.dto.BookmarkGroupDTO" %>
<%@ page import="com.oxahex.wifinearme.service.BookMarkGroupService" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보</title>
    <link rel="stylesheet" href="index.css" />
</head>
<body>
<%
    String userManageNo = request.getParameter("manageNo");
%>
<h1>와이파이 정보: <%=userManageNo%></h1>

<div class="nav">
    <a href="/">홈</a>
    <a href="/history.jsp">위치 히스토리 목록</a>
    <a href="/load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
    <a href="/bookmark-list.jsp">즐겨찾기 보기</a>
    <a href="/bookmark-group.jsp">즐겨찾기 그룹 관리</a>
</div>

<%
    WifiService wifiService = new WifiService();
    WifiDTO wifi = wifiService.getWifi(userManageNo);
    EarthDistanceCalculator calculator = new EarthDistanceCalculator();

    double lat = 0.0;
    double lnt = 0.0;
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), "lat")) lat = Double.parseDouble(cookie.getValue());
            if (Objects.equals(cookie.getName(), "lnt")) lnt = Double.parseDouble(cookie.getValue());
        }
    }

    BookMarkGroupService bookMarkGroupService = new BookMarkGroupService();
    ArrayList<BookmarkGroupDTO> bookmarkGroupList = bookMarkGroupService.getBookmarkGroup();

%>

<form action="/add-bookmark" method="post">
    <label for="bookmark-select"></label>
    <select name="bookmark-group-id" id="bookmark-select" required>
        <% if (bookmarkGroupList.isEmpty()) { %>
            <option value="" selected disabled>등록된 북마크 그룹이 없습니다.</option>
        <% } else { %>
            <option value="" selected disabled>북마크 그룹 이름 선택</option>
            <% for (BookmarkGroupDTO bookmarkGroup : bookmarkGroupList) { %>
                <option value="<%=bookmarkGroup.getId()%>"><%=bookmarkGroup.getName()%></option>
            <% }%>
        <% } %>
    </select>
    <input type="hidden" id="wifi-select" name="wifi-manage-no" value="<%=wifi.getManageNo()%>" />
    <button type="submit">즐겨찾기 추가하기</button>
</form>


<table id="default-table">
    <% if (wifi == null) { %>
        <tr><td>와이파이 정보를 가져오지 못했습니다. 다시 시도해주세요.</td></tr>
    <% } else { %>
        <tr><th>거리(Km)</th><td><%=calculator.calculateDistance(lat, lnt, wifi.getLat(), wifi.getLnt())%></td></tr>
        <tr><th>관리번호</th><td><%=wifi.getManageNo()%></td></tr>
        <tr><th>자치구</th><td><%=wifi.getDistrict()%></td></tr>
        <tr><th>와이파이명</th><td><%=wifi.getWifiName()%></td></tr>
        <tr><th>도로명주소</th><td><%=wifi.getAddress()%></td></tr>
        <tr><th>상세주소</th><td><%=wifi.getAddressDetail()%></td></tr>
        <tr><th>설치위치(층)</th><td><%=wifi.getInstallFloor()%></td></tr>
        <tr><th>설치유형</th><td><%=wifi.getInstallType()%></td></tr>
        <tr><th>설치기관</th><td><%=wifi.getInstallAgency()%></td></tr>
        <tr><th>서비스구분</th><td><%=wifi.getServiceType()%></td></tr>
        <tr><th>망종류</th><td><%=wifi.getNetType()%></td></tr>
        <tr><th>설치년도</th><td><%=wifi.getInstallYear()%></td></tr>
        <tr><th>실내외구분</th><td><%=wifi.getInstallPlace()%></td></tr>
        <tr><th>WIFI접속환경</th><td><%=wifi.getNetEnv()%></td></tr>
        <tr><th>X좌표</th><td><%=wifi.getLnt()%></td></tr>
        <tr><th>Y좌표</th><td><%=wifi.getLat()%></td></tr>
        <tr><th>작업일자</th><td><%=wifi.getWorkDate()%></td></tr>
    <% } %>
</table>

</body>
</html>