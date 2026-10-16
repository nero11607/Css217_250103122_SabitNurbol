package week7;

interface AppLogger {
    void log(String level, String message);
}

class ConsoleWriter {
    public void writeLine(String fullText) {
        System.out.println(fullText);
    }
}

class LoggerAdapter implements AppLogger {
    private ConsoleWriter writer;

    public LoggerAdapter(ConsoleWriter writer) {
        this.writer = writer;
    }

    public void log(String level, String message) {
        writer.writeLine("[" + level.toUpperCase() + "] " + message);
    }
}
