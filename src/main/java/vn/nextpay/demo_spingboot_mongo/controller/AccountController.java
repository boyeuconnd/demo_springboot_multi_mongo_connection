package vn.nextpay.demo_spingboot_mongo.controller;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.nextpay.demo_spingboot_mongo.Service.AccountService;
import vn.nextpay.demo_spingboot_mongo.model.Account;

import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @GetMapping
    public List<Account> getAllAccount(){
        return accountService.getAllAccount();
    }

    @PostMapping
    public Account createOne(@RequestBody Account newAcc){
        return accountService.createOne(newAcc);
    }

    @GetMapping("/{id}")
    public Account getOneAccount(@PathVariable ObjectId id){
        return accountService.findOne(id);
    }

    @PutMapping("/{id}")
    public Account updateOne(@RequestBody Account updatedAcc, @PathVariable ObjectId id){
        return accountService.updateOne(updatedAcc,id);
    }

    @DeleteMapping("/{id}")
    public Account deleteOne(@PathVariable ObjectId id){
        return accountService.deteleOne(id);
    }

    @PostMapping("/recover/{id}")
    public Account recoverArchive(@PathVariable ObjectId id){
        return accountService.recoverArchive(id);
    }

    @GetMapping("/archived")
    public List<Account> showArchived(){
        return accountService.showArchived();
    }

    @PostMapping("/archive/{id}")
    public Account archiveAccount(@PathVariable ObjectId id){
        return accountService.archiveAccount(id);
    }
}
