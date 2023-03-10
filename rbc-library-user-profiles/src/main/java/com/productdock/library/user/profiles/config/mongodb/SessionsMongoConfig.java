package com.productdock.library.user.profiles.config.mongodb;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "org.springframework.session.data.mongo",
        mongoTemplateRef = SessionsMongoConfig.MONGO_TEMPLATE)
public class SessionsMongoConfig {

    protected static final String MONGO_TEMPLATE = "sessionsMongoTemplate";

    @Primary
    @Bean(name = "sessionsDbProperties")
    @ConfigurationProperties(prefix = "mongodb.sessions")
    public MongoProperties getSessionsProperties() {
        return new MongoProperties();
    }

    @Primary
    @Bean(name = MONGO_TEMPLATE)
    public MongoTemplate sessionsMongoTemplate() {
        return new MongoTemplate(sessionsDbFactory(getSessionsProperties()));
    }

    @Bean
    @Primary
    public MongoDatabaseFactory sessionsDbFactory(final MongoProperties mongo) {
        return new SimpleMongoClientDatabaseFactory(mongo.getUri());
    }

}
