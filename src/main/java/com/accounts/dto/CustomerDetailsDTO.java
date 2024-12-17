package com.accounts.dto;

import com.accounts.dto.cards.CardsDto;
import com.accounts.dto.loans.LoansDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold customer,account and loan information"
)
public class CustomerDetailsDTO {

    @Schema(description = "Name of the customer")
    private String name;

    @Schema(description = "Email address of the customer")
    private String email;

    @Schema(description = "Mobile Number of the customer")
    private String mobileNumber;

    @Schema(description = "Accounts details of customer")
    private AccountDTO accountDTO;

    @Schema(description = "Loans details of customer")
    private LoansDto loansDto;

    @Schema(description = "Card details of customer")
    private CardsDto cardsDto;
}
