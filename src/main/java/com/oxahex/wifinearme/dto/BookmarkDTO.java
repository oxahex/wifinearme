package com.oxahex.wifinearme.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookmarkDTO {
    private Integer id;
    private String bookmarkGroupName;
    private String wifiName;
    private String wifiManageNo;
    private Timestamp createTimestamp;
}

/*
 *  TODO: 이렇게 fk 가 있는 경우 DTO 선언을 어떻게 하는지?
 *   지금 프로젝트에서는 wifiName,wifi manage no(detail 페이지 이동을 위한), bookmark Group 이름 정도가 필요한데
 *   필요한 것만 따로 선언하는 것이 아니라
 *   private WifiDTO wifi;
 *   private BookmarkGroupDTO bookmarkGroup;
 *   이런 식으로 클래스 자체를 다 넣어주는 것이 좀 더 낫지 않나? 왜냐면
 *   DB에서 읽어오는 시점에 한 번에 가져올 수 있는 정보를 다 가져오니까
 *   서버에서는 이 정보를 다 들고 있는게 맞지 않나 해서...
 */