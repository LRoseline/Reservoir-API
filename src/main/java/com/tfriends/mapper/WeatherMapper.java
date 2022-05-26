package com.tfriends.mapper;

import com.tfriends.domain.DustStationVO;
import com.tfriends.domain.LocationVO;
import com.tfriends.domain.WeatherVO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WeatherMapper {
	public int counttown();
	public LocationVO locationinfo(@Param("no")int no);

	public WeatherVO WeatherLoad(@Param("no")int no);
	public int weatherupdate(WeatherVO weather);

	public DustStationVO stationno(@Param("no")int no);
	public int stationup(DustStationVO vo);
}