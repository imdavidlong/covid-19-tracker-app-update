package com.site.blog.my.core.service;

import com.site.blog.my.core.entity.locationStats;
import com.site.blog.my.core.entity.stateWith7dayData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


//This service is for getting the data from CVS
@Service
public class CoronaVirusDataService {
    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private  int totalCases=0;

    public int getTotalCases() {
        return totalCases;
    }

    public List<locationStats> getAllStats() {
        return allStats;
    }

    private List<locationStats> allStats = new ArrayList<>();
    private List<stateWith7dayData> sevenDate = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "* * * 1 * *")
    public void fetchVirusData() throws IOException, InterruptedException {

        List<locationStats> newStats = new ArrayList<>();
        List<stateWith7dayData> newSevenDate = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());



        //(in) is a reader instance
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : records) {

            //save date to sevenData
            stateWith7dayData seven = new stateWith7dayData();
            seven.setCountry(record.get("Country/Region"));
            seven.getState();


            //
            locationStats locationStat = new locationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            locationStat.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));
            totalCases+=Integer.parseInt(record.get(record.size()-1));
            int newUpdate = Integer.parseInt(record.get(record.size()-1)) - Integer.parseInt(record.get(record.size()-2));
            locationStat.setNewUpdate(newUpdate);
           //System.out.println(locationStat.toString());
            newStats.add(locationStat);
        }

        this.allStats = newStats;
        this.sevenDate = newSevenDate;
    }
}
