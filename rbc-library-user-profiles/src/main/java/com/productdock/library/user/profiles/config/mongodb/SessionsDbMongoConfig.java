package com.productdock.library.user.profiles.config.mongodb;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "org.springframework.session.data.mongo",
        mongoTemplateRef = SessionsDbMongoConfig.MONGO_TEMPLATE)
public class SessionsDbMongoConfig {

    protected static final String MONGO_TEMPLATE = "sessionsMongoTemplate";

}
