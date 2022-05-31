package com.tfriends.domain;

import lombok.Data;

@Data
public class CompanyVO {
    private int no;
    private String type;
    private String company;
    private String leader;
    private String address;
    private String fax;
    private String tel;
}
