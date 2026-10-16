interface MegabyteReader {
    double getSizeInMB();
}

class ByteFileStat {
    public long getBytes() {
        return 5242880L;
    }
}

class FileSizeAdapter implements MegabyteReader {
    private ByteFileStat stat;

    public FileSizeAdapter(ByteFileStat stat) {
        this.stat = stat;
    }

    public double getSizeInMB() {
        return stat.getBytes() / 1048576.0;
    }
}

public class Task36 {
    public static void main(String[] args) {
        MegabyteReader reader = new FileSizeAdapter(new ByteFileStat());
        System.out.println(reader.getSizeInMB());
    }
}
