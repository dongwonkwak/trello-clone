package com.trelloclone.backend.domain.model.list;

import com.trelloclone.backend.domain.model.board.BoardId;
import com.trelloclone.backend.domain.model.card.Card;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class BoardList {
    private final ListId id;
    private final BoardId boardId;
    private String name;
    private int position;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final List<Card> cards;

    private BoardList(
            ListId id,
            BoardId boardId,
            String name,
            int position,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = requireNonNull(id);
        this.boardId = requireNonNull(boardId);
        this.name = requireNonNull(name);
        this.position = position;
        this.createdAt = requireNonNull(createdAt);
        this.updatedAt = updatedAt;
        this.cards = new ArrayList<>();
    }

    public static BoardList create(BoardId boardId, String name, int position) {
        LocalDateTime now = LocalDateTime.now();
        return new BoardList(
                ListId.newId(),
                boardId,
                name,
                position,
                now,
                now
        );
    }

    public void update(String name) {
        this.name = requireNonNull(name);
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePosition(int position) {
        this.position = position;
        this.updatedAt = LocalDateTime.now();
    }

    public void addCard(Card card) {
        this.cards.add(card);
        this.sortCards();
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }

    public Card findCardById(String cardId) {
        return this.cards.stream()
                .filter(card -> card.getId().toString().equals(cardId))
                .findFirst()
                .orElse(null);
    }

    private void sortCards() {
        this.cards.sort(Comparator.comparingInt(Card::getPosition));
    }

    public ListId getId() {
        return id;
    }

    public BoardId getBoardId() {
        return boardId;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }
}
