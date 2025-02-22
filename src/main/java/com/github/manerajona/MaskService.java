package com.github.manerajona;

import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MaskService {

    private static final String MASK_CHARACTER = "X";

    /**
     * Returns a masked credit card number whose last 4 characters are shown while the rest is masked using a mask character.
     * <br>
     *
     * @param cardNumber the credit card number to be masked.
     * @return the masked credit card number.
     */
    @CacheResult(cacheName = "maskedCardNumber")
    public Uni<String> mask(String cardNumber) {
        int numDigitsToMask = Math.max(0, cardNumber.length() - 4);
        String maskedDigits = MASK_CHARACTER.repeat(numDigitsToMask);
        String lastDigits = cardNumber.substring(cardNumber.length() - 4);

        return Uni.createFrom().item(maskedDigits + lastDigits);
    }
}
