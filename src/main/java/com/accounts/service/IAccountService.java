package com.accounts.service;

import com.accounts.dto.CustomerDTO;

import java.util.List;

public interface IAccountService {
    void createAccount(CustomerDTO customerDTO);

    CustomerDTO fetchAccountDetails(String mobileNumber);

    boolean updateAccountDetails(CustomerDTO customerDTO);

    List<CustomerDTO> fetchAllAccountsDetails();

   void deleteAccount(String mobileNumber);

}