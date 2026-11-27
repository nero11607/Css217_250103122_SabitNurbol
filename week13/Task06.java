public class Task06 {
    interface FilterStrategy {
        int[][] apply(int[][] pixels);
    }

    static class GrayscaleFilter implements FilterStrategy {
        @Override
        public int[][] apply(int[][] pixels) {
            System.out.println("GrayscaleFilter: converting pixel matrix to grayscale");
            return pixels;
        }
    }

    static class SepiaToneFilter implements FilterStrategy {
        @Override
        public int[][] apply(int[][] pixels) {
            System.out.println("SepiaToneFilter: applying warm sepia tone to pixel matrix");
            return pixels;
        }
    }

    static class HighContrastFilter implements FilterStrategy {
        @Override
        public int[][] apply(int[][] pixels) {
            System.out.println("HighContrastFilter: boosting contrast on pixel matrix");
            return pixels;
        }
    }

    static class ImageDecoder {
        public int[][] decode(String inputPath) {
            System.out.println("ImageDecoder: decoding " + inputPath);
            return new int[4][4];
        }
    }

    static class ThumbnailResizer {
        public int[][] resize(int[][] pixels, int width, int height) {
            System.out.println("ThumbnailResizer: resizing to " + width + "x" + height);
            return new int[height][width];
        }
    }

    static class ImageEncoder {
        public void save(int[][] pixels, String outputPath) {
            System.out.println("ImageEncoder: saved " + pixels[0].length + "x" + pixels.length + " image to " + outputPath);
        }
    }

    static class ImagePipelineFacade {
        private final ImageDecoder decoder = new ImageDecoder();
        private final ThumbnailResizer resizer = new ThumbnailResizer();
        private final ImageEncoder encoder = new ImageEncoder();
        private FilterStrategy strategy;

        public ImagePipelineFacade(FilterStrategy strategy) {
            this.strategy = strategy;
        }

        public void setStrategy(FilterStrategy strategy) {
            this.strategy = strategy;
        }

        public void processImage(String inputPath, int width, int height) {
            int[][] decoded = decoder.decode(inputPath);
            int[][] resized = resizer.resize(decoded, width, height);
            int[][] filtered = strategy.apply(resized);
            encoder.save(filtered, "thumb_" + inputPath);
        }
    }

    public static void main(String[] args) {
        ImagePipelineFacade pipeline = new ImagePipelineFacade(new GrayscaleFilter());
        pipeline.processImage("photo1.jpg", 200, 150);
        pipeline.setStrategy(new SepiaToneFilter());
        pipeline.processImage("photo2.png", 300, 300);
        pipeline.setStrategy(new HighContrastFilter());
        pipeline.processImage("photo3.jpg", 128, 128);
    }
}
