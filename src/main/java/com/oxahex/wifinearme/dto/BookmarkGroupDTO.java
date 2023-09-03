package com.oxahex.wifinearme.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

/*
    TODO: @AllArgsConstructor를 쓰는 것이 맞는지 공부 필요
    왜냐면 항상 전체 다 들어있지 않기 떄문인데, 케이스에 따라 다 다른 DTO를 만드는 건지?
    필수값 DTO를 만들고 상속 받아서 더 넓은 범위의 DTO를 만드는 건지?
* */

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

/*
    TODO: int, Integer 구분은 어떻게 하는지? 보통 primitive type?
 */