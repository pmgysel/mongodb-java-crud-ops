package com.example;

import org.springframework.data.annotation.Id;

public class Company {

  @Id
  public String _id;

  private String name;
  private String industrySector;
  private Integer marketCap; // in $T

  public Company(String name, String industrySector, Integer marketCap) {
    this.name = name;
    this.industrySector = industrySector;
    this.marketCap = marketCap;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIndustrySector() {
    return industrySector;
  }

  public void setIndustrySector(String industrySector) {
    this.industrySector = industrySector;
  }

  public Integer getMarketCap() {
    return marketCap;
  }

  public void setMarketCap(Integer marketCap) {
    this.marketCap = marketCap;
  }

  @Override
  public String toString() {
    return "Company{" +
        "_id='" + _id + '\'' +
        ", name='" + name + '\'' +
        ", industrySector='" + industrySector + '\'' +
        ", marketCap=" + marketCap +
        '}';
  }
}

