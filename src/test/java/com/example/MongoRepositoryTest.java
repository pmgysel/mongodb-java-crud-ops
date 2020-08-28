package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MongoRepositoryTest {
  @Autowired
  CompanyRepository repository;

  @BeforeEach
  void before() {
    repository.deleteAll();
  }

  @Test
  void showcaseCrud() {
    // create
    Company amazon = new Company("Amazon", "e-commerce, cloud", 1600);
    Company google = new Company("Google", "cloud, ads", 1010);
    repository.save(amazon);
    repository.save(google);

    // read: find all
    System.out.println("Companies after create:");
    repository.findAll()
        .forEach(System.out::println);
    assertEquals(2, repository.findAll().size());

    // query
    repository.findByMarketCapGreaterThan(1500)
        .forEach(company -> System.out.println("Company with market cap > 1500B: " + company.getName()));

    // update
    Company getCompany = repository.findByName("Google").get(0);
    getCompany.setName("Alphabet");
    repository.save(getCompany);

    System.out.println("Companies after update:");
    repository.findAll()
        .forEach(System.out::println);

    // delete
    repository.deleteById(getCompany._id);

    System.out.println("Number of companies after delete: " + repository.findAll().size());
  }
}
