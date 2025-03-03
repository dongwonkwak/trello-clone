package com.trelloclone.backend.domain.model.card;

import com.trelloclone.backend.domain.model.common.BaseEntity;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public class ChecklistItem extends BaseEntity {
    private final ChecklistItemId id;
    private final ChecklistId checklistId;
    private String content;
    private boolean completed;
    private int position;

    public ChecklistItem(
            ChecklistItemId id,
            ChecklistId checklistId,
            String content,
            boolean completed,
            int position,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        super(createdAt, updatedAt);

        this.id = requireNonNull(id);
        this.checklistId = requireNonNull(checklistId);
        this.content = requireNonNull(content);
        this.completed = completed;
        this.position = position;
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
        recordUpdate();
    }

    public void toggle() {
        this.completed = !this.completed;
        recordUpdate();
    }

    public void markComplete() {
        this.completed = true;
        recordUpdate();
    }

    public void markIncomplete() {
        this.completed = false;
        recordUpdate();
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
}
