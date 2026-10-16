package week7;

interface TextComponent {
    String format(String input);
}

class PlainText implements TextComponent {
    public String format(String input) {
        return input;
    }
}

class UpperCaseDecorator implements TextComponent {
    private TextComponent component;

    public UpperCaseDecorator(TextComponent component) {
        this.component = component;
    }

    public String format(String input) {
        return component.format(input).toUpperCase();
    }
}

class TrimDecorator implements TextComponent {
    private TextComponent component;

    public TrimDecorator(TextComponent component) {
        this.component = component;
    }

    public String format(String input) {
        return component.format(input).trim();
    }
}

class BracketDecorator implements TextComponent {
    private TextComponent component;

    public BracketDecorator(TextComponent component) {
        this.component = component;
    }

    public String format(String input) {
        return "[" + component.format(input) + "]";
    }
}

public class Task05 {
    public static void main(String[] args) {
        TextComponent formatter = new BracketDecorator(new UpperCaseDecorator(new TrimDecorator(new PlainText())));
        System.out.println(formatter.format("   hello world   "));
    }
}
