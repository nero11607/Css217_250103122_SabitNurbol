public class SemanticAnalyzer {
    public String analyze(String ast) {
        System.out.println("SemanticAnalyzer validating " + ast);
        return "VALIDATED(" + ast + ")";
    }
}
