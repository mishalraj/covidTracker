package io.mishal.coronavirus.models;

public class LocationStats {
	
	private String state;
	private String country;
	private int cases;
	private int newCases;
	public int getNewCases() {
		return newCases;
	}
	public void setNewCases(int newCases) {
		this.newCases = newCases;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getCases() {
		return cases;
	}
	public void setCases(int cases) {
		this.cases = cases;
	}
	@Override
	public String toString() {
		return "LocationStats [state=" + state + ", country=" + country + ", cases=" + cases + "]";
	}
	

}
