package com.bytes.accounts.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomerDto {

    private String name;

    private String email;

    private String mobileNumber;

    private AccountsDto accountsDto;

}
