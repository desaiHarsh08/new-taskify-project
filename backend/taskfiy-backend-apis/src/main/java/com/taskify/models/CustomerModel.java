package com.taskify.models;

import com.taskify.app.AppConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = AppConstants.CUSTOMER_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String customerIdTemp;

    private String customerName;

    private String customerContact;

    private String customerMobile;

    private String customerAddress;

    private String customerCity;

    private String customerPincode;

}
