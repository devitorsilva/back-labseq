package org.acme.DTO;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@ApplicationScoped
@Schema(name = "LabseqResponseDTO", description = "Represents the LabSeq sequence calculation response")
public class LabseqResponsetDTO {

    @Schema(description = "Execution time in milliseconds", example = "300")
    private long transactionTime;

    @Schema(description = "Error message, if any", example = "Timeout reached, returning partial calculation up to n=250000")
    private String error;

    @Schema(description = "Result of the LabSeq sequence", example = "5")
    private String result;

}
