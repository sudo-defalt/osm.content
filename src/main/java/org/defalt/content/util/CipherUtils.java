package org.defalt.content.util;

import org.defalt.content.context.CurrentApplicationContext;
import org.defalt.content.model.MediaAccessToken;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public final class CipherUtils {
    private final Cipher cipher;

    public static CipherUtils getInstance() {
        return CurrentApplicationContext.getBean(CipherUtils.class);
    }

    private CipherUtils() throws Exception {
        byte[] password = "1111111111111111".getBytes();
        byte[] iv = "1111111111111111".getBytes();

        SecretKey secretKey = new SecretKeySpec(password,"AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
    }

    public MediaAccessToken decryptAccess(String accessToken) throws CipheringProcessException {
        try {
            return new MediaAccessToken(new String(cipher.doFinal(Base64.getDecoder().decode(accessToken))));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new CipheringProcessException();
        }
    }
}
