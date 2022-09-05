package com.tfriends.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfriends.domain.DustStationVO;
import com.tfriends.domain.LocationVO;
import com.tfriends.domain.WeatherVO;
import com.tfriends.mapper.WeatherMapper;

@Service
public class WeatherService {

	@Autowired
	private WeatherMapper mapper;
	
	public int TownCount() {
		return mapper.counttown();
	}
	
	public LocationVO LocationList(int no) {
		return mapper.locationinfo(no);
	}

	public WeatherVO WeatherReady(int no) {
		return mapper.WeatherLoad(no);
	}

	public boolean WUpdate(WeatherVO weather) {
		return mapper.weatherupdate(weather) == 1;
	}

	public DustStationVO DustLoad(int no) {
		return mapper.stationno(no);
	}

	public boolean DustUpdate(DustStationVO vo) {
		return mapper.stationup(vo) == 1;
	}

	public void WeatherMigrate(String location) {
		mapper.migration(location);
	}
}
