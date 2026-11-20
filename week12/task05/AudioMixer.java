public class AudioMixer {
    public String fix(String videoData) {
        System.out.println("Fixing and normalizing audio track for " + videoData);
        return videoData + "+audio";
    }
}
