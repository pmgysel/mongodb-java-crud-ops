package com.example;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CompanyRepository extends MongoRepository<Company, String> {
  List<Company> findByName(String name);
  List<Company> findByMarketCapGreaterThan(Integer marketCap);
}
