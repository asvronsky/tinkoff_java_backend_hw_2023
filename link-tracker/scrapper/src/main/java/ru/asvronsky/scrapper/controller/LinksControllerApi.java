package ru.asvronsky.scrapper.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import ru.asvronsky.scrapper.dto.controller.AddLinkRequest;
import ru.asvronsky.scrapper.dto.controller.ApiErrorResponse;
import ru.asvronsky.scrapper.dto.controller.LinkResponse;
import ru.asvronsky.scrapper.dto.controller.ListLinkResponse;
import ru.asvronsky.scrapper.dto.controller.RemoveLinkRequest;

@RequestMapping("/links")
public interface LinksControllerApi {

    @Operation(summary = "List all tracked links")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Links received"),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid request format",
            content = @Content(
                schema = @Schema(implementation = ApiErrorResponse.class),
                mediaType = "application/json"))
    })
    @GetMapping(produces = "application/json")
    public ListLinkResponse getAllLinks(
        @RequestHeader(required = true, value = "Tg-Chat-Id") long chatId);


    @Operation(summary = "Start tracking link")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Link added"),
        @ApiResponse(responseCode = "400", description = "Invalid request format",
            content = @Content(
                    schema = @Schema(implementation = ApiErrorResponse.class),
                    mediaType = "application/json"))
    })
    @PostMapping(produces = "application/json")
    public LinkResponse addLink(
        @RequestHeader(required = true, value = "Tg-Chat-Id") long chatId, 
        @RequestBody(required = true) AddLinkRequest request);


    @Operation(summary = "Stop tracking link")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Link removed"),
        @ApiResponse(responseCode = "400", description = "Invalid request format",
            content = @Content(
                    schema = @Schema(implementation = ApiErrorResponse.class),
                    mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Link not found",
            content = @Content(
                    schema = @Schema(implementation = ApiErrorResponse.class),
                    mediaType = "application/json"))
    })
    @DeleteMapping(produces = "application/json")
    public LinkResponse deleteLink(
        @RequestHeader(required = true, value = "Tg-Chat-Id") long chatId, 
        @RequestBody(required = true) RemoveLinkRequest request);

}