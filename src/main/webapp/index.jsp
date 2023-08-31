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
  <a href="/history">위치 히스토리 목록</a>
  <a href="/load-wifi">Open API 와이파이 정보 가져오기</a>
</div>

<div id="location-form" >
  <form action="object_ok.jsp" name="getWifiForm">
    <label>
      <input id="lat" type="text" value="0.0" name="lat"/>
      <input id="lnt" type="text" value="0.0" name="lnt"/>
    </label>
    <button type="button" id="getLocationBtn">내 위치 가져오기</button>
    <button type="submit" id="getNearestWifi">근처 WIFI 정보 가져오기</button>
  </form>
</div>

<table id="wifi">
  <tr>
    <th>거리</th>
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

  <tr>
    <td>Alfreds Futterkiste</td>
    <td>Maria Anders</td>
    <td>Germany</td>
  </tr>
  <tr>
    <td>Berglunds snabbköp</td>
    <td>Christina Berglund</td>
    <td>Sweden</td>
  </tr>
  <tr>
    <td>Centro comercial Moctezuma</td>
    <td>Francisco Chang</td>
    <td>Mexico</td>
  </tr>
  <tr>
    <td>Ernst Handel</td>
    <td>Roland Mendel</td>
    <td>Austria</td>
  </tr>
  <tr>
    <td>Island Trading</td>
    <td>Helen Bennett</td>
    <td>UK</td>
  </tr>
  <tr>
    <td>Königlich Essen</td>
    <td>Philip Cramer</td>
    <td>Germany</td>
  </tr>
  <tr>
    <td>Laughing Bacchus Winecellars</td>
    <td>Yoshi Tannamuri</td>
    <td>Canada</td>
  </tr>
  <tr>
    <td>Magazzini Alimentari Riuniti</td>
    <td>Giovanni Rovelli</td>
    <td>Italy</td>
  </tr>
  <tr>
    <td>North/South</td>
    <td>Simon Crowther</td>
    <td>UK</td>
  </tr>
  <tr>
    <td>Paris spécialités</td>
    <td>Marie Bertrand</td>
    <td>France</td>
  </tr>
</table>

<script type="text/javascript" src="index.js"></script>
</body>
</html>