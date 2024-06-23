package gg.pots.queue.player.message;

import gg.pots.queue.QueueSpigotPlugin;

public class QueueMessage {

    public static int QUEUE_DELAY = QueueSpigotPlugin.instance.settingsFile.getInt("delay") * 20;
    public static int QUEUE_REMINDER_DELAY = QueueSpigotPlugin.instance.settingsFile.getInt("reminder-delay") * 20;
}
