package exception;

public class RowNotSelectedException extends Exception {
    
    public RowNotSelectedException() {
        super("Select a row before performing the operation.");
    }
    
}
