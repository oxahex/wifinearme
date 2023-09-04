# WI-FI NEAR ME

1. [주요 기능](#주요-기능)
2. [폴더 구조 및 페이지 기능 상세](#폴더-구조-및-페이지-기능-상세)

<br />

## 주요 기능

1. [Open API 데이터 저장](#open-api-데이터-저장)
2. [사용자 위치 기반 근처 Wi-Fi 검색](#사용자-위치-기반-근처-wi-fi-검색)
3. [근처 Wi-Fi 검색 내역](#근처-wi-fi-검색-내역)
4. [북마크 그룹 관리](#북마크-그룹-관리)
5. [북마크 관리](#북마크-관리)

### Open API 데이터 저장
1. [Open API 데이터](https://data.seoul.go.kr/dataList/OA-20883/S/1/datasetView.do) HTTP 요청 및 DB 저장

### 사용자 위치 기반 근처 Wi-Fi 검색
1. 사용자 위치(위경도) 구하기
2. DB에 저장된 Wi-Fi 데이터가 있는 경우 가장 가까운 순서대로 20개 데이터 조회
   - 사용자가 입력한 위치 정보는 DB에 저장하지 않고, 조회 시 연산하도록 처리
3. Wi-Fi 이름 클릭 시 `/detail` 페이지로 이동

### 근처 Wi-Fi 검색 내역
1. Wi-Fi 검색 시 GET 방식으로 검색 history 생성 후 DB 저장
2. `/history` 페이지에서 검색 내역 조회

### 북마크 그룹 관리
1. 북마크 그룹 생성(POST)
   - 북마크 그룹 생성 시 순서 정렬 처리 구현(생성 시 DB에 동일 순서가 존재하는 경우)
2. 북마크 그룹 수정(POST)
3. 북마크 그룹 삭제(POST)

생성/수정/삭제 수행 후 `/bookmark-group.jsp` 페이지로 리다이렉트 처리

### 북마크 관리
1. 북마크 생성(POST)
   - 생성된 북마크 그룹이 없는 경우 그룹을 우선 생성하도록 처리
   - 북마크 생성 시 이전 페이지(`/detail`)로 리다이렉트 처리
2. 북마크 목록 조회
   - `/bookmark-list.jsp` 페이지에서 조회
3. 북마크 삭제(POST)

<br />
<br />

## 폴더 구조 및 메서드 상세

```text
/src
├── db			// DB 관련 클래스
├── dto			// 데이터 정의 클래스
├── service		// 기능 단위로 정의한 서비스 로직 구현부
├── util		// 위경도 거리 계산 관련 유틸 클래스
└── ...			// 서블릿 클래스		
```

### db
```text
└── db
  └── DBManager.java
```

#### DBManager
- getConnection()
- closeConnection()

<br />

### dto
```text
└── dto
  ├── BookmarkDTO.java
  ├── BookmarkGroupDTO.java
  ├── HistoryDTO.java
  └── WifiDTO.java
```

#### BookmarkDTO
#### BookmarkGroupDTO
#### HistoryDTO
#### WifiDTO

<br />

### service
```text
└── service
  ├── BookMarkGroupService.java
  ├── BookmarkService.java
  ├── HistoryService.java
  ├── OpenApiService.java
  └── WifiService.java
```
#### BookmarkGroupService
#### BookmarkService
#### HistoryService
#### OpenApiService
#### WifiService

<br />

### util
```text
└── util
    └── EarthDistanceCalculator.java
```
#### EarthDistanceCalculator

<br />

### Servlet
```text
└── .
  ├── AddBookmark.java
  ├── AddBookmarkGroup.java
  ├── DeleteBookmark.java
  ├── DeleteBookmarkGroup.java
  ├── DeleteHistory.java
  ├── EditBookmarkGroup.java
  ├── LoadWifi.java
  └── SaveHistory.java
```