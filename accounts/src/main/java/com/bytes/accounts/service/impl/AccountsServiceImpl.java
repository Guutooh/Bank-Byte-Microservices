package com.bytes.accounts.service.impl;

import com.bytes.accounts.Entity.Accounts;
import com.bytes.accounts.Entity.Customer;
import com.bytes.accounts.constants.AccountsConstants;
import com.bytes.accounts.dto.AccountsDto;
import com.bytes.accounts.dto.CustomerDto;
import com.bytes.accounts.exceptions.CustomerAlreadyExistsExceptions;
import com.bytes.accounts.exceptions.ResourceNotFoundException;
import com.bytes.accounts.mapper.AccountsMapper;
import com.bytes.accounts.mapper.CustomerMapper;
import com.bytes.accounts.repository.AccountsRepository;
import com.bytes.accounts.repository.CustomerRepository;
import com.bytes.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsExceptions("Customer already registered with this mobile number: " + customerDto.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);

        accountsRepository.save(createNewAccount(savedCustomer));
    }


    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());

        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated = false;

        AccountsDto accountsDto = customerDto.getAccountsDto();

        if(accountsDto !=null ){

            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );

            AccountsMapper.mapToAccounts(accountsDto, accounts);

            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();

            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );

            CustomerMapper.mapToCustomer(customerDto,customer);

            customerRepository.save(customer);

            isUpdated = true;
        }

        return  isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return  true;
    }


    private Accounts createNewAccount(Customer customer) {

        Accounts newAccounts = new Accounts();

        newAccounts.setCustomerId(customer.getCustomerId());

        long randomAccNumber = 1000000000L + new Random().nextLong(900000000);

        newAccounts.setAccountNumber(randomAccNumber);
        newAccounts.setAccountType(AccountsConstants.SAVINGS);
        newAccounts.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccounts;
    }


}
