package ru.asvronsky.scrapper.clients;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.asvronsky.scrapper.dto.client.StackOverflowResponse;

@RequiredArgsConstructor
@Slf4j
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
                .filter((request, next) -> {
                    log.debug("Executing: ", request.url());
                    return next.exchange(request);
                })
                .build();
        
        return new StackOverflowClient(webClient);
    }

    public static StackOverflowClient create(String token) {
        return create(API_URL, token);
    }

    public List<StackOverflowResponse> getStackOverflowData(List<Long> questionIds) {
        String path = "/questions/%s?site=stackoverflow".formatted(
            questionIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(";"))
        );

        ListStackOverflowResponse listResponse = webClient.get()
            .uri(path)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ListStackOverflowResponse.class)
            .block();
        
        return listResponse.items();
    }



    @Nullable
    public Optional<StackOverflowResponse> getStackOverflowData(long id) {
        List<StackOverflowResponse> items = getStackOverflowData(List.of(id));
        return Optional.ofNullable((items.size() > 0) ? items.get(0) : null);
    }

    private record ListStackOverflowResponse(List<StackOverflowResponse> items) {
    
    }
    
}
