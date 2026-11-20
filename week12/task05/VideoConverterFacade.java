public class VideoConverterFacade {
    private final CodecFactory codecFactory;
    private final BitrateReader bitrateReader;
    private final AudioMixer audioMixer;

    public VideoConverterFacade() {
        this.codecFactory = new CodecFactory();
        this.bitrateReader = new BitrateReader();
        this.audioMixer = new AudioMixer();
    }

    public String convert(String filename, String targetFormat) {
        VideoFile file = new VideoFile(filename);
        String sourceCodec = codecFactory.extract(file);
        String targetCodec = codecFactory.getTargetCodec(targetFormat);
        String buffer = bitrateReader.read(file, sourceCodec);
        String converted = bitrateReader.convert(buffer, targetCodec);
        String result = audioMixer.fix(converted);
        int dotIndex = filename.lastIndexOf('.');
        String baseName = dotIndex >= 0 ? filename.substring(0, dotIndex) : filename;
        String outputFile = baseName + "." + targetFormat.toLowerCase();
        System.out.println("Conversion complete: " + outputFile + " (" + result + ")");
        return outputFile;
    }
}
