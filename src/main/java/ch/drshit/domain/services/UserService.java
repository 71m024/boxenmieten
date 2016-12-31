package ch.drshit.domain.services;

import ch.drshit.domain.model.BmUser;
import org.slf4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by timo on 16.12.16.
 */

@Named
@RequestScoped
@Transactional
public class UserService extends GenericService<BmUser> {

    @Inject
    private transient Logger logger;

    public UserService() {
        super(BmUser.class);
    }

    public void setPassword(BmUser user, String password) {
        String hashedPassword;
        try {
            String salt = PasswordHash.createSalt();
            hashedPassword = PasswordHash.createHash(password, salt);
            user.setSalt(salt);
            user.setPassword(hashedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error(null, e);
        }
    }

    public boolean validatePassword(BmUser user, String password) {
        try {
            return PasswordHash.validatePassword(password, user.getPassword(), user.getSalt());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error(null, e);
            return false;
        }
    }

}