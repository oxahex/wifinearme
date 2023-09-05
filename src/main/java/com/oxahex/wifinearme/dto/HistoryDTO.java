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
public class HistoryDTO {
    private Integer id;
    private double lat;
    private double lnt;
    private Timestamp viewTimestamp;
}