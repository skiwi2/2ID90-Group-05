package nl.tue.s2id90.group05;

/**
 *
 * @author Frank van Heeswijk
 */
public class AIStoppedException extends Exception {

    /**
     * Creates a new instance of <code>AIStoppedException</code> without detail
     * message.
     */
    public AIStoppedException() {
    }

    /**
     * Constructs an instance of <code>AIStoppedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AIStoppedException(String msg) {
        super(msg);
    }
}
