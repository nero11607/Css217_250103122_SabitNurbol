public class Main {
    public static void main(String[] args) {
        DeploymentFacade deployment = new DeploymentFacade();
        System.out.println("Deployed: " + deployment.deployService("https://github.com/org/service.git", "main"));
    }
}
