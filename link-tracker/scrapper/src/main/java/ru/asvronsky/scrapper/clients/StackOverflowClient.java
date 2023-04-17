package ru.asvronsky.scrapper.clients;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.dto.client.ListStackOverflowResponse;

@RequiredArgsConstructor
public class StackOverflowClient {
    
    private static final String API_URL = "https://api.stackexchange.com";

    private final WebClient webClient;

    public static StackOverflowClient create(String baseUrl) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
        
        return new StackOverflowClient(webClient);
    }

    public static StackOverflowClient create() {
        return create(API_URL);
    }

    public ListStackOverflowResponse getStackOverflowData(List<Long> questionIds, String site) {
        String path = "/questions/%s?site=%s".formatted(
            questionIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(";"))
        );
        return webClient.get()
            .uri(path)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ListStackOverflowResponse.class)
            .block();
    }

    public ListStackOverflowResponse getStackOverflowData(long id) {
        return getStackOverflowData(List.of(id), "stackoverflow");
    }
}
