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