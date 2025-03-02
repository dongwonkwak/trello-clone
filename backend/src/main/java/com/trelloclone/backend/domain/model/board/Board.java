package com.trelloclone.backend.domain.model.board;

import com.trelloclone.backend.domain.model.list.BoardList;
import com.trelloclone.backend.domain.model.workspace.WorkspaceId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Board {
    private final BoardId id;
    private final WorkspaceId workspaceId;
    private String name;
    private String description;
    private String background;
    private boolean isPublic;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final List<BoardList> lists;
    private final List<Label> labels;

    private Board(
            BoardId id,
            WorkspaceId workspaceId,
            String name,
            String description,
            String background,
            boolean isPublic,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = requireNonNull(id);
        this.workspaceId = requireNonNull(workspaceId);
        this.name = requireNonNull(name);
        this.description = description;
        this.background = background;
        this.isPublic = isPublic;
        this.createdAt = requireNonNull(createdAt);
        this.updatedAt = updatedAt;
        this.lists = new ArrayList<>();
        this.labels = new ArrayList<>();
    }

    public static Board create(
            WorkspaceId workspaceId,
            String name,
            String description,
            String background,
            boolean isPublic) {
        LocalDateTime now = LocalDateTime.now();
        return new Board(
                BoardId.newId(),
                workspaceId,
                name,
                description,
                background,
                isPublic,
                now,
                now
        );
    }

    public void update(String name, String description, String background, boolean isPublic) {
        this.name = requireNonNull(name);
        this.description = description;
        this.background = background;
        this.isPublic = isPublic;
        this.updatedAt = LocalDateTime.now();
    }

    public void addList(BoardList list) {
        this.lists.add(list);
    }

    public void removeList(BoardList list) {
        this.lists.remove(list);
    }

    public void addLabel(Label label) {
        this.labels.add(label);
    }

    public void removeLabel(Label label) {
        this.labels.remove(label);
    }

    public BoardId getId() {
        return id;
    }

    public WorkspaceId getWorkspaceId() {
        return workspaceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBackground() {
        return background;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<BoardList> getLists() {
        return Collections.unmodifiableList(lists);
    }

    public List<Label> getLabels() {
        return Collections.unmodifiableList(labels);
    }
}
