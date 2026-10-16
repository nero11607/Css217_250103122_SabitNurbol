package week7;

interface CsvExporter {
    String exportCsv();
}

class ArrayDataStore {
    public String[] fetchWords() {
        return new String[]{"apple", "banana", "cherry"};
    }
}

class ArrayToCsvAdapter implements CsvExporter {
    private ArrayDataStore store;

    public ArrayToCsvAdapter(ArrayDataStore store) {
        this.store = store;
    }

    public String exportCsv() {
        String[] words = store.fetchWords();
        return String.join(",", words);
    }
}