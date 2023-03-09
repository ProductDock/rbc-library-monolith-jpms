package com.productdock.library.inventory.config.mongodb;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.productdock.library.inventory.adapter.out.mongo",
        mongoTemplateRef = InventoryDbMongoConfig.MONGO_TEMPLATE)
public class InventoryDbMongoConfig {

    protected static final String MONGO_TEMPLATE = "inventoryMongoTemplate";

}
