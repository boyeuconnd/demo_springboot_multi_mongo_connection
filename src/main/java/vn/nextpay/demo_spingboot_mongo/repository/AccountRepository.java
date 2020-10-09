package vn.nextpay.demo_spingboot_mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.nextpay.demo_spingboot_mongo.model.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account,String> {
}
