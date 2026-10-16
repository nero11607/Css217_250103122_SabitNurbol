package week7;

interface ModernPrinter {
    void printDocument(String text);
}

class DotMatrixPrinter {
    public void printCharArray(char[] buffer) {
        for (int i = 0; i < buffer.length; i++) {
            System.out.print(buffer[i] + (i < buffer.length - 1 ? " " : ""));
        }
        System.out.println();
    }
}

class PrinterAdapter implements ModernPrinter {
    private DotMatrixPrinter printer;

    public PrinterAdapter(DotMatrixPrinter printer) {
        this.printer = printer;
    }

    public void printDocument(String text) {
        printer.printCharArray(text.toCharArray());
    }
}