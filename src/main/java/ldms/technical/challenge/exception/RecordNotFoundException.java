package ldms.technical.challenge.exception;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(Integer id) {
        super("Record: " + id + " not found.");
    }

}
