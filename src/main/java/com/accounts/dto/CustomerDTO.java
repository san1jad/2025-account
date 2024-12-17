package com.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDTO {
    //customer
    @NotEmpty(message = "Name should not be empty or null")
    @Size(min = 5, max = 30, message = "The name of customer length should be in between 5 to 30")
    @Schema(description = "Name of the customer", example = "SXXX JXX")
    private String name;

    @NotEmpty(message = "Email should not be empty or null")
    @Email(message = "Email should be in valid format")
    @Schema(description = "Email address of the customer", example = "XXX@XXX.com")
    private String email;

    @NotEmpty(message = "mobile number should not be empty or null")
    @Pattern(regexp = "(^[0-9]{10}$)", message = "mobile number should be 10 digit")
    @Schema(description = "Mobile Number of the customer", example = "9345432123")
    private String mobileNumber;

    @Schema(description = "Account details of the Customer")
    private AccountDTO accountDTO;
}
