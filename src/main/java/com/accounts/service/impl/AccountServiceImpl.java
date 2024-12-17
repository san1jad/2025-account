package com.accounts.service.impl;

import com.accounts.constants.AccountConstants;
import com.accounts.dto.AccountDTO;
import com.accounts.dto.CustomerDTO;
import com.accounts.entity.Accounts;
import com.accounts.entity.Customer;
import com.accounts.exception.CustomerAlreadyPresentException;
import com.accounts.exception.ResourceNotFoundException;
import com.accounts.mapper.AccountMapper;
import com.accounts.mapper.CustomerMapper;
import com.accounts.repository.AccountRepository;
import com.accounts.repository.CustomerRepository;
import com.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        customerRepository.findByMobileNumber(customer.getMobileNumber())
                .ifPresent(ex -> {
                    throw new CustomerAlreadyPresentException("Customer already present with given mobile number: " + customerDTO.getMobileNumber());
                });
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDTO fetchAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));
        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDto(customer, new CustomerDTO());
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Accounts", "Customer Id", String.valueOf(customer.getCustomerId())));
        customerDTO.setAccountDTO(AccountMapper.mapToAccountsDto(accounts,new AccountDTO()));
        return customerDTO;
    }

    @Override
    public boolean updateAccountDetails(CustomerDTO customerDTO) {
       return Optional.ofNullable(customerDTO)
                .map(CustomerDTO::getMobileNumber)
                .map(mobileNumber -> updateCustomerDetails(customerDTO)).orElse(false);
    }

    @Override
    public List<CustomerDTO> fetchAllAccountsDetails() {
        return Optional.of(customerRepository.findAll())
                .filter(isListEmpty ->!isListEmpty.isEmpty())
                .map(customers -> customers.stream().map(customer ->
                        CustomerMapper.mapToCustomerAndAccountDto(customer, getAccountDtoByCustomerId(customer)))
                        .collect(Collectors.toList())).orElse(new ArrayList<>());
    }

    @Override
    public void deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));
        customerRepository.delete(customer);
        accountRepository.deleteByCustomerId(customer.getCustomerId());
    }

    private boolean updateCustomerDetails(CustomerDTO customerDTO) {
        Accounts accounts = accountRepository.findById(customerDTO.getAccountDTO().getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Accounts", "Account number", String.valueOf(customerDTO.getAccountDTO().getAccountNumber())));
        accountRepository.save(AccountMapper.mapToAccounts(customerDTO.getAccountDTO(), accounts));
        Customer customer = customerRepository.findById(accounts.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Customer Id", String.valueOf(accounts.getCustomerId())));
        customerRepository.save(CustomerMapper.mapToCustomer(customerDTO, customer));
        return true;
    }

    private AccountDTO getAccountDtoByCustomerId(Customer customer){
        return AccountMapper.mapToAccountsDto(accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Accounts", "Customer Id", String.valueOf(customer.getCustomerId()))),new AccountDTO());
    }

}
