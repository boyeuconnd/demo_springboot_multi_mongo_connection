package vn.nextpay.demo_spingboot_mongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.nextpay.demo_spingboot_mongo.Service.AccountEntryService;
import vn.nextpay.demo_spingboot_mongo.model.AccountEntry;
import vn.nextpay.demo_spingboot_mongo.model.input.RecordEntryInput;

@RestController
@RequestMapping("entries")
public class AccountEntryController {

    @Autowired
    AccountEntryService accountEntryService;

    @PostMapping("/record")
    public AccountEntry recordEntry(@RequestBody RecordEntryInput input){
        return accountEntryService.recordEntry(input);

    }
}
