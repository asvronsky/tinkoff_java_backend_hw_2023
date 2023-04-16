package ru.asvronsky.scrapper.clients;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.dto.client.GithubResponse;

@RequiredArgsConstructor
public class GithubClient {

    private static final String API_URL = "https://api.github.com";

    private final WebClient webClient;

    public static GithubClient create(String baseUrl) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
        
        return new GithubClient(webClient);
    }
    
    public static GithubClient create() {
        return create(API_URL);
    }

    public GithubResponse getGihubData(String ownerAndName) {
        String path = "/repos/" + ownerAndName;
        return webClient.get()
                .uri(path)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GithubResponse.class)
                .block();
    }
}
