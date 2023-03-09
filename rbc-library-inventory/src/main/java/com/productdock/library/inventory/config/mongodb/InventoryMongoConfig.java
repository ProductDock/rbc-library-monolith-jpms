package com.productdock.library.inventory.config.mongodb;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
@RequiredArgsConstructor
public class InventoryMongoConfig {

    @Bean(name = "inventoryDbProperties")
    @ConfigurationProperties(prefix = "mongodb.inventory")
    public MongoProperties getInventoryProperties() {
        return new MongoProperties();
    }

    @Bean(name = InventoryDbMongoConfig.MONGO_TEMPLATE)
    public MongoTemplate inventoryMongoTemplate() {
        return new MongoTemplate(secondaryFactory(getInventoryProperties()));
    }

    @Bean
    public MongoDatabaseFactory secondaryFactory(final MongoProperties mongo) {
        return new SimpleMongoClientDatabaseFactory(mongo.getUri());
    }

}
