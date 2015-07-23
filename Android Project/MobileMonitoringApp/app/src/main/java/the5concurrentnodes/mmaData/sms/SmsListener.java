package the5concurrentnodes.mmaData.sms;

/**
 * Defines methods triggered when SMS's are sent or received
 */
public interface SmsListener {

    /**
     *  Method that listens for incoming sms messages
     * @param sms Sms object containing incoming message information
     */
    public void onSmsReceived(Sms sms);

    /**
     *  Method that listens to outgoing sms message
     * @param sms Sms object containing outgoing message information
     */
    public void onSmsSent(Sms sms);
}
