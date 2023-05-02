package ru.asvronsky.bot.botApi;

import java.util.function.Supplier;

import ru.asvronsky.bot.exceptions.ScrapperResponseException;
import ru.asvronsky.shared.shareddto.ApiErrorResponse;

public class ClientResponseHandler {
    private Supplier<String> onBadRequest = () -> null;
    private Supplier<String> onNotFound = () -> null;

    static public ClientResponseHandler get() {
        return new ClientResponseHandler();
    }

    public ClientResponseHandler onBadRequest(Supplier<String> supplier) {
        onBadRequest = supplier;
        return this;
    }

    public ClientResponseHandler onNotFound(Supplier<String> supplier) {
        onNotFound = supplier;
        return this;
    }

    public String handle(Supplier<String> supplier) {
        String message;
        try {
            message = supplier.get();
        } catch (ScrapperResponseException e) {
            message = switch(getCode(e.getApiResponse())) {
                case 400 -> onBadRequest.get();
                case 404 -> onNotFound.get();
                default -> null;
            };
            if (message == null) {
                throw e;
            }
        }
        return message;
    }

    static private int getCode(ApiErrorResponse response) {
        return Integer.parseInt(response.code());
    }
    
}
