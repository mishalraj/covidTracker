package io.mishal.coronavirus.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.mishal.coronavirus.models.LocationStats;



@Service
public class CoronaDataService {
		
	private static String virusDataURL= "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	private List<LocationStats> allStats = new ArrayList<>();
	@PostConstruct
	@Scheduled(cron="* 1 * * * *")
	public void fetchData() throws IOException, InterruptedException {
		
		List<LocationStats> newStats = new ArrayList<>();

		HttpClient client =HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(virusDataURL))
				.build();
		HttpResponse<String> httpResponse =client.send(request, HttpResponse.BodyHandlers.ofString());
		
		StringReader csvBodyReader = new StringReader(httpResponse.body());
		
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		
		for (CSVRecord record : records) {
			LocationStats locationStat= new LocationStats();
		    locationStat.setState(record.get("Province/State"));
		    locationStat.setCountry(record.get("Country/Region"));
		   int latestCases= Integer.parseInt(record.get(record.size()-1));
		   int prevdayCases= Integer.parseInt(record.get(record.size()-2));
		    locationStat.setCases(latestCases);
		    locationStat.setNewCases(latestCases-prevdayCases);
		    newStats.add(locationStat);
		}	
		
		this.allStats= newStats;
	}
	
	public List<LocationStats> getAllStats() {
		return allStats;
	}
	
}
