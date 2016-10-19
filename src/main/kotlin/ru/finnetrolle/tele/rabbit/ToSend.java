package ru.finnetrolle.tele.rabbit;

import java.util.Objects;

public class ToSend {

    private String method;
    private String chatId;
    private String text;
    private String parseMode;
    private Boolean disableWebPagePreview;
    private Boolean disableNotification;
    private Integer replyToMessageId;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParseMode() {
        return parseMode;
    }

    public void setParseMode(String parseMode) {
        this.parseMode = parseMode;
    }

    public Boolean getDisableWebPagePreview() {
        return disableWebPagePreview;
    }

    public void setDisableWebPagePreview(Boolean disableWebPagePreview) {
        this.disableWebPagePreview = disableWebPagePreview;
    }

    public Boolean getDisableNotification() {
        return disableNotification;
    }

    public void setDisableNotification(Boolean disableNotification) {
        this.disableNotification = disableNotification;
    }

    public Integer getReplyToMessageId() {
        return replyToMessageId;
    }

    public void setReplyToMessageId(Integer replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToSend toSend = (ToSend) o;
        return Objects.equals(method, toSend.method) &&
                Objects.equals(chatId, toSend.chatId) &&
                Objects.equals(text, toSend.text) &&
                Objects.equals(parseMode, toSend.parseMode) &&
                Objects.equals(disableWebPagePreview, toSend.disableWebPagePreview) &&
                Objects.equals(disableNotification, toSend.disableNotification) &&
                Objects.equals(replyToMessageId, toSend.replyToMessageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, chatId, text, parseMode, disableWebPagePreview, disableNotification, replyToMessageId);
    }

    @Override
    public String toString() {
        return "ToSend{" +
                "method='" + method + '\'' +
                ", chatId='" + chatId + '\'' +
                ", text='" + text + '\'' +
                ", parseMode='" + parseMode + '\'' +
                ", disableWebPagePreview=" + disableWebPagePreview +
                ", disableNotification=" + disableNotification +
                ", replyToMessageId=" + replyToMessageId +
                '}';
    }
}
