package ru.asvronsky.scrapper.clients;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import ru.asvronsky.scrapper.dto.client.GithubResponse;

@RequiredArgsConstructor
public class GithubClient {

    private static final String API_URL = "https://api.github.com";

    private final WebClient webClient;

    public static GithubClient create(String baseUrl, String token) {
        WebClient webClient = WebClient.builder()
                .filter((request, next) -> {
                    ClientRequest newRequest = ClientRequest.from(request)
                            .header("Authorization", "Bearer %s".formatted(token))
                            .build();
                    return next.exchange(newRequest);
                })
                .baseUrl(baseUrl)
                .build();
        
        return new GithubClient(webClient);
    }
    
    public static GithubClient create(String apiToken) {
        return create(API_URL, apiToken);
    }

    public GithubResponse getGihubData(String username, String repo) {
        String path = "/repos/%s/%s".formatted(username, repo);
        return webClient.get()
                .uri(path)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GithubResponse.class)
                .block();
    }
}
