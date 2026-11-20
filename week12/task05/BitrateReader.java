public class BitrateReader {
    public String read(VideoFile file, String sourceCodec) {
        System.out.println("Reading bitrate buffer for " + file.getFilename() + " [" + sourceCodec + "]");
        return "buffer(" + file.getFilename() + ")";
    }

    public String convert(String buffer, String targetCodec) {
        System.out.println("Converting " + buffer + " to codec " + targetCodec);
        return buffer + "->" + targetCodec;
    }
}
