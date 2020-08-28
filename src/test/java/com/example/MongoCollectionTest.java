package com.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Updates.set;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MongoCollectionTest {

  private MongoCollection<Document> collection;

  @BeforeEach
  void before() {
    MongoClient mongoClient = MongoClients.create();
    MongoDatabase database = mongoClient.getDatabase("mydb");
    collection = database.getCollection("company");

    collection.deleteMany(new Document());
  }

  @Test
  void showcaseCrud() {
    // insert
    Company amazon = new Company("Amazon", "e-commerce, cloud", 1600);
    Company google = new Company("Google", "cloud, ads", 1010);
    Document amazonDoc = mapToDocument(amazon);
    Document googleDoc = mapToDocument(google);
    googleDoc.append("foundingYear", 1998);
    collection.insertOne(amazonDoc);
    collection.insertOne(googleDoc);

    // read: find all
    List<Document> readAll = new ArrayList<>();
    collection.find().into(readAll);
    System.out.println("All companies:");
    readAll.forEach(System.out::println);
    assertEquals(2, readAll.size());

    // inspect
    System.out.println("Check if " + readAll.get(0).get("name") + " contains key \"industrySector\":");
    System.out.println(inspectDocument(readAll.get(0), "industrySector"));
    System.out.println("Check if " + readAll.get(0).get("name") + " contains key \"fundingYear\":");
    System.out.println(inspectDocument(readAll.get(0), "foundingYear"));

    // query
    List<Document> largeComp = new ArrayList<>();
    collection.find(gt("marketCap", 1500)).into(largeComp);
    System.out.println("Company with market cap > 1500B:");
    largeComp.forEach(System.out::println);

    // update
    collection.updateOne(eq("name", "Google"), set("name", "Alphabet"));
    System.out.println("Companies after update:");
    readAll = new ArrayList<>();
    collection.find().into(readAll);
    readAll.forEach(System.out::println);

    // delete
    collection.deleteOne(eq("name", "Alphabet"));
    System.out.println("Number of companies after delete: " + collection.countDocuments());
  }

  private Document mapToDocument(Company company) {
    return new Document()
        .append("name", company.getName())
        .append("industrySector", company.getIndustrySector())
        .append("marketCap", company.getMarketCap());
  }

  private Optional<String> inspectDocument(Document document, String key) {
    return Optional.ofNullable(document.get(key).toString());
  }
}
