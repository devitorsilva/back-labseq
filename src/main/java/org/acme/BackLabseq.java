package org.acme;

import io.smallrye.common.annotation.Blocking;
import jakarta.ws.rs.core.Response;
import org.acme.DTO.LabseqResponsetDTO;
import org.acme.Services.LabseqService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/labseq")
@Tag(name = "LabSeq API", description = "Endpoints for calculating the LabSeq sequence")
public class BackLabseq {

    @Inject
    LabseqService labseqService;

    @GET
    @Path("/{n}")
    @Produces(MediaType.APPLICATION_JSON)
    @Blocking
    @Operation(
            summary = "Calculate the LabSeq sequence",
            description = "Returns the result of the LabSeq sequence for a given number"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Sequence calculated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = LabseqResponsetDTO.class))
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = LabseqResponsetDTO.class))
            )
    })
    public Response calculateLabseq(
            @Parameter(description = "Number for LabSeq calculation", required = true)
            @PathParam("n") long number) {

        long start = System.currentTimeMillis();
        LabseqResponsetDTO response = labseqService.calculateLabseq(number);
        long end = System.currentTimeMillis();
        response.setTransactionTime(end - start);

        System.out.println("Execution time: " + (end - start) + " ms");

        return Response.ok(response, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/clean-cache")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Clear the sequence cache",
            description = "Clears the cached values and returns the operation status"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Cache cleared successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = LabseqResponsetDTO.class))
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal error while clearing cache",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = LabseqResponsetDTO.class))
            )
    })
    public Response cleanCache() {
        long start = System.currentTimeMillis();
        LabseqResponsetDTO response = labseqService.cleanCache();
        long end = System.currentTimeMillis();
        response.setTransactionTime(end - start);

        System.out.println("Execution time: " + (end - start) + " ms");

        return Response.ok(response, MediaType.APPLICATION_JSON).build();
    }
}
