package com.tfriends.reservoir;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.tfriends.domain.CompanyVO;
import com.tfriends.domain.DustStationVO;
import com.tfriends.domain.LocationVO;
import com.tfriends.domain.WeatherVO;
import com.tfriends.service.CompanyService;
import com.tfriends.service.WeatherService;

import org.json.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@SpringBootTest
class ReservoirApplicationTests {

    String s = "C:/Users/amb17/Desktop/keys.txt";

    public String Tricker(int Input_the_line_number) throws Exception {
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

	@Test
	void contextLoads() {
	}

	@Autowired
	private WeatherService service;

	@Test
	public void CountTownTest() {
		System.out.println(service.TownCount());
	}

	@Test
	public void SpotList() {
		for (int i = 0; i < service.TownCount(); i++) {
			LocationVO vo = service.LocationList(i+1);
			System.out.println(vo.getLocation());
		}
	}

    @Test
    public void OneCallExtends() throws Exception {
        SimpleDateFormat dtformat = new SimpleDateFormat("YYYY-MM-dd a hh:mm", Locale.KOREA);
        double [] LatLon = {35.68106, 139.76720};

        String OnecallURL = "http://api.openweathermap.org/data/2.5/onecall?&lang=kr&units=metric&lat="+LatLon[0]+"&lon="+LatLon[1]+"&appid="+this.Tricker(1);
    
        BufferedReader weatherbf;
        URL urlweather = new URL(OnecallURL);
        String weatherline = "";
        String weatherresult = "";
        SimpleDateFormat ForeTimeStamp = new SimpleDateFormat("a hh:mm", Locale.KOREA);
        
        weatherbf = new BufferedReader(new InputStreamReader(urlweather.openStream()));		
        while((weatherline=weatherbf.readLine())!=null) {
            weatherresult = weatherresult.concat(weatherline);
        }

        JSONObject weatherobj = new JSONObject(weatherresult);
        
        // Weather Area -----------------------------

        // System.out.println(weatherobj);
        JSONObject currentObject = weatherobj.getJSONObject("current");
        
        // Common
        Long DTimeJ = Long.parseLong(currentObject.getLong("dt")+"000");

        Long SunriseJ = Long.parseLong(currentObject.getLong("sunrise")+"000");
        Long SunsetJ = Long.parseLong(currentObject.getLong("sunset")+"000");

        Long Sunrise = currentObject.getLong("sunrise");
        Long Sunset = currentObject.getLong("sunset");

        Long temp = currentObject.getLong("temp");
        Long humidity = currentObject.getLong("humidity");

        JSONArray weatherarray = currentObject.getJSONArray("weather");
        JSONObject warray = weatherarray.getJSONObject(0);

        String main = warray.getString("main");
        String descript = warray.getString("description");
        String icon = warray.getString("icon");

        // Result Now -----------------------------------

        System.out.println("측정날짜 : "+dtformat.format(DTimeJ));
        System.out.println("일출시간 : "+ForeTimeStamp.format(Sunrise));
        System.out.println("일몰시간 : "+ForeTimeStamp.format(Sunset));
        System.out.println("현재기온 : "+temp+"C");
        System.out.println("현재습도 : "+humidity+"%");
        System.out.println("현재날씨 : "+main);
        System.out.println("날씨설명 : "+descript);
        System.out.println("현재기호 : "+icon);

        if (main.equals("Thunderstorm") || main.equals("Drizzle") || main.equals("Rain")) {
            JSONObject rain1h = currentObject.getJSONObject("rain");
            double raindrop = rain1h.getDouble("1h");
            System.out.println("강수량 : "+raindrop+"mm");
        }

        if (main.equals("Snow")) {
            JSONObject snow1h = currentObject.getJSONObject("snow");
            double snowdrop = snow1h.getDouble("1h");
            System.out.println("적설량 : "+snowdrop+"mm");
        }

        // Daily Area
        JSONArray dailyArray = weatherobj.getJSONArray("daily");
        
        for (int i = 0; i < 6; i++) {
            JSONObject dailyObject = dailyArray.getJSONObject(i);

            SunriseJ = Long.parseLong(dailyObject.getLong("sunrise")+"000");
            SunsetJ = Long.parseLong(dailyObject.getLong("sunset")+"000");
    
            JSONObject tempObj = dailyObject.getJSONObject("temp");

            Long dailyTempMin = tempObj.getLong("min");
            Long dailyTempMax = tempObj.getLong("max");

            Long dailyTempMorning = tempObj.getLong("morn");
            Long dailyTempDay = tempObj.getLong("day");
            Long dailyTempEvening = tempObj.getLong("eve");
            Long dailyTempNight = tempObj.getLong("night");

            humidity = dailyObject.getLong("humidity");
    
            weatherarray = dailyObject.getJSONArray("weather");
            warray = weatherarray.getJSONObject(0);
    
            main = warray.getString("main");
            descript = warray.getString("description");
            icon = warray.getString("icon");

            System.out.println("\n예보일자 : "+dtformat.format(DTimeJ));
            System.out.println("일출시간 : "+ForeTimeStamp.format(SunriseJ));
            System.out.println("일몰시간 : "+ForeTimeStamp.format(SunsetJ));
            System.out.println("최저최고 : "+dailyTempMin+" ~ "+dailyTempMax);
            System.out.println("기온변화 : "+dailyTempMorning+"=>"+dailyTempDay+"=>"+dailyTempEvening+"=>"+dailyTempNight);
            System.out.println("현재습도 : "+humidity+"%");
            System.out.println("현재날씨 : "+main);
            System.out.println("날씨설명 : "+descript);
            System.out.println("현재기호 : "+icon);

            if (main.equals("Thunderstorm") || main.equals("Drizzle") || main.equals("Rain")) {
                double raindrop = dailyObject.getDouble("rain");
                System.out.println("강수량 : "+raindrop+"mm");
            }

            if (main.equals("Snow")) {
                double raindrop = dailyObject.getDouble("rain");
                System.out.println("적설량 : "+raindrop+"mm");
            }
        }
    }
	
	@Test
	public void Onecall() throws Exception {
        SimpleDateFormat dtformat = new SimpleDateFormat("YYYY-MM-dd a hh:mm", Locale.KOREA);

        for (int a = 0; a < 27; a++) {
            WeatherVO vo = service.WeatherReady(a+1);
    
            String OnecallURL = "http://api.openweathermap.org/data/2.5/onecall?&lang=kr&units=metric&lat="+vo.getLat()+"&lon="+vo.getLon()+"&appid="+this.Tricker(1);
    
            BufferedReader weatherbf;
            URL urlweather = new URL(OnecallURL);
            String weatherline = "";
            String weatherresult = "";
            SimpleDateFormat ForeTimeStamp = new SimpleDateFormat("a hh:mm", Locale.KOREA);
            
            weatherbf = new BufferedReader(new InputStreamReader(urlweather.openStream()));		
            while((weatherline=weatherbf.readLine())!=null) {
                weatherresult = weatherresult.concat(weatherline);
            }
    
            JSONObject weatherobj = new JSONObject(weatherresult);
            
            // Weather Area -----------------------------
    
            // System.out.println(weatherobj);
            JSONObject currentObject = weatherobj.getJSONObject("current");
            
            // Common
            Long DTime = currentObject.getLong("dt");
            Long DTimeJ = Long.parseLong(currentObject.getLong("dt")+"000");
    
            Long SunriseJ = Long.parseLong(currentObject.getLong("sunrise")+"000");
            Long SunsetJ = Long.parseLong(currentObject.getLong("sunset")+"000");

            Long Sunrise = currentObject.getLong("sunrise");
            Long Sunset = currentObject.getLong("sunset");
    
            Long temp = currentObject.getLong("temp");
            Long humidity = currentObject.getLong("humidity");
    
            JSONArray weatherarray = currentObject.getJSONArray("weather");
            JSONObject warray = weatherarray.getJSONObject(0);
    
            String main = warray.getString("main");
            String descript = warray.getString("description");
            String icon = warray.getString("icon");
    
            // Result Now -----------------------------------
    
            System.out.println("측정날짜 : "+dtformat.format(DTimeJ));
            System.out.println("일출시간 : "+ForeTimeStamp.format(Sunrise));
            System.out.println("일몰시간 : "+ForeTimeStamp.format(Sunset));
            System.out.println("현재기온 : "+temp+"C");
            System.out.println("현재습도 : "+humidity+"%");
            System.out.println("현재날씨 : "+main);
            System.out.println("날씨설명 : "+descript);
            System.out.println("현재기호 : "+icon);

            vo.setSunrise(Sunrise);
            vo.setSunset(Sunset);

            vo.setWeather0(icon);
            vo.setTemp0(temp);
            vo.setHumid0(humidity);
    
            // Daily Area
            JSONArray dailyArray = weatherobj.getJSONArray("daily");
            
            for (int i = 0; i < 6; i++) {
                JSONObject dailyObject = dailyArray.getJSONObject(i);
    
                DTime = dailyObject.getLong("dt");
    
                SunriseJ = Long.parseLong(dailyObject.getLong("sunrise")+"000");
                SunsetJ = Long.parseLong(dailyObject.getLong("sunset")+"000");
        
                JSONObject tempObj = dailyObject.getJSONObject("temp");
    
                Long dailyTempMin = tempObj.getLong("min");
                Long dailyTempMax = tempObj.getLong("max");
    
                Long dailyTempMorning = tempObj.getLong("morn");
                Long dailyTempDay = tempObj.getLong("day");
                Long dailyTempEvening = tempObj.getLong("eve");
                Long dailyTempNight = tempObj.getLong("night");
    
                humidity = dailyObject.getLong("humidity");
        
                weatherarray = dailyObject.getJSONArray("weather");
                warray = weatherarray.getJSONObject(0);
        
                main = warray.getString("main");
                descript = warray.getString("description");
                icon = warray.getString("icon");
    
                System.out.println("예보일자 : "+dtformat.format(DTimeJ));
                System.out.println("일출시간 : "+ForeTimeStamp.format(SunriseJ));
                System.out.println("일몰시간 : "+ForeTimeStamp.format(SunsetJ));
                System.out.println("최저최고 : "+dailyTempMin+" ~ "+dailyTempMax);
                System.out.println("기온변화 : "+dailyTempMorning+"=>"+dailyTempDay+"=>"+dailyTempEvening+"=>"+dailyTempNight);
                System.out.println("현재습도 : "+humidity+"%");
                System.out.println("현재날씨 : "+main);
                System.out.println("날씨설명 : "+descript);
                System.out.println("현재기호 : "+icon);

                if (i == 1) {
                    vo.setTime1(DTime);
                    vo.setWeather1(icon);
                    vo.setTemp1(dailyTempDay);
                }
                if (i == 2) {
                    vo.setTime2(DTime);
                    vo.setWeather2(icon);
                    vo.setTemp2(dailyTempDay);
                }
                if (i == 3) {
                    vo.setTime3(DTime);
                    vo.setWeather3(icon);
                    vo.setTemp3(dailyTempDay);
                }
                if (i == 4) {
                    vo.setTime4(DTime);
                    vo.setWeather4(icon);
                    vo.setTemp4(dailyTempDay);
                }
                if (i == 5) {
                    vo.setTime5(DTime);
                    vo.setWeather5(icon);
                    vo.setTemp5(dailyTempDay);
                }
            }
            service.WUpdate(vo);
            weatherbf.close();
        }
	}

	@Test
	public void DustNEW() throws Exception {
		for (int d = 1; d < 8; d++) {
            DustStationVO vo = service.DustLoad(d);
			String station = URLEncoder.encode(vo.getName(), "UTF-8");
            String dustcom = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?returnType=json&numOfRows=1&pageNo=1&stationName="+station+"&dataTerm=DAILY&ver=1.0&serviceKey="+this.Tricker(3);
            URL urldust = new URL(dustcom);
            
            String dustline = "";
            String dustresult = "";
            BufferedReader dustbf = new BufferedReader(new InputStreamReader(urldust.openStream()));
            
            dustline = dustbf.readLine();
            dustresult = dustresult.concat(dustline);
            
            JSONObject dustobj = new JSONObject(dustresult);
            JSONObject DustResponse = dustobj.getJSONObject("response");
            JSONObject DustBody = DustResponse.getJSONObject("body");
            JSONArray DustArray = DustBody.getJSONArray("items");
            JSONObject LocalObject = DustArray.getJSONObject(0);
            
            // Dust Area --------------------------------
            String pm10v;
            String pm25v;
			String pm10g;
			String pm25g;
            
            try {
                pm10v = LocalObject.getString("pm10Value").toString();
            } catch (Exception e1) {
                pm10v = "-";
            }
    
            try {
                pm25v = LocalObject.getString("pm25Value").toString();
            } catch (Exception e1) {
                pm25v = "-";
            }
            
			try {
				pm10g = LocalObject.getString("pm10Grade").toString();
			} catch (Exception e1) {
                pm10g = "-";
            }

			try {
				pm25g = LocalObject.getString("pm25Grade").toString();
			} catch (Exception e1) {
				pm25g = "-";
            }
    
            vo.setPm10g(pm10g);
            vo.setPm25g(pm25g);
            vo.setPm10v(pm10v);
            vo.setPm25v(pm25v);
            service.DustUpdate(vo);

            dustbf.close();
        }
	}

	@Test
	public void NewAPI() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
		Calendar [] dates = {Calendar.getInstance(), Calendar.getInstance()};
		dates[0].add(Calendar.DATE, -2);

		String [] datesdf = {sdf.format(dates[0].getTime()), sdf.format(dates[1].getTime())};

		String xml;
		String URLComp = "http://apis.data.go.kr/B552149/reserviorWaterLevel/reservoirlevel/?serviceKey="+this.Tricker(3)+"&pageNo=1&numOfRows=10&fac_code=4420010010&date_s="+datesdf[0]+"&date_e="+datesdf[1];

        URL url = new URL(URLComp);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
//        http.setConnectTimeout(10000);
        http.setUseCaches(false);

        BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;
            sb.append(line);
        }
        xml = sb.toString();
        br.close();
        http.disconnect();
        
        if (xml != null) {
	        DocumentBuilderFactory factory = DocumentBuilderFactory
	                .newInstance();
	        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
	
	        InputStream is =new ByteArrayInputStream(xml.getBytes());
	        Document doc = documentBuilder.parse(is);
	        Element element = doc.getDocumentElement();

            NodeList [] voir = {
                element.getElementsByTagName("fac_name"),
                element.getElementsByTagName("check_date"),
                element.getElementsByTagName("rate"),
                element.getElementsByTagName("water_level")
            };

			for (int j = 0; j < 2; j++) {
				for (int i = 0; i < voir.length; i++) {
					System.out.println(voir[i].item(j).getFirstChild().getNodeValue());
				}
				System.out.println("==================");
			}
        }
	}

    @Autowired
    private CompanyService coms;

    @Test
    public void CountInc() throws Exception {
        System.out.println(coms.CompanyTotal());
    }

    @Test
    public void CompanyInc() {
        for (int i = 0; i < coms.CompanyTotal(); i++) {
            CompanyVO vo = coms.IncCheck(i+1);
            CompanyVO voII = new CompanyVO();
            CompanyVO voFind = coms.IncFind(vo.getCompany());

            
            if (voFind == null) {
                System.out.println("신규 회사 등록, "+vo.getCompany());
                voII.setAddress(vo.getAddress());
                voII.setCompany(vo.getCompany());
                voII.setFax(vo.getFax());
                voII.setLeader(vo.getLeader());
                voII.setTel(vo.getTel());
                voII.setType(vo.getType());
                coms.IncAdd(voII);
            } else {
                voFind.setType(voFind.getType()+","+vo.getType());
                coms.TypeAdd(voFind);
            }
        }
    }
}
