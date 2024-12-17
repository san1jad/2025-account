package com.accounts.service;

import com.accounts.dto.CustomerDetailsDTO;

public interface ICustomerService {
    CustomerDetailsDTO fetchCustomerDetails(String mobileNumber);
}
