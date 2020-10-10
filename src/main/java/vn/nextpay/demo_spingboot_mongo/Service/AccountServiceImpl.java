package vn.nextpay.demo_spingboot_mongo.Service;

import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import vn.nextpay.demo_spingboot_mongo.model.Account;
import vn.nextpay.demo_spingboot_mongo.model.AccountEntry;
import vn.nextpay.demo_spingboot_mongo.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    @Qualifier("firstMongoTemplate")
    private MongoTemplate mongoTemplate1;

    @Autowired
    @Qualifier("secondMongoTemplate")
    private MongoTemplate mongoTemplate2;

    @Autowired
    private AccountEntryService accountEntryService;

    FindAndModifyOptions options = new FindAndModifyOptions();




    @Override
    public List<Account> getAllAccount() {
        Query query = new Query();
        query.limit(30);

//        List<Account> listAcc = accountRepository.findAll();
        List<Account> listAcc = this.mongoTemplate1.find(query,Account.class);
        if(listAcc == null){
            return null;

        }
        return listAcc;
    }

    @Override
    public Account findOne(ObjectId id) {
//        Query query = new Query(Criteria.where("id").is(id));
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Account account = mongoTemplate1.findOne(query,Account.class);
//        query.limit(15);
//        return accountRepository.findById(id).get();
        return account;
    }

    @Override
    public Account createOne(Account account) {
        if(account.getCurrency()==null||account.getOwner()==null
        ||account.getCreatedBy()==null){
            return null;
        }

        account.setIsArchived(true);
        account.setIsFrozen(false);
        account.setBalance("0");
        account.setMinBalance("0");
        account.setMaxBalance("10000000000000"); //10 tỷ
        account.setOnlyVisibleToCreatorApp(false);

        Account newAccount = mongoTemplate1.save(account);

        AccountEntry firstEntry = accountEntryService.firstEntry(newAccount.getId());

        if(firstEntry != null){
            return recoverArchive(newAccount.getId());
        }

        return null;
//        return accountRepository.save(account);
    }

    @Override
    public Account updateOne(Account account, ObjectId id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();

        update.set("title",account.getTitle());

        if(account.getMinBalance() == null || account.getMinBalance() == ""){
            update.set("minBalance","0");
        }else {
            update.set("minBalance",account.getMinBalance());
        }

        if(account.getMaxBalance() == null || account.getMaxBalance() ==""){
            update.set("maxBalance",String.valueOf(Long.MAX_VALUE));
        }else {
            update.set("maxBalance",account.getMaxBalance());
        }

        Account updatedAccount = mongoTemplate1.findAndModify(query,update,options.returnNew(true),Account.class);


        if(updatedAccount == null){
            return null;
//            return result.getModifiedCount();

        }

        return updatedAccount;

    }

    @Override
    public Account deteleOne(ObjectId id) {
        Account existAcc = this.findOne(id);

        if(existAcc == null){
            return null;
        }

        mongoTemplate1.remove(existAcc);

        return existAcc;
    }

    @Override
    public Account recoverArchive(ObjectId id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();

        update.set("isArchived",false);

        try {

            Account recoverdAcc = mongoTemplate1.findAndModify(query,update,options.returnNew(true),Account.class);
            if(recoverdAcc != null) return recoverdAcc;
        }catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public Account archiveAccount(ObjectId id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();

        update.set("isArchived",true);

        try {
            Account archiveAccount = mongoTemplate1.findAndModify(query,update,options.returnNew(true),Account.class);
            if(archiveAccount != null) return  archiveAccount;
        }catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public List<Account> showArchived() {
        Query query = new Query(Criteria.where("isArchived").is(true));
        List<Account> listArchivedAccount = mongoTemplate1.find(query,Account.class);
        if(listArchivedAccount.isEmpty()) return new ArrayList<>();
        return listArchivedAccount;
    }
}
