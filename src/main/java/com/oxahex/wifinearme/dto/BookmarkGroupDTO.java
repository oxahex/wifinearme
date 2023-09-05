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
public class BookmarkGroupDTO {
    private Integer id;
    private String name;
    private Integer order;
    private Timestamp createTimestamp;
    private Timestamp updateTimestamp;
}