package ru.asvronsky.bot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ru.asvronsky.shared.botdto.UpdateLinksRequest;
import ru.asvronsky.shared.shareddto.ApiErrorResponse;

@RequestMapping("/updates")
public interface BotControllerApi {
    
    @Operation(summary = "Request update")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated"),
        @ApiResponse(responseCode = "400", description = "Invalid request format",
            content = @Content(
                    schema = @Schema(implementation = ApiErrorResponse.class),
                    mediaType = "application/json"))
    })
    @PostMapping(consumes = "application/json")
    public void updateLinks(@RequestBody(required = true) UpdateLinksRequest request);

}
