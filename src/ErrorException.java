/**
 * The type Error exception.
 */
public class ErrorException extends Exception {
    private static final long serialVersionUID = 1L;
    private String generalErrorMessage;
    private String reasonMessage;
    private String detailsMessage;
    private String exitMessage;


    /**
     * Instantiates a new Error exception.
     *
     * @param generalErrorMessage the general error message
     * @param reasonMessage       the reason message
     * @param detailsMessage      the details message
     * @param exitMessage         the exit message
     */
    public ErrorException(String generalErrorMessage, String reasonMessage,
                          String detailsMessage, String exitMessage) {
        this.generalErrorMessage = generalErrorMessage;
        this.reasonMessage = reasonMessage;
        this.detailsMessage = detailsMessage;
        this.exitMessage = exitMessage;
    }

}
