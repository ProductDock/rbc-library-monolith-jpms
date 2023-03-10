package com.productdock.library.search;

import org.springframework.boot.SpringApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

//@SpringBootApplication
@EnableElasticsearchRepositories
public class MicroserviceSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceSearchApplication.class, args);
    }

}
