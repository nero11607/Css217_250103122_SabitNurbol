public class Task09 {
    interface BitrateStrategy {
        String selectResolution(int bandwidthMbps);
    }

    static class DataSaverStrategy implements BitrateStrategy {
        @Override
        public String selectResolution(int bandwidthMbps) {
            return bandwidthMbps >= 2 ? "480p" : "360p";
        }
    }

    static class BalancedAutoStrategy implements BitrateStrategy {
        @Override
        public String selectResolution(int bandwidthMbps) {
            return bandwidthMbps >= 10 ? "1080p" : "720p";
        }
    }

    static class UltraHdStrategy implements BitrateStrategy {
        @Override
        public String selectResolution(int bandwidthMbps) {
            return bandwidthMbps >= 25 ? "4K" : "1440p";
        }
    }

    static class BufferMonitor {
        public int measureBandwidthMbps() {
            int mbps = 18;
            System.out.println("BufferMonitor: current bandwidth = " + mbps + " Mbps");
            return mbps;
        }
    }

    static class VideoDecoder {
        public void decodeStream(String streamUrl, String resolution) {
            System.out.println("VideoDecoder: decoding " + streamUrl + " at " + resolution);
        }
    }

    static class AudioSyncEngine {
        public void syncAudio(String streamUrl) {
            System.out.println("AudioSyncEngine: audio synchronized for " + streamUrl);
        }
    }

    static class SubtitleRenderer {
        public void render(String streamUrl) {
            System.out.println("SubtitleRenderer: subtitles loaded for " + streamUrl);
        }
    }

    static class MediaPlayerFacade {
        private final BufferMonitor bufferMonitor = new BufferMonitor();
        private final VideoDecoder videoDecoder = new VideoDecoder();
        private final AudioSyncEngine audioSyncEngine = new AudioSyncEngine();
        private final SubtitleRenderer subtitleRenderer = new SubtitleRenderer();
        private BitrateStrategy strategy;
        private String currentStreamUrl;

        public MediaPlayerFacade(BitrateStrategy strategy) {
            this.strategy = strategy;
        }

        public void playStream(String streamUrl) {
            this.currentStreamUrl = streamUrl;
            int mbps = bufferMonitor.measureBandwidthMbps();
            videoDecoder.decodeStream(streamUrl, strategy.selectResolution(mbps));
            audioSyncEngine.syncAudio(streamUrl);
            subtitleRenderer.render(streamUrl);
        }

        public void changeQualityMode(BitrateStrategy strategy) {
            this.strategy = strategy;
            if (currentStreamUrl != null) {
                int mbps = bufferMonitor.measureBandwidthMbps();
                videoDecoder.decodeStream(currentStreamUrl, strategy.selectResolution(mbps));
            }
        }
    }

    public static void main(String[] args) {
        MediaPlayerFacade player = new MediaPlayerFacade(new BalancedAutoStrategy());
        player.playStream("https://cdn.example.com/live/stream1");
        player.changeQualityMode(new DataSaverStrategy());
        player.changeQualityMode(new UltraHdStrategy());
    }
}
