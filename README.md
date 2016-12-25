Routing DataSource
==================

## Why
* An utility frame work for Data base shard.

    
## How
### Maven Dependency
```xml
    <dependency>
        <groupId>com.uimirror.framework</groupId>
        <artifactId>routing-datasource</artifactId>
        <version>1.0.1</version>
    </dependency>
```

### Gradle Dependency

`com.uimirror.framework:routing-datasource:1.0.1`


### Features

- Supports Database Sharding
- Supports Unified data source configuration from the config/json file.
- Supports sharding based on master slave or db sequence number from the context

#### Sample code:

- Sample Json Format for the data base configuration

```json
{
  "key_family":"default",
  "con_details": [
    {
      "name":"common",
      "key_family":"specific",
      "is_shard": false,
      "connection":{
        "url":"jdbc:mysql://localhost/test_multi?relaxAutoCommit=true",
        "user_name":"root",
        "password":"purpose",
        "config":{
          "max_active":1,
          "min_active":1,
          "min_idle":1,
          "max_idle":1,
          "validation_query":"SELECT 1",
          "default_auto_commit":false
        }
      }
    },
    {
      "name":"user",
      "is_shard": true,
      "key_family":"specific",
      "shard_details":[
        {
          "id":1,
          "key_family":"specific",
          "is_default":true,
          "connection":{
            "url":"jdbc:mysql://localhost/test_multi?relaxAutoCommit=true",
            "user_name":"root",
            "password":"purpose"
          }
        },
        {
          "id":2,
          "connection":{
            "url":"jdbc:mysql://localhost/test_multi?relaxAutoCommit=true",
            "user_name":"root",
            "password":"purpose"
          }
        },
        {
          "id":2,
          "connection":{
            "url":"jdbc:mysql://localhost/test_multi?relaxAutoCommit=true",
            "user_name":"root",
            "password":"purpose"
          }
        }
      ]
    },
    {
      "name":"user_lookup",
      "is_shard": true,
      "shard_details":[
        {
          "id":1,
          "key_family":"specific",
          "is_default":true,
          "connection":{
            "url":"jdbc:mysql://localhost/test_multi?relaxAutoCommit=true",
            "user_name":"root",
            "password":"purpose"
          }
        },
        {
          "id":2,
          "connection":{
            "url":"jdbc:mysql://localhost/test_multi?relaxAutoCommit=true",
            "user_name":"root",
            "password":"purpose"
          }
        }
      ]
    },
    {
      "name":"static",
      "is_shard": true,
      "shard_type" : "MASTER_SLAVE",
      "shard_details":[
        {
          "id":1,
          "key_family":"specific",
          "is_default":true,
          "type":"MASTER",
          "connection":{
            "url":"jdbc:mysql://localhost/test_multi?relaxAutoCommit=true",
            "user_name":"root",
            "password":"purpose"
          }
        },
        {
          "id":2,
          "type":"SLAVE",
          "connection":{
            "url":"jdbc:mysql://localhost/test_multi?relaxAutoCommit=true",
            "user_name":"root",
            "password":"purpose"
          }
        }
      ]
    }
  ]
}
```
Here each connection can have its own config as shown in the first entry.


- Sample Code when using multi shard db. 

```java

    //Use Case -1 When you want to use json based configuration for the shard and non shard data base follow the below 
    //steps in your bean defination:
    
    //Step 1- Add A Bean defination for the DBDetailParser 
    public DBDetailParser dbDetailParser(){
        return new DBDetailParser();
    }
    
    //Step 2- Add A Bean Defination for the Configurator, shardPrefix for the key detection and keySeparator for the key family, 
    //db and name separator
    public DBDetailConfigurator dbDetailConfigurator(String shardPrefix, String keySeparator){
         return new DBDetailConfigurator(shardPrefix, keySeparator);
    }
    
    Step 3- Add A Bean Defination to Load the Config File and Parse to a DBDetail 
    public DBDetail dbDetail(String configLoc, String shardPrefix, String keySeparator){
         return new DBDetailConfigurator(shardPrefix, keySeparator).configure(dbDetailParser().parse(configLoc));
    }
    or 
    Step 3- Add A Bean Defination to parse the exisiting DBDetail 
    public DBDetail dbDetail(String shardPrefix, String keySeparator, DBDetail dbDetail){
         return new DBDetailConfigurator(shardPrefix, keySeparator).configure(dbDetail);
    }
    
    //Now Use the bean DBDetail for the data source defination

```

#### Developer Guide
* Make Sure you are on Maven 3.1 +
* Clone the repo
* Navigate to main directory and run `mvn clean install`
* In case changing the project version do change in the parent project `<currentVersion>0.1-SNAPSHOT</currentVersion>`
 
Navigate to code coverage folder to see current coverage status

##### Simple Steps for a complete release process
* ```git clone```
* ```mvn versions:set```
* ```mvn deploy```
* ```mvn scm:tag```

* `mvn source:jar` For Generating Source
* `mvn javadoc:jar` For generating Java doc

*** Please note before any push or merge request you run the test cases at least once.
