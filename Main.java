import java.util.ArrayList;
import Main.EOF;
import java.util.List;
import java.util.Random;



public class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {
        List<String> buffer = new ArrayList<String>();
        MyProducer producer = new MyProducer(buffer);
        MyConsumer consumer1 = new MyConsumer(buffer);
        MyConsumer consumer2 = new MyConsumer(buffer);

        new Thread(producer).start();
        new Thread(consumer1).start();
        new Thread(consumer2).start();
    }
}

class MyProducer implements  Runnable {
    private List<String> buffer;
    private String color;

    public MyProducer(List<String> buffer) {
        this.buffer = buffer;
        
    }

    public void run() {
        Random random = new Random();
        String[] nums = { "1", "2", "3", "4", "5"};

        for(String num: nums) {
            try {
                System.out.println(color + "Adding..." + num);
                synchronized(buffer) {
                    buffer.add(num);
                }

                Thread.sleep(random.nextInt(1000));
            } catch(InterruptedException e) {
                System.out.println("Producer was interrupted");
            }
        }

        System.out.println(color + "Adding EOF and exiting....");
        synchronized(buffer) {
            buffer.add("EOF");
        }
    }
}

class MyConsumer implements Runnable {
    private List<String> buffer;
    

    public MyConsumer(List<String> buffer) {
        this.buffer = buffer;
        
    }

    public void run() {
        while(true) {
            synchronized(buffer) {
                if(buffer.isEmpty()) {
                    continue;
                }
                if(buffer.get(0).equals(EOF)) {
                    System.out.println( "Exiting");
                    break;
                } else {
                    System.out.println( "Removed " + buffer.remove(0));
                }
            }
        }
    }
}
