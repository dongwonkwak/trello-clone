package com.trelloclone.backend.domain.model.card;

import com.trelloclone.backend.domain.model.board.LabelId;
import com.trelloclone.backend.domain.model.common.BaseEntity;
import com.trelloclone.backend.domain.model.list.ListId;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Objects.requireNonNull;

public class Card extends BaseEntity {
    private final CardId id;
    private ListId listId;
    private String title;
    private String description;
    private int position;
    private LocalDateTime dueDate;
    private String coverImage;
    private boolean archived;

    private final Set<LabelId> labelIds;
    private final List<Attachment> attachments;
    private final List<Comment> comments;
    private final List<Checklist> checklists;

    private Card(
            CardId id,
            ListId listId,
            String title,
            String description,
            int position,
            LocalDateTime dueDate,
            String coverImage,
            boolean archived,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        super(createdAt, updatedAt);

        this.id = requireNonNull(id);
        this.listId = requireNonNull(listId);
        this.title = requireNonNull(title);
        this.description = description;
        this.position = position;
        this.dueDate = dueDate;
        this.coverImage = coverImage;
        this.archived = archived;
        this.labelIds = new HashSet<>();
        this.attachments = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.checklists = new ArrayList<>();
    }

    public static Card create(ListId listId, String title, String description, int position) {
        LocalDateTime now = LocalDateTime.now();
        return new Card(
                CardId.newId(),
                listId,
                title,
                description,
                position,
                null,
                null,
                false,
                now,
                now
        );
    }

    public void update(String title, String description, LocalDateTime dueDate, String coverImage) {
        this.title = Objects.requireNonNull(title);
        this.description = description;
        this.dueDate = dueDate;
        this.coverImage = coverImage;
        recordUpdate();
    }

    public void moveToList(ListId newListId, int newPosition) {
        this.listId = Objects.requireNonNull(newListId);
        this.position = newPosition;
        recordUpdate();
    }

    public void updatePosition(int position) {
        this.position = position;
        recordUpdate();
    }

    public void archive() {
        this.archived = true;
        recordUpdate();
    }

    public void unarchive() {
        this.archived = false;
        recordUpdate();
    }

    public void addLabel(LabelId labelId) {
        this.labelIds.add(labelId);
        recordUpdate();
    }

    public void removeLabel(LabelId labelId) {
        this.labelIds.remove(labelId);
        recordUpdate();
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }

    public void removeAttachment(Attachment attachment) {
        this.attachments.remove(attachment);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }

    public void addChecklist(Checklist checklist) {
        this.checklists.add(checklist);
    }

    public void removeChecklist(Checklist checklist) {
        this.checklists.remove(checklist);
    }

    public boolean isDueToday() {
        if (dueDate == null) {
            return false;
        }
        LocalDateTime today = LocalDateTime.now();
        return dueDate.toLocalDate().equals(today.toLocalDate());
    }

    public boolean isDueOverdue() {
        if (dueDate == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(dueDate);
    }

    public CardId getId() {
        return id;
    }

    public ListId getListId() {
        return listId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPosition() {
        return position;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public boolean isArchived() {
        return archived;
    }

    public Set<LabelId> getLabelIds() {
        return Collections.unmodifiableSet(labelIds);
    }

    public List<Attachment> getAttachments() {
        return Collections.unmodifiableList(attachments);
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public List<Checklist> getChecklists() {
        return Collections.unmodifiableList(checklists);
    }
}
