package myvirtualfactory;



import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Crane
 */
public class Message {
    private ArrayBlockingQueue<String> myQueue;
    public Message(){
        myQueue = new ArrayBlockingQueue<String>(100);
    }

    public synchronized void add(String newMessage)
    {
        myQueue.add(newMessage);
        notifyAll();
    }
    public synchronized void remove(String newMessage)
    {
        myQueue.remove(newMessage);
        notifyAll();
    }
    public synchronized String toPrint()
    {
        try {
            while(myQueue.size() == 0)
            {
                wait(); //gönderilecek bir mesaj yoksa socket threadini beklet.
            }
        }catch (Exception ex){ex.printStackTrace();}
        return myQueue.peek();
    }

    public synchronized String take(){
        try {
            while(myQueue.size() == 0)
            {
                wait(); //gönderilecek bir mesaj yoksa socket threadini beklet.
            }
            return myQueue.poll();

        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
