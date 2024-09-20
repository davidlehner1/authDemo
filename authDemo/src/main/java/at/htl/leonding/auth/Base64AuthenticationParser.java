package at.htl.leonding.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Base64;
import java.util.regex.Pattern;

@ApplicationScoped
public class Base64AuthenticationParser {

    public static final Pattern basicAuthenticationHeaderPattern =  Pattern.compile("Basic (.*)");
    @Inject
    Logger logger;

    public Credentials parseAuthenticationHeader(String headers) {
        Credentials credentials = null;
        var matcher = basicAuthenticationHeaderPattern.matcher(headers);
        boolean matchFound = matcher.find();

        if(matchFound) {
            var encodedCredentials = matcher.group(1);
            var decodedCredentials = new String (Base64.getDecoder().decode(encodedCredentials));
            String[] userAndPass = decodedCredentials.split(":");
            String user = userAndPass[0];
            String pass = userAndPass[1];

            logger.infof("User: %s, Password: %s", user, pass);

            credentials = new Credentials(user, pass);
        }

        return credentials;
    }
}
