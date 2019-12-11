package com.revolut.challenge.service;

import com.revolut.challenge.model.Account;
import com.revolut.challenge.model.Transaction;

import java.util.List;

/**
 * Created by Fazel on 12/11/2019.
 */
public interface Service {

    void saveOrUpdate(Account account);

    List<Account> loadAllAccounts();

    void transferMoney(Transaction transaction);
}