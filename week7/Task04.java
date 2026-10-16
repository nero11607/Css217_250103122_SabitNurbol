package week7;
import java.util.*;
interface SortStrategy{
    void sort(int[] data);
}
class BubbleSortStrategy implements SortStrategy {
    public void sort(int[] data) {
        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (data[j] > data[j + 1]) {
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }
    }
}

class SelectionSortStrategy implements SortStrategy {
    public void sort(int[] data) {
        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (data[j] < data[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = data[minIdx];
            data[minIdx] = data[i];
            data[i] = temp;
        }
    }
}

class InsertionSortStrategy implements SortStrategy {
    public void sort(int[] data) {
        int n = data.length;
        for (int i = 1; i < n; i++) {
            int key = data[i];
            int j = i - 1;
            while (j >= 0 && data[j] > key) {
                data[j + 1] = data[j];
                j = j - 1;
            }
            data[j + 1] = key;
        }
    }
}

class DataProcessor {
    private SortStrategy strategy;

    public DataProcessor(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void process(int[] data) {
        strategy.sort(data);
        System.out.println(Arrays.toString(data));
    }
}

public class Task04 {
    public static void main(String[] args) {
        DataProcessor processor = new DataProcessor(new BubbleSortStrategy());
        processor.process(new int[]{5, 2, 9, 1});
        processor.setStrategy(new SelectionSortStrategy());
        processor.process(new int[]{8, 3, 7, 4});
        processor.setStrategy(new InsertionSortStrategy());
        processor.process(new int[]{6, 1, 5, 2});
    }
}
