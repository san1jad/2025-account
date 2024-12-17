package com.accounts.service.impl;

import com.accounts.dto.AccountDTO;
import com.accounts.dto.CustomerDetailsDTO;
import com.accounts.entity.Accounts;
import com.accounts.entity.Customer;
import com.accounts.exception.ResourceNotFoundException;
import com.accounts.mapper.AccountMapper;
import com.accounts.mapper.CustomerMapper;
import com.accounts.repository.AccountRepository;
import com.accounts.repository.CustomerRepository;
import com.accounts.service.ICustomerService;
import com.accounts.service.feignclient.CardsFeignClient;
import com.accounts.service.feignclient.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDTO fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));
        CustomerDetailsDTO customerDetailsDTO = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDTO());
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Accounts", "Customer Id", String.valueOf(customer.getCustomerId())));
        customerDetailsDTO.setAccountDTO(AccountMapper.mapToAccountsDto(accounts, new AccountDTO()));

        Optional.ofNullable(loansFeignClient.fetchLoanDetails(mobileNumber))
                .map(HttpEntity::getBody)
                .ifPresent(customerDetailsDTO::setLoansDto);

        Optional.ofNullable(cardsFeignClient.fetchCardDetails(mobileNumber))
                .map(HttpEntity::getBody)
                .ifPresent(customerDetailsDTO::setCardsDto);

        return customerDetailsDTO;
    }
}
