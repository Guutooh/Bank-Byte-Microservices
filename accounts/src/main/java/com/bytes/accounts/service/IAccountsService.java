package com.bytes.accounts.service;

import com.bytes.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * Create a new account and return the account details.
     *
     * @param customerDto
     *            Customer details to create the account
     */
    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);
}
