package com.accounts.mapper;

import com.accounts.dto.AccountDTO;
import com.accounts.dto.CustomerDTO;
import com.accounts.dto.CustomerDetailsDTO;
import com.accounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDTO mapToCustomerDto(Customer customer, CustomerDTO customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static CustomerDetailsDTO mapToCustomerDetailsDto(Customer customer, CustomerDetailsDTO customerDetailsDTO) {
        customerDetailsDTO.setName(customer.getName());
        customerDetailsDTO.setEmail(customer.getEmail());
        customerDetailsDTO.setMobileNumber(customer.getMobileNumber());
        return customerDetailsDTO;
    }

    public static Customer mapToCustomer(CustomerDTO customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

    public static CustomerDTO mapToCustomerAndAccountDto(Customer customer, AccountDTO accountDTO) {
        CustomerDTO customerDTO = mapToCustomerDto(customer, new CustomerDTO());
        customerDTO.setAccountDTO(accountDTO);
        return customerDTO;
    }


}