package com.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountDTO {
    //account
    @NotEmpty(message = "Account number should not be empty or null")
    @Pattern(regexp = "(^[0-9]{10}$)", message = "Account number should be 10 digit")
    @Schema(description = "Account Number of Bank account", example = "3454433243")
    private Long accountNumber;

    @NotEmpty(message = "Account type should not be empty or null")
    @Schema(description = "Account type of Bank account", example = "Savings")
    private String accountType;

    @NotEmpty(message = "Branch address should not be empty or null")
    @Schema(description = "Bank branch address", example = "123 NewYork")
    private String branchAddress;
}
