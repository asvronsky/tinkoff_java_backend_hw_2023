package ru.asvronsky.bot.clients;

import java.time.Duration;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;
import ru.asvronsky.bot.exceptions.ScrapperResponseException;
import ru.asvronsky.shared.scrapperdto.AddLinkRequest;
import ru.asvronsky.shared.scrapperdto.LinkResponse;
import ru.asvronsky.shared.scrapperdto.ListLinkResponse;
import ru.asvronsky.shared.scrapperdto.RemoveLinkRequest;
import ru.asvronsky.shared.shareddto.ApiErrorResponse;

@RequiredArgsConstructor
@Slf4j
public class ScrapperClient {
    
    private static final String API_URL = "http://localhost:8080";
    private final String tgChatPath = "/tg-chat";
    private final String linksPath = "/links";
    private final RetryBackoffSpec retrySpec = Retry
            .fixedDelay(3, Duration.ofSeconds(2))
            .filter(e -> !(e instanceof ScrapperResponseException))
            .doAfterRetry(signal -> log.info("Retry number {}", signal.totalRetries()));

    private final WebClient webClient;

    public static ScrapperClient create(String baseUrl) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .filter(apiErrorResponseToError())
                .build();
        
        return new ScrapperClient(webClient);
    }

    public static ScrapperClient create() {
        return create(API_URL);
    }

    public void registerChat(long chatId) {
        log.info("Webclient: register chat with id" + Long.toString(chatId));
        webClient.post()
            .uri(tgChatPath+"/{id}", chatId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toBodilessEntity()
            .retryWhen(retrySpec)
            .block();
    }

    public void deleteChat(long chatId) {
        webClient.delete()
            .uri(tgChatPath+"/{id}", chatId)
            .retrieve()
            .toBodilessEntity()
            .retryWhen(retrySpec)
            .block();
    }

    public ListLinkResponse getAllLinks(Long chatId) {
        return webClient.get()
                .uri(linksPath)
                .header("Tg-Chat-Id", chatId.toString())
                .retrieve()
                .bodyToMono(ListLinkResponse.class)
                .retryWhen(retrySpec)
                .block();
    }

    public LinkResponse addLink(Long chatId, AddLinkRequest request) {
        return webClient.post()
                .uri(linksPath)
                .header("Tg-Chat-Id", chatId.toString())
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .retryWhen(retrySpec)
                .block();
    }

    public LinkResponse deleteLink(Long chatId, RemoveLinkRequest request) {
        return webClient.method(HttpMethod.DELETE)
                .uri(linksPath)
                .header("Tg-Chat-Id", chatId.toString())
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .retryWhen(retrySpec)
                .block();
    }
    

    private static ExchangeFilterFunction apiErrorResponseToError() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().is4xxClientError()) {
                
                return clientResponse
                    .bodyToMono(ApiErrorResponse.class)
                    .flatMap(apiResponse -> {
                        log.info("Scrapper error response: " + apiResponse);
                        return Mono.error(new ScrapperResponseException(apiResponse));
                    });
            }
            return Mono.just(clientResponse);
        });
    }
}
