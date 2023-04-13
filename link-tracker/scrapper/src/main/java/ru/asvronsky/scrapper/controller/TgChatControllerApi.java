package ru.asvronsky.scrapper.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import ru.asvronsky.scrapper.dto.controller.ApiErrorResponse;

@RequestMapping("/tg-chat")
public interface TgChatControllerApi {

    @Operation(summary = "Add chat")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Chat registered"),
        @ApiResponse(responseCode = "400", description = "Invalid request format",
            content = @Content(
                    schema = @Schema(implementation = ApiErrorResponse.class),
                    mediaType = "application/json"))
    })
    @PostMapping("/{id}")
    public void registerChat(@PathVariable("id") long chatId);

    @Operation(summary = "Delete chat")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Chat deleted"),
        @ApiResponse(responseCode = "400", description = "Invalid request format",
            content = @Content(
                    schema = @Schema(implementation = ApiErrorResponse.class),
                    mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Chat not found",
            content = @Content(
                    schema = @Schema(implementation = ApiErrorResponse.class),
                    mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable("id") long chatId);

}