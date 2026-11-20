public class VideoFile {
    private final String filename;
    private final String codecType;

    public VideoFile(String filename) {
        this.filename = filename;
        int dotIndex = filename.lastIndexOf('.');
        this.codecType = dotIndex >= 0 ? filename.substring(dotIndex + 1) : "raw";
    }

    public String getFilename() {
        return filename;
    }

    public String getCodecType() {
        return codecType;
    }
}
