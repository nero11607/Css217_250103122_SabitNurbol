public class HomeTheaterFacade {
    private final Amplifier amplifier;
    private final Projector projector;
    private final StreamingPlayer player;
    private final TheaterLights lights;

    public HomeTheaterFacade() {
        this.amplifier = new Amplifier();
        this.projector = new Projector();
        this.player = new StreamingPlayer();
        this.lights = new TheaterLights();
    }

    public void watchMovie(String movie) {
        System.out.println("Starting movie setup...");
        lights.dim(15);
        projector.on();
        projector.wideScreenMode();
        amplifier.on();
        amplifier.setSurroundSound();
        amplifier.setVolume(20);
        player.on();
        player.play(movie);
    }

    public void endMovie() {
        System.out.println("Shutting down movie theater...");
        player.stop();
        player.off();
        amplifier.off();
        projector.off();
        lights.on();
    }
}
