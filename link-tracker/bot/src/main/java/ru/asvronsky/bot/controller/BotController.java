package ru.asvronsky.bot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import ru.asvronsky.bot.dto.ApiErrorResponse;
import ru.asvronsky.bot.dto.UpdateLinksRequest;
import ru.asvronsky.bot.services.BotService;

@RestController
@RequestMapping("/updates")
@RequiredArgsConstructor
public class BotController {
    private final BotService botService;
    
    @Operation(summary = "Request update")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated"),
        @ApiResponse(responseCode = "400", description = "Invalid request format",
            content = @Content(
                    schema = @Schema(implementation = ApiErrorResponse.class),
                    mediaType = "application/json"))
    })
    @PostMapping(consumes = "application/json")
    public void updateLinks(@RequestBody(required = true) UpdateLinksRequest request) {
        botService.updateLinks();
    }

}
