package com.github.manerajona;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Set;

@Path("/mask")
public class MaskResource {

    private static final String DIGITS_PATTERN = "\\d+";
    private static final Set<Integer> VALID_CARD_LENGTHS = Set.of(13, 14, 15, 16, 19);

    private final MaskService maskService;

    public MaskResource(MaskService maskService) {
        this.maskService = maskService;
    }

    @GET
    @Path("/{cardNumber}")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> getMaskedCardNumber(@PathParam("cardNumber") String cardNumber) {
        boolean isValidCard = cardNumber.matches(DIGITS_PATTERN) && VALID_CARD_LENGTHS.contains(cardNumber.length());
        if (isValidCard) {
            return maskService.mask(cardNumber)
                    .runSubscriptionOn(Infrastructure.getDefaultExecutor());
        } else {
            return Uni.createFrom()
                    .emitter(emitter -> emitter.fail(new BadRequestException("Invalid credit card.")));
        }
    }
}
