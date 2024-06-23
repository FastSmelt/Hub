package gg.pots.queue;

import gg.pots.permissions.api.utility.CC;
import lombok.Getter;

@Getter
public enum QueueConstants {

    ALREADY_IN_QUEUE("queue.already-in-queue"),
    NOT_IN_QUEUE("queue.not-in-queue"),
    JOINED_QUEUE("queue.joined-queue"),
    LEFT_QUEUE("queue.left-queue"),
    REMINDER_QUEUE("queue.reminder"),
    SENT_QUEUE("queue.sent-queue"),
    PAUSED_QUEUE("queue.paused-queue"),
    RESUMED_QUEUE("queue.resumed-queue"),
    AVAILABLE_SERVERS("queue.available-servers");

    private final String messages;

    QueueConstants(String messages) {
        this.messages = messages;
    }

    public String get() {
        return CC.translate(QueueSpigotPlugin.instance.messagesFile.getString(messages));
    }
}