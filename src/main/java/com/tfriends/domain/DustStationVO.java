package com.tfriends.domain;

import lombok.Data;

@Data
public class DustStationVO {
    private int no;
    private String name;
    private String type;
    private String pm10v;
    private String pm10g;
    private String pm25v;
    private String pm25g;
}
