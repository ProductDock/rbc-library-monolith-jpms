package com.productdock.library.inventory.config.mongodb;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.productdock.library.inventory.adapter.out.mongo",
        mongoTemplateRef = InventoryMongoConfig.MONGO_TEMPLATE)
public class InventoryMongoConfig {

    protected static final String MONGO_TEMPLATE = "inventoryMongoTemplate";

    @Bean(name = "inventoryDbProperties")
    @ConfigurationProperties(prefix = "mongodb.inventory")
    public MongoProperties getInventoryProperties() {
        return new MongoProperties();
    }

    @Bean(name = MONGO_TEMPLATE)
    public MongoTemplate inventoryMongoTemplate() {
        return new MongoTemplate(inventoryDbFactory(getInventoryProperties()));
    }

    @Bean
    public MongoDatabaseFactory inventoryDbFactory(final MongoProperties mongo) {
        return new SimpleMongoClientDatabaseFactory(mongo.getUri());
    }

}
