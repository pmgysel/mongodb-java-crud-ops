# MongoDB: Demo Queries (from Java + MongoDB Shell)

## Prerequisites

* Java
* Maven
* MongoDB (Server + MongoDB Shell client)

## MongoDB Shell Queries

```sh
> show databases
> use mydb
> db.company.insertOne({name:"Amazon", marketCap:1600})
> db.company.find({marketCap:{$gt:1500}})
> db.company.updateOne({name:"Amazon"},{$set:{name:"Amazon Inc"}})
> db.company.deleteOne({name:"Amazon"})
```

## SpringBoot Queries (Type-safe)

Access database using a fix document type. 

First, define the data model as POJO:

```java
public class Company {
  @Id
  public String _id;
  private String name;
  // other members...
}
```

SpringBoot's [MongoRepository](https://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/repository/MongoRepository.html)
allows to generate CRUD queries for you:

```java
public interface CompanyRepository extends MongoRepository<Company, String> {}
```

MongoRepository offers a variety of query methods like
`eq`, `save`, `findAll`, `findById`, `delete`.

Search for all existing companies:

```java
@Autowired
CompanyRepository repository;
repository.findAll();
```

## Java Queries (Generic Documents)

Access database in a generic fashion (no fixed data schema).

Java offers the low-level [MongoCollection](https://api.mongodb.com/java/3.0/?com/mongodb/client/MongoCollection.html)
interface which works with arbitrary documents. Each document
we read and write can have any number of fields.

Initialize MongoCollection:

```java
MongoClient client = MongoClients.create();
MongoDatabase db = client.getDatabase("mydb");
MongoCollection<Document> collection = db.getCollection("company");
```

Persist document:

```java
Document google = new Document()
    .append("name", "Google")
    .append("marketCap", 1010);
collection.insertOne(google);
```

Query document and inspect it:

```java
List<Document> google = new ArrayList<>();
collection.find(eq("name", "Google")).into(google);
google.get(0).get("marketCap");
```