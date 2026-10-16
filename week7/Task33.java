package week7;

interface LibraryItem {
    String getCitation();
}

class OldBookEntry {
    public String getAuthor() {
        return "Joshua Bloch";
    }

    public String getTitle() {
        return "Effective Java";
    }

    public int getPubYear() {
        return 2018;
    }
}

class BookAdapter implements LibraryItem {
    private OldBookEntry book;

    public BookAdapter(OldBookEntry book) {
        this.book = book;
    }

    public String getCitation() {
        return book.getAuthor() + " (" + book.getPubYear() + ") - " + book.getTitle();
    }
}