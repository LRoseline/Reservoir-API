package com.tfriends.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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

	public String Tricker(int Input_the_line_number) throws Exception {
		String s = "/home/emilia/keys.txt";
		// String s = "C:/Users/amb17/Documents/VSCode/reservoir/src/main/resources/keys.txt";
		
        File f = new File(s);

		FileReader reader = new FileReader(f);
		BufferedReader br = new BufferedReader(reader);

		String WYF = "/n";

		String str="", l="";
		
		while((l=br.readLine())!=null) {
			str += l+WYF;
		}

		br.close();
		reader.close();

		String[] array = str.trim().split(WYF);

		String [] codesplit = array[Input_the_line_number].trim().split(" : ");

		return codesplit[1];
    }
}
