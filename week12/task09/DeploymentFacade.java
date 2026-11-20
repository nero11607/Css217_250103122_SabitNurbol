public class DeploymentFacade {
    private final CodeRepository codeRepository;
    private final SecurityScanner securityScanner;
    private final ContainerBuilder containerBuilder;
    private final ClusterDeployer clusterDeployer;

    public DeploymentFacade() {
        this.codeRepository = new CodeRepository();
        this.securityScanner = new SecurityScanner();
        this.containerBuilder = new ContainerBuilder();
        this.clusterDeployer = new ClusterDeployer();
    }

    public boolean deployService(String repoUrl, String branch) {
        String source = codeRepository.fetchSource(repoUrl, branch);
        if (!securityScanner.scan(source)) {
            System.out.println("Deployment aborted: security scan failed.");
            return false;
        }
        String image = containerBuilder.buildImage(source);
        clusterDeployer.deploy(image);
        return true;
    }
}
