package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

public class CustomChangeSet implements CustomTaskChange {

    @Autowired
    private TransactionTemplate txTemplateDefault;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public String getConfirmationMessage() {
        return null;
    }

    @Override
    public void setUp() {
        AutowireHelper.autowire(this, itemRepository);
    }

    @Override
    public void setFileOpener(ResourceAccessor resourceAccessor) {
    }

    @Override
    public ValidationErrors validate(Database database) {
        return null;
    }

    @Override
    public void execute(Database database) throws CustomChangeException {
        txTemplateDefault.execute(new TransactionCallbackWithoutResult() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                itemRepository.save(new Item("myitem"));
            }
        });
    }

}
