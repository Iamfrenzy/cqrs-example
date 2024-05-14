package com.example.cqrs;

import com.example.cqrs.AccountViewController;
import com.example.cqrs.api.controller.TransactionController;
import com.example.cqrs.api.domain.TransactionRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberSteps {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> responseEntity;

    private UUID accountId;

    private BigDecimal amount;

    @Given("an account with id {string}")
    public void anAccountWithId(String accountId) {
        this.accountId = UUID.fromString(accountId);
    }

    @Given("a deposit request with amount {string}")
    public void aDepositRequestWithAmount(String amount) {
        this.amount = new BigDecimal(amount);
    }

    @When("the deposit endpoint is called")
    public void theDepositEndpointIsCalled() {
        TransactionRequest request = new TransactionRequest();
        request.setAmount(amount);
        responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/transactions/deposit/{accountId}", request, String.class, accountId);
    }

    @Then("the deposit is successful")
    public void theDepositIsSuccessful() {
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }

    @Given("a withdrawal request with amount {string}")
    public void aWithdrawalRequestWithAmount(String amount) {
        this.amount = new BigDecimal(amount);
    }

    @When("the withdrawal endpoint is called")
    public void theWithdrawalEndpointIsCalled() {
        TransactionRequest request = new TransactionRequest();
        request.setAmount(amount);
        responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/transactions/withdraw/{accountId}", request, String.class, accountId);
    }

    @Then("the withdrawal is successful")
    public void theWithdrawalIsSuccessful() {
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }

    @Then("the balance of the account is {string}")
    public void theBalanceOfTheAccountIs(String expectedBalance) {
        ResponseEntity<String> balanceResponse = restTemplate.getForEntity("http://localhost:" + port + "/accounts/balance/{accountId}", String.class, accountId);
        assertEquals(HttpStatus.OK, balanceResponse.getStatusCode());
        assertEquals(expectedBalance, balanceResponse.getBody());
    }
}

