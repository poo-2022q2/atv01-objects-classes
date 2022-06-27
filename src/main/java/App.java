/**
 * App class.
 */
public class App {

    public String sayHello() {
        return "Hello World!";
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        System.out.println(new App().sayHello());
    }
}
