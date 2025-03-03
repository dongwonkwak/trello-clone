package com.trelloclone.backend.domain.model.card;

import com.trelloclone.backend.domain.model.common.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Checklist extends BaseEntity {

    private final ChecklistId id;
    private final CardId cardId;
    private String title;
    private int position;
    private final List<ChecklistItem> items;

    public Checklist(
            ChecklistId id,
            CardId cardId,
            String title,
            int position,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        super(createdAt, updatedAt);

        this.id = requireNonNull(id);
        this.cardId = requireNonNull(cardId);
        this.title = requireNonNull(title);
        this.position = position;
        this.items = new ArrayList<>();
    }

    public static Checklist create(CardId cardId, String title, int position) {
        LocalDateTime now = LocalDateTime.now();
        return new Checklist(
                ChecklistId.newId(),
                cardId,
                title,
                position,
                now,
                now
        );
    }

    public void update(String title, int position) {
        this.title = requireNonNull(title);
        this.position = position;
        recordUpdate();
    }

    public void addItem(ChecklistItem item) {
        this.items.add(item);
        this.sortItems();
    }

    public void removeItem(ChecklistItem item) {
        this.items.remove(item);
    }

    public double getCompletionPercentage() {
        if (items.isEmpty()) {
            return 0.0;
        }

        long completedCount = items.stream()
                .filter(ChecklistItem::isCompleted)
                .count();

        return (double) completedCount / items.size() * 100.0;
    }

    private void sortItems() {
        this.items.sort(Comparator.comparingInt(ChecklistItem::getPosition));
    }

    public ChecklistId getId() {
        return id;
    }

    public CardId getCardId() {
        return cardId;
    }

    public String getTitle() {
        return title;
    }

    public int getPosition() {
        return position;
    }

    public List<ChecklistItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
