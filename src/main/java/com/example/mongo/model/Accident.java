package com.example.mongo.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import org.springframework.data.geo.Point;

@Document(collection = "accidentes")
public class Accident {

	@Id
	private String id;

	private String weatherCondition;

	private String street;

	@Field("Start_Time")
	private String startTime;

	@Field("start_location")
	private Point location;

	private String county;

	@Field("City")
	private String city;

	public Accident(@Value("Weather_Condition") String aWeatherCondition, @Value("Street") String aStreet, @Value("Start_Time") String aStartTime, @Value("start_location") Point aLocation, @Value("County") String aCounty, @Value("City") String aCity) {
		this.setWeatherCondition(aWeatherCondition);
		this.setStreet(aStreet);
		this.setStartTime(aStartTime);
		this.setLocation(aLocation);
		this.setCounty(aCounty);
		this.setCity(aCity);
	}

	public String getId() {
		return this.id;
	}

	public void setId(String anId) {
		this.id = anId;
	}

	public String getWeatherCondition() {
		return this.weatherCondition;
	}

	public void setWeatherCondition(String aDate) {
		this.weatherCondition = aDate;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String aStreet) {
		this.street = aStreet;
	}

	public String getStartTime() { return startTime; }

	public void setStartTime(String startTime) { this.startTime = startTime; }

	public Point getLocation() { return location; }

	public void setLocation(Point location) { this.location = location; }

	public String getCounty() { return county; }

	public void setCounty(String county) { this.county = county; }

	public String getCity() { return city; }

	public void setCity(String city) { this.city = city; }
}
