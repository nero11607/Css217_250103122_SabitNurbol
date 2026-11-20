public class CompilerFacade {
    private final Lexer lexer;
    private final Parser parser;
    private final SemanticAnalyzer semanticAnalyzer;
    private final BytecodeGenerator bytecodeGenerator;

    public CompilerFacade() {
        this.lexer = new Lexer();
        this.parser = new Parser();
        this.semanticAnalyzer = new SemanticAnalyzer();
        this.bytecodeGenerator = new BytecodeGenerator();
    }

    public String compile(String sourceCode) {
        String tokens = lexer.tokenize(sourceCode);
        String ast = parser.parse(tokens);
        String validated = semanticAnalyzer.analyze(ast);
        return bytecodeGenerator.generate(validated);
    }
}
