package week7;

interface FormattedText {
    String render();
}

class SimpleText implements FormattedText {
    private String text;

    public SimpleText(String text) {
        this.text = text;
    }

    public String render() {
        return text;
    }
}

class BoldTagDecorator implements FormattedText {
    private FormattedText formattedText;

    public BoldTagDecorator(FormattedText formattedText) {
        this.formattedText = formattedText;
    }

    public String render() {
        return "[B]" + formattedText.render() + "[/B]";
    }
}

class ItalicTagDecorator implements FormattedText {
    private FormattedText formattedText;

    public ItalicTagDecorator(FormattedText formattedText) {
        this.formattedText = formattedText;
    }

    public String render() {
        return "[I]" + formattedText.render() + "[/I]";
    }
}

class CodeTagDecorator implements FormattedText {
    private FormattedText formattedText;

    public CodeTagDecorator(FormattedText formattedText) {
        this.formattedText = formattedText;
    }

    public String render() {
        return "[CODE]" + formattedText.render() + "[/CODE]";
    }
}

