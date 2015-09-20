package wojilu.mail;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by shenyuyang
 */
public class MailAuth extends Authenticator {

    private String username;
    private String password;

    public MailAuth(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}
