import DatagramLevel.DatagramLevel;

public class Start {
    public static void main(String args[]) {
        DatagramLevel server = new DatagramLevel(8000,1024, "src/russian.lang");

    }
}
