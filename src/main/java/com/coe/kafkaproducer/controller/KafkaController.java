package com.coe.kafkaproducer.controller;

import com.coe.kafkaproducer.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer/produce")
public class KafkaController {
    @Autowired
    private KafkaTemplate<String, Customer> kafkaUserTemplate;
    @Autowired
    private KafkaTemplate<String, Integer> kafkaIdCustomerTemplate;

    @PostMapping("")
    public String saveCustomer(@RequestBody Customer customer) {
        kafkaUserTemplate.send("customer-save-topic", customer);
        return "Customer published successfully";
    }

    @PutMapping("")
    public String updateCustomer(@RequestBody Customer customer) {
        if (customer.getId() <= 0)
            return "Customer Id cannot be null or 0";
        kafkaUserTemplate.send("customer-update-topic", customer);
        return "Customer upload published successfully";
    }

    @DeleteMapping("/{idCustomer}")
    public String deleteCustomer(@PathVariable("idCustomer") int idCustomer) {
        kafkaIdCustomerTemplate.send("customer-delete-topic", idCustomer);
        return "Customer delete published successfully";
    }
}
