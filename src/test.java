import java.util.function.Consumer;

public class test {

    private static void command(Consumer<String> consumer, String s) {
        consumer.accept(s);
    }

    public static void main(String args[]) {
        String string = "2047";
        command((s)->{
            if(s.equals("2048")) {
                System.out.print(s);
            }
        }, string);
    }

}
