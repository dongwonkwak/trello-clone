package com.trelloclone.backend.adapter.out.persistence.adapter;

import com.trelloclone.backend.adapter.out.persistence.repository.AccountRepository;
import com.trelloclone.backend.domain.model.Account;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountPersistenceAdapterIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    private AccountPersistenceAdapter adapter;
    private UUID testId;
    private Account testAccount;

    @BeforeAll
    void setUp() {
        adapter = new AccountPersistenceAdapter(accountRepository);

        // Clean repository for tests
        accountRepository.deleteAll();
    }

    @BeforeEach
    void prepareTest() {
        // Create test account
        testAccount = Account.builder()
                .firstName("John")
                .lastName("Doe")
                .email("test@example.com")
                .password("password")
                .build();
    }

    @Test
    @DisplayName("Should save and retrieve account successfully")
    void shouldSaveAndRetrieveAccount() {
        //When saving account
        var savedResult = adapter.saveAccount(testAccount);

        // Then it should succeed
        assertThat(savedResult.isRight()).isTrue();

        // When retrieving by ID
        var id = savedResult.get().getId();
        var retrievedResult = adapter.findAccountById(id);

        // Then it should match
        assertThat(retrievedResult.isRight()).isTrue();
        assertThat(retrievedResult.get().getEmail()).isEqualTo("test@example.com");
    }
}
