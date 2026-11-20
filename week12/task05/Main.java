public class Main {
    public static void main(String[] args) {
        VideoConverterFacade converter = new VideoConverterFacade();
        String output = converter.convert("lecture.ogg", "mp4");
        System.out.println("Output file: " + output);
    }
}
