public class CodeRepository {
    public String fetchSource(String repoUrl, String branch) {
        System.out.println("Cloning " + repoUrl + " on branch " + branch);
        return "source(" + branch + ")";
    }
}
