public class Main {
    public static void main(String[] args) {
        CompilerFacade compiler = new CompilerFacade();
        String output = compiler.compile("int x = 10;");
        System.out.println("Compiled output: " + output);
    }
}
