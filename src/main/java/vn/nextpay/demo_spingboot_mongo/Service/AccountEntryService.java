package vn.nextpay.demo_spingboot_mongo.Service;


import org.bson.types.ObjectId;
import vn.nextpay.demo_spingboot_mongo.model.Account;
import vn.nextpay.demo_spingboot_mongo.model.AccountEntry;
import vn.nextpay.demo_spingboot_mongo.model.input.CreateEntryInput;
import vn.nextpay.demo_spingboot_mongo.model.input.RecordEntryInput;

import java.util.List;

public interface AccountEntryService {
    List<AccountEntry> searchEntry();

    AccountEntry recordEntry(RecordEntryInput input);

    AccountEntry createEntryToSetBalance(CreateEntryInput input);

    AccountEntry firstEntry(ObjectId accountId);


}
