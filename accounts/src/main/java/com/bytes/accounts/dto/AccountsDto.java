package com.bytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class AccountsDto{

    @Schema(description = "Account Number of Bank account", example = "3454433243")
    @NotEmpty(message = "AccountNumber can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    private Long accountNumber;

    @Schema(description = "Account type of Bytes Bank account", example = "Savings")
    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;

    @Schema(description = "Bytes Bank branch address", example = "123 NewYork")
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    private String branchAddress;

}
