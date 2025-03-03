package com.trelloclone.backend.domain.model.card;

import com.trelloclone.backend.domain.model.account.AccountId;
import com.trelloclone.backend.domain.model.common.BaseEntity;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public class Comment extends BaseEntity {
    private final CommentId id;
    private final CardId cardId;
    private final AccountId accountId;
    private String content;

    public Comment(
            CommentId id,
            CardId cardId,
            AccountId accountId,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        super(createdAt, updatedAt);

        this.id = requireNonNull(id);
        this.cardId = requireNonNull(cardId);
        this.accountId = requireNonNull(accountId);
        this.content = requireNonNull(content);
    }

    public static Comment create(CardId cardId, AccountId accountId, String content) {
        LocalDateTime now = LocalDateTime.now();
        return new Comment(
                CommentId.newId(),
                cardId,
                accountId,
                content,
                now,
                now
        );
    }

    public void updateContent(String content) {
        this.content = requireNonNull(content);
        recordUpdate();
    }

    public CommentId getId() {
        return id;
    }

    public CardId getCardId() {
        return cardId;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public String getContent() {
        return content;
    }
}
