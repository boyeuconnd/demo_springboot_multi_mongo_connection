package vn.nextpay.demo_spingboot_mongo.config;

import com.mongodb.ConnectionString;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Data
@Configuration
public class MultipleMongoProperties {
    @Bean
    @Qualifier("firstMongodbFactory")
    public MongoDatabaseFactory firstMongodbFactory() {
        String uri = "mongodb://root:ngocMai1@localhost:8899/test?authSource=admin";
        return new SimpleMongoClientDatabaseFactory(new ConnectionString(uri));
    }

    @Bean
    @Qualifier("secondMongodbFactory")
    public MongoDatabaseFactory secondMongodbFactory(){
        String uri = "mongodb://root:ngocMai1@localhost:8899/test2?authSource=admin";
        return new SimpleMongoClientDatabaseFactory(new ConnectionString(uri));
    }


    @Bean
    @Qualifier("firstMongoTemplate")
    public MongoTemplate mongoTemplate(){
        MappingMongoConverter converter = primaryMappingMongoConverter(firstMongodbFactory());
        return new MongoTemplate(firstMongodbFactory(),converter);
    }

    @Bean
    @Qualifier("secondMongoTemplate")
    public MongoTemplate mongoTemplate2(){
        MappingMongoConverter converter = secondaryMappingMongoConverter(secondMongodbFactory());
        return new MongoTemplate(secondMongodbFactory(),converter);
    }


    @Bean
    public MappingMongoConverter secondaryMappingMongoConverter(@Qualifier("firstMongodbFactory")  MongoDatabaseFactory mongoDbFactory) {
        MappingMongoConverter converter = new MappingMongoConverter(
                new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
//        converter.setCustomConversions(customConversions());
        converter.afterPropertiesSet();

        return converter;
    }

    @Bean
    public MappingMongoConverter primaryMappingMongoConverter( @Qualifier("secondMongodbFactory") MongoDatabaseFactory mongoDbFactory) {
        MappingMongoConverter converter = new MappingMongoConverter(
                new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
//        converter.setCustomConversions(customConversions());
        converter.afterPropertiesSet();

        return converter;
    }

}
