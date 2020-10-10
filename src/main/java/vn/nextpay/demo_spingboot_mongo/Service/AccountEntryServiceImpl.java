package vn.nextpay.demo_spingboot_mongo.Service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import vn.nextpay.demo_spingboot_mongo.model.Account;
import vn.nextpay.demo_spingboot_mongo.model.AccountEntry;
import vn.nextpay.demo_spingboot_mongo.model.input.CreateEntryInput;
import vn.nextpay.demo_spingboot_mongo.model.input.RecordEntryInput;

import java.util.List;

@Service
public class AccountEntryServiceImpl implements AccountEntryService {

    @Autowired
    @Qualifier("firstMongoTemplate")
    private MongoTemplate mongoTemplate1;

    @Autowired
    @Qualifier("secondMongoTemplate")
    private MongoTemplate mongoTemplate2;

    @Override
    public List<AccountEntry> searchEntry() {
        return null;
    }

    @Override
    public AccountEntry recordEntry(RecordEntryInput input) {
        Query query = new Query(Criteria.where("_id").is(input.getAccountId()));
        query.limit(1);
        Account ownerAccount = mongoTemplate1.findOne(query,Account.class);

        if(ownerAccount.getIsArchived()||ownerAccount.getIsFrozen()){
            return null;
        }

        Query entryQuery = new Query(Criteria.where("accountId").is(input.getAccountId()));
        entryQuery.with(Sort.by(Sort.Direction.DESC,"_id"));
        AccountEntry lastEntry = mongoTemplate2.findOne(entryQuery,AccountEntry.class);


        AccountEntry newEntry = new AccountEntry();

        Long beforeBlance = Long.parseLong(lastEntry.getAfterBalance());
        Long amount = Long.parseLong(input.getAmount());
        if(beforeBlance+amount > Long.parseLong(ownerAccount.getMaxBalance())&&
        beforeBlance+amount < Long.parseLong(ownerAccount.getMinBalance())){
            return null;
        }

        newEntry.setAccountId(lastEntry.getAccountId());
        newEntry.setBeforeBalance(beforeBlance.toString());
        newEntry.setAmount(input.getAmount());
        newEntry.setAfterBalance( String.valueOf(beforeBlance+amount));
        newEntry.setEntryCounter(lastEntry.getEntryCounter()+1);

        AccountEntry response = mongoTemplate2.save(newEntry);

        if(response != null){
            Update update = new Update();
            update.set("balance",response.getAfterBalance());
            

            mongoTemplate1.findAndModify(query,update,Account.class);

        }


        return response;
    }

    @Override
    public AccountEntry createEntryToSetBalance(CreateEntryInput input) {
        return null;
    }

    @Override
    public AccountEntry firstEntry(ObjectId accountId) {
        AccountEntry firstEntry = new AccountEntry();

        firstEntry.setAccountId(accountId);
        firstEntry.setBeforeBalance(String.valueOf(0));
        firstEntry.setAmount(String.valueOf(0));
        firstEntry.setAfterBalance(String.valueOf(0));
        firstEntry.setEntryCounter(0L);

        return mongoTemplate2.save(firstEntry);
    }
}
