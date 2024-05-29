package com.taskify.services.impl;

import com.taskify.models.CustomerModel;
import com.taskify.repositories.CustomerRepository;
import com.taskify.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServicesImpl implements CustomerServices  {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerModel createCustomer(CustomerModel customerModel) {
        return customerRepository.save(customerModel);
    }
}
