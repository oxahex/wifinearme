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

/*
    TODO: unix timestamp or Date?
    유닉스 타임스탬프와 Date 중 어떤 것을 쓰는 것이 나은지?
 */

/*
    TODO: Date java.sql or java.util?
    DB에서 값을 받아오기 때문에 sql 패키지의 Date를 사용했는데
    어떤 차이가 있는지? 실무에서는 어떤 것을 쓰는지?
 */