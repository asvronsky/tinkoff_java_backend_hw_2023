package ru.asvronsky.scrapper.clients;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.dto.client.ListStackOverflowResponse;
import ru.asvronsky.scrapper.dto.client.StackOverflowResponse;

@RequiredArgsConstructor
public class StackOverflowClient {
    
    private static final String API_URL = "https://api.stackexchange.com";

    private final WebClient webClient;

    public static StackOverflowClient create(String baseUrl, String token) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .filter((request, next) -> {
                    String newUrl = request.url().toString() + "&key=" + token;
                    ClientRequest newRequest = ClientRequest.from(request)
                        .url(URI.create(newUrl))
                        .build();
                    
                    return next.exchange(newRequest);
                })
                .build();
        
        return new StackOverflowClient(webClient);
    }

    public static StackOverflowClient create(String token) {
        return create(API_URL, token);
    }

    public ListStackOverflowResponse getStackOverflowData(List<Long> questionIds, String site) {
        String path = "/questions/%s?site=%s".formatted(
            questionIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(";")),
            "stackoverflow"
        );
        return webClient.get()
            .uri(path)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ListStackOverflowResponse.class)
            .block();
    }

    public StackOverflowResponse getStackOverflowData(long id) {
        return getStackOverflowData(List.of(id), "stackoverflow").items().get(0);
    }
}
