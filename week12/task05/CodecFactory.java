public class CodecFactory {
    public String extract(VideoFile file) {
        System.out.println("Extracting source codec: " + file.getCodecType());
        return file.getCodecType();
    }

    public String getTargetCodec(String format) {
        System.out.println("Selecting target codec for format: " + format);
        return format.toLowerCase();
    }
}
