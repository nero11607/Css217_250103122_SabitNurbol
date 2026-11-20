public class ContainerBuilder {
    public String buildImage(String source) {
        System.out.println("Building container image for " + source);
        return "image:latest";
    }
}
