package com.tfriends.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeatherAPI {
    private Long date;
    private String weather;
	private double temp;
    private double humid;
	private Long time;
    private double drop;
}
