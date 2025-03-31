package com.trelloclone.backend.adapter.out.persistence.adapter;

import com.trelloclone.backend.adapter.out.persistence.entity.AccountEntity;
import com.trelloclone.backend.adapter.out.persistence.mapper.AccountPersistenceMapper;
import com.trelloclone.backend.adapter.out.persistence.repository.AccountRepository;
import com.trelloclone.backend.adapter.out.persistence.repository.BoardMemberRepository;
import com.trelloclone.backend.adapter.out.persistence.repository.BoardRepository;
import com.trelloclone.backend.domain.model.Account;
import com.trelloclone.backend.domain.model.Board;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BoardPersistenceAdapterIntegrationTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardMemberRepository boardMemberRepository;

    @Autowired
    private AccountRepository accountRepository;

    private BoardPersistenceAdapter adapter;
    private Account testAccount;
    private AccountEntity testAccountEntity;
    private Board testBoard;

    @BeforeAll
    void setUp() {
        adapter = new BoardPersistenceAdapter(boardRepository, boardMemberRepository);

        // 테스트 전 리포지토리 정리
        boardMemberRepository.deleteAll();
        boardRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @BeforeEach
    void prepareTest() {
        // 테스트용 계정 생성 및 저장
        testAccount = Account.builder()
                .firstName("John")
                .lastName("Doe")
                .email("board-test@example.com")
                .password("password")
                .build();
        testAccountEntity = accountRepository.save(AccountPersistenceMapper.toEntity(testAccount));

        // 테스트용 보드 생성
        testBoard = Board.builder()
                .title("Test Board")
                .description("Board for testing")
                .build();
    }

    @AfterEach
    void cleanUp() {
        boardMemberRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("보드를 생성하고 ID로 조회할 수 있어야 한다")
    void shouldCreateAndRetrieveBoardById() {
        // 보드 생성
        var createdResult = adapter.createBoard(testBoard, testAccount.getId());

        // 생성 성공 확인
        assertThat(createdResult.isRight()).isTrue();

        // ID로 조회
        var boardId = createdResult.get().getId();
        var retrievedResult = adapter.findBoardById(boardId);

        // 조회 결과 확인
        assertThat(retrievedResult.isRight()).isTrue();
        assertThat(retrievedResult.get().getTitle()).isEqualTo("Test Board");
    }
}
