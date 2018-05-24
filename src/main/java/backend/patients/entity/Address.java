package backend.patients.entity;

import java.util.HashMap;

public class Address {
	
	public static HashMap<String, String> columnMapper =  new HashMap<String, String>();
	static {
		columnMapper.put("nationality", "citizenship_code");
		columnMapper.put("province", "home_addr_province_code");
		columnMapper.put("city", "home_addr_city_code");
		columnMapper.put("county", "home_addr_county_code");
		columnMapper.put("detailAddress", "home_addr_street");
		columnMapper.put("contactAddress", "next_of_kin_addr");
	}
	
	private String nationality;
	private String province;
	private String city;
	private String county;
	private String detailAddress;
	private String contactAddress;
	
	public Address(String nationality, String province, String city, String county, String detailAddress, String contactAddress) {
		this.nationality = nationality;
		this.province = province;
		this.city = city;
		this.county = county;
		this.detailAddress = detailAddress;
		this.contactAddress = contactAddress;
	}
	
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	
}
