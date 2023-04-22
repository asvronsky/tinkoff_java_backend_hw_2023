package ru.asvronsky.scrapper.clients;

import java.time.Duration;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;
import ru.asvronsky.scrapper.exceptions.BotResponseException;
import ru.asvronsky.shared.botdto.UpdateLinksRequest;
import ru.asvronsky.shared.shareddto.ApiErrorResponse;

@RequiredArgsConstructor
@Slf4j
public class BotClient {

    private static final String API_URL = "http://localhost:8081";
    private final String updatesPath = "/updates";
    private final RetryBackoffSpec retrySpec = Retry
            .fixedDelay(3, Duration.ofSeconds(2))
            .filter(e -> !(e instanceof BotResponseException))
            .doAfterRetry(signal -> log.info("Retry number {}", signal.totalRetries()));

    private final WebClient webClient;

    public static BotClient create(String baseUrl) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .filter(apiErrorResponseToError())
                .build();
        
        return new BotClient(webClient);
    }
    
    public static BotClient create() {
        return create(API_URL);
    }

    public void sendNotification(UpdateLinksRequest request) {
        webClient.post()
            .uri(updatesPath)
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .toBodilessEntity()
            .retryWhen(retrySpec)
            .block();
    }

    // TODO: map 5xx errors
    private static ExchangeFilterFunction apiErrorResponseToError() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().is4xxClientError()) {
                
                return clientResponse
                    .bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiResponse -> {
                        log.info("Bot error response: " + apiResponse);
                        return Mono.error(new BotResponseException(apiResponse));
                    });
            }
            return Mono.just(clientResponse);
        });
    }
}
