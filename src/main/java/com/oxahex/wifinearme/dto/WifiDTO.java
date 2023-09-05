package com.oxahex.wifinearme.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class WifiDTO {
    private Double distance;                    // 사용자 위치와의 거리
    private String manageNo;                    // X_SWIFI_MGR_NO           관리 번호
    private String district;                    // X_SWIFI_WRDOFC           자치구
    private String wifiName;                    // X_SWIFI_MAIN_NM          와이파이 이름
    private String address;                     // X_SWIFI_ADRES1           도로명 주소
    private String addressDetail;               // X_SWIFI_ADRES2           상세 주소
    private String installFloor;                // X_SWIFI_INSTL_FLOOR      설치 위치(층)
    private String installType;                 // X_SWIFI_INSTL_TY         설치 유형
    private String installAgency;               // X_SWIFI_INSTL_MBY        설치 기관
    private String serviceType;                 // X_SWIFI_SVC_SE           서비스 구분
    private String netType;                     // X_SWIFI_CMCWR            망 종류
    private String installYear;                 // X_SWIFI_CNSTC_YEAR       설치 년도
    private String installPlace;                // X_SWIFI_INOUT_DOOR       실내외 구분
    private String netEnv;                      // X_SWIFI_REMARS3          WIFI 접속 환경
    private double lnt;                         // LNT                      경도
    private double lat;                         // LAT                      위도
    private String workDate;                    // WORK_DTTM                작업 일자
}