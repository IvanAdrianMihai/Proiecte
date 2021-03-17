package domain;

import java.time.LocalDateTime;
import java.util.List;

public class ReplyMessage extends Message {
    private Message replayMessage;

    public ReplyMessage(Utilizator from, String message, LocalDateTime date, Message msg) {
        super(from, message, date);
        this.replayMessage = msg;
    }

    public ReplyMessage(Utilizator from, String message, Message msg) {
        super(from, message);
        this.replayMessage = msg;
    }

    public Message getReplayMessage() {
        return replayMessage;
    }

    @Override
    public String toString() {
        return super.toString() + " - Replay "+ replayMessage.getId();
    }
}