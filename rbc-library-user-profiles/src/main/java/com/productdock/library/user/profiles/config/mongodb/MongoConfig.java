package com.productdock.library.user.profiles.config.mongodb;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
@RequiredArgsConstructor
public class MongoConfig {

    @Primary
    @Bean(name = "sessionsDbProperties")
    @ConfigurationProperties(prefix = "mongodb.sessions")
    public MongoProperties getSessionsProperties() {
        return new MongoProperties();
    }

    @Primary
    @Bean(name = SessionsDbMongoConfig.MONGO_TEMPLATE)
    public MongoTemplate sessionsMongoTemplate() {
        return new MongoTemplate(primaryFactory(getSessionsProperties()));
    }

    @Bean
    @Primary
    public MongoDatabaseFactory primaryFactory(final MongoProperties mongo) {
        return new SimpleMongoClientDatabaseFactory(mongo.getUri());
    }

    @Bean
    public MongoDatabaseFactory secondaryFactory(final MongoProperties mongo) {
        return new SimpleMongoClientDatabaseFactory(mongo.getUri());
    }

}
