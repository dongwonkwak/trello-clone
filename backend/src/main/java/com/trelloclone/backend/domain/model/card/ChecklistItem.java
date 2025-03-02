package com.trelloclone.backend.domain.model.card;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public class ChecklistItem {
    private final ChecklistItemId id;
    private final ChecklistId checklistId;
    private String content;
    private boolean completed;
    private int position;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ChecklistItem(
            ChecklistItemId id,
            ChecklistId checklistId,
            String content,
            boolean completed,
            int position,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = requireNonNull(id);
        this.checklistId = requireNonNull(checklistId);
        this.content = requireNonNull(content);
        this.completed = completed;
        this.position = position;
        this.createdAt = requireNonNull(createdAt);
        this.updatedAt = updatedAt;
    }

    public static ChecklistItem create(ChecklistId checklistId, String content, boolean completed, int position) {
        LocalDateTime now = LocalDateTime.now();
        return new ChecklistItem(
                ChecklistItemId.newId(),
                checklistId,
                content,
                completed,
                position,
                now,
                now
        );
    }

    public void update(String content, boolean completed, int position) {
        this.content = requireNonNull(content);
        this.completed = completed;
        this.position = position;
        this.updatedAt = LocalDateTime.now();
    }

    public void toggle() {
        this.completed = !this.completed;
        this.updatedAt = LocalDateTime.now();
    }

    public void markComplete() {
        this.completed = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void markIncomplete() {
        this.completed = false;
        this.updatedAt = LocalDateTime.now();
    }

    public ChecklistItemId getId() {
        return id;
    }

    public ChecklistId getChecklistId() {
        return checklistId;
    }

    public String getContent() {
        return content;
    }

    public boolean isCompleted() {
        return completed;
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
}
