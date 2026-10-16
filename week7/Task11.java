package week7;

interface Notifier {
    String send(String msg);
}

class EmailNotifier implements Notifier {
    public String send(String msg) {
        return "EMAIL: " + msg;
    }
}

class SMSDecorator implements Notifier {
    private Notifier notifier;

    public SMSDecorator(Notifier notifier) {
        this.notifier = notifier;
    }

    public String send(String msg) {
        return notifier.send(msg) + " | SMS: " + msg;
    }
}

class SlackDecorator implements Notifier {
    private Notifier notifier;

    public SlackDecorator(Notifier notifier) {
        this.notifier = notifier;
    }

    public String send(String msg) {
        return notifier.send(msg) + " | SLACK: " + msg;
    }
}

class WhatsAppDecorator implements Notifier {
    private Notifier notifier;

    public WhatsAppDecorator(Notifier notifier) {
        this.notifier = notifier;
    }

    public String send(String msg) {
        return notifier.send(msg) + " | WA: " + msg;
    }
}