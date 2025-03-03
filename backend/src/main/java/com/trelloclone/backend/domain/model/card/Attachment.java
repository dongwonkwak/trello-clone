package com.trelloclone.backend.domain.model.card;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public class Attachment {
    private final AttachmentId id;
    private final CardId cardId;
    private String name;
    private String url;
    private String fileType;
    private long size;
    private final LocalDateTime uploadedAt;

    public Attachment(
            AttachmentId id,
            CardId cardId,
            String name,
            String url,
            String fileType,
            long size,
            LocalDateTime uploadedAt) {
        this.id = requireNonNull(id);
        this.cardId = requireNonNull(cardId);
        this.name = requireNonNull(name);
        this.url = requireNonNull(url);
        this.fileType = fileType;
        this.size = size;
        this.uploadedAt = requireNonNull(uploadedAt);
    }

    public static Attachment create(
            CardId cardId,
            String name,
            String url,
            String fileType,
            long size) {
        return new Attachment(
                AttachmentId.newId(),
                cardId,
                name,
                url,
                fileType,
                size,
                LocalDateTime.now()
        );
    }

    public AttachmentId getId() {
        return id;
    }

    public CardId getCardId() {
        return cardId;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getFileType() {
        return fileType;
    }

    public long getSize() {
        return size;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
}
