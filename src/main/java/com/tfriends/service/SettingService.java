package com.tfriends.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.springframework.stereotype.Service;

@Service
public class SettingService {
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
