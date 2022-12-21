package com.tfriends.exception;

public class WeatherException extends RuntimeException {
    WeatherException() {
        super("경고, 알 수 없는 오류입니다.");
    }
    
}
