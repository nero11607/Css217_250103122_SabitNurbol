package week7;

interface MediaPlayer {
    void playAudio(String filename);
}

class VlcEngine {
    public void startPlayback(String filepath, int volume) {
        System.out.println("Playing " + filepath + " at volume " + volume);
    }
}

class VlcAdapter implements MediaPlayer {
    private VlcEngine engine;

    public VlcAdapter(VlcEngine engine) {
        this.engine = engine;
    }

    public void playAudio(String filename) {
        engine.startPlayback(filename, 80);
    }
}
