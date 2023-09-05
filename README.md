# WI-FI NEAR ME

1. [주요 기능](#주요-기능)
2. [구현부 구조와 개요](#구현부-구조와-개요)
3. [이렇게 구현한 이유 간략 설명 및 질문 정리](#이렇게-구현한-이유-간략-설명-및-질문-정리)

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

## 구현부 구조와 개요

```text
.
├── AddBookmark.java                    // 북마크 추가 POST 요청 처리
├── AddBookmarkGroup.java               // 북마크 그룹 추가 POST 요청 처리
├── DeleteBookmark.java                 // 북마크 삭제 POST 요청 처리
├── DeleteBookmarkGroup.java            // 북마크 그룹 삭제 POST 요청 처리
├── DeleteHistory.java                  // 히스토리 삭제 GET 요청 처리
├── EditBookmarkGroup.java              // 북마크 그룹 수정 POST 요청 처리
├── LoadWifi.java                       // Open API 데이터 HTTP 요청 및 DB 저장 처리
├── SaveHistory.java                    // 히스토리 저장 처리
├── db
│ └── DBManager.java                    // DB 연결, 연결 종료와 관련된 부분 구현
├── dto
│ ├── BookmarkDTO.java                  // 북마크 객체
│ ├── BookmarkGroupDTO.java             // 북마크 그룹 객체
│ ├── HistoryDTO.java                   // 히스토리 객체
│ └── WifiDTO.java                      // 와이파이 객체
├── service
│ ├── BookMarkGroupService.java         // 북마크 그룹 관련 등록/조회/수정/삭제 로직
│ ├── BookmarkService.java              // 북마크 관련 등록/조회/삭제 로직
│ ├── HistoryService.java               // 히스토리 관련 등록/조회/삭제 로직
│ ├── OpenApiService.java               // Open API HTTP 요청 로직
│ └── WifiService.java                  // 와이파이 등록/조회 로직
└── util
    └── EarthDistanceCalculator.java    // 거리 계산 유틸
```

<br />

## 이렇게 구현한 이유 간략 설명 및 질문 정리

### DB
#### DB Connection 객체를 하나만 생성하려면 어떻게 하면 될까요?

DB 연결 객체를 하나만 생성해, DB 작업이 끝나면 연결 객체를 반환하도록 처리하도록 구현해보고 싶어서 getConnection() 메서드를 static으로 만들고, 여기서 connection을 생성해 반환하도록 했습니다.

그런데 이렇게 하는 경우, 여러 명의 사용자가 동시에 connection을 생성할 때도 문제가 없을까요? DB 연결 부분을 실무에서는 어떻게 처리하는지 궁금합니다. 유저 식별값을 생성해서 유저 별로 connection을 하나씩 주나요?

```java
public class DBManager {
   public static Connection getConnection() {
      Connection connection = null;
      try {
         Class.forName(JDBC_DRIVER);
         connection = DriverManager.getConnection(DB_FILE_PATH);
      } catch (SQLException | ClassNotFoundException e) {
         System.out.println("DBManager.getConnection(): " + e.getMessage());
         e.printStackTrace();
      }
      
      return connection;
   }
}
```

### DTO
DTO를 실무에서 어떻게 정의하고, 사용하는지 아직 잘 모르지만 뷰단과 서비스단에서 데이터를 주고받기 용이할 것 같아 DTO라는 이름으로 필요한 주요 데이터 객체(와이파이, 북마크, 북마크 그룹, 히스토리)에 대한 정의를 클래스로 선언해두었습니다.

### DTO 관련 질문

#### DTO 필드 정의 시, 연결된 다른 객체가 있는 경우 필드를 어떻게 선언하는 것이 나을까요?

현재 Bookmark에 대한 필드는 아래와 같이 정의되어 있는데, 지금 프로젝트에서는 북마크 조회 시 필요한 데이터는

- 북마크 ID
- 연결된 북마크 그룹 이름: 화면 출력
- 연결된 Wi-Fi 이름: 화면 출력
- Wi-Fi 관리번호: 와이파이 detail 페이지 이동

정도라고 생각했습니다. 그래서 DTO를 아래와 같이 만들어주었습니다.

```java
public class BookmarkDTO { 
    private Integer id;
    private String bookmarkGroupName;
    private String wifiName;
    private String wifiManageNo;
    private Timestamp createTimestamp;
}
```

그런데 필요한 데이터만 고려하는 것이 아니라, 실제로 연결되어 있는 데이터를 DTO로 표현하려면 아래와 같이 정의하는 것이 좀 더 맞지 않을까 싶어서 질문 드립니다. 

```java
public class BookmarkDTO { 
    private Integer id;
    private BookmarkDTO bookmarkGroup;
    private WifiDTO wifi;
    private Timestamp createTimestamp;
}
```

### Service 관련 질문

#### DB에 저장된 정보를 가지고 값을 가공해 화면에 출력해야 하는 경우, DB에서 연산하는 것이 나은가요? 아니면 저장된 값을 그대로 가지고 와서 서비스 로직에서 가공하는 것이 나은가요?

DB에 저장된 데이터를 어떻게 가공할지는 DB가 아니라 서비스 로직에서 구현해야 하는 부분인 것 같다는 생각이 듭니다.

과제에서는 distance를 기준으로 20개 정렬 처리를 해야 해서, 정렬을 DB에서 해서 가져오도록 구현했는데(서비스 로직에서 구현하는 방법이 생각나지 않았습니다), 어떻게 하는 것이 좀 더 맞는 방법인가요?

```text
String sql = " select *,                                     "+"\n"
           + " (                                             "+"\n"
           + "   6371 *                                      "+"\n"
           + "     acos(                                     "+"\n"
           + "      cos(radians(?)) *                        "+"\n"
           + "      cos(radians(lat)) *                      "+"\n"
           + "      cos(radians(lnt) - radians(?)) +         "+"\n"
           + "      sin(radians(?)) *                        "+"\n"
           + "      sin(radians(lat))                        "+"\n"
           + "    )                                          "+"\n"
           + " ) as distance                                 "+"\n"
           + " from wifi                                     "+"\n"
           + " order by distance                             "+"\n"
           + " limit ?                                       ";
```