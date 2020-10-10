package vn.nextpay.demo_spingboot_mongo.Service;

import org.bson.types.ObjectId;
import vn.nextpay.demo_spingboot_mongo.model.Account;

import java.util.List;

public interface AccountService {


    List<Account> getAllAccount ();

    Account findOne (ObjectId id);

    Account createOne (Account account);

    Account updateOne (Account account,ObjectId id);

    Account deteleOne (ObjectId id);

    Account recoverArchive (ObjectId id);

    Account archiveAccount (ObjectId id);

    List<Account> showArchived();
}
