package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.AddMedicationRequest;
import com.devyanan.CareCompass.activity.results.AddMedicationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lambda function handler for adding medication.
 */

public class AddMedicationLambda extends LambdaActivityRunner<AddMedicationRequest, AddMedicationResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddMedicationRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();

    /**
     * Handles the Lambda request for adding medication.
     *
     * @param input   The input request containing the authenticated request and context.
     * @param context The Lambda execution context.
     * @return A LambdaResponse indicating the result of the operation.
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddMedicationRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<AddMedicationRequest> received");
        log.warn("Input {}", input.toString());
        return super.runActivity(
                () -> {
                    AddMedicationRequest unauthenticatedRequest = input.fromBody(AddMedicationRequest.class);
                    return input.fromUserClaims(claims ->
                            AddMedicationRequest.builder()
                                    .withPatientId(claims.get("email"))
//                                    .withMedicationId(unauthenticatedRequest.getMedicationId())
                                    .withMedicationName(unauthenticatedRequest.getMedicationName())
                                    .withPrescription(unauthenticatedRequest.getPrescription())
                                    .withInstructions(unauthenticatedRequest.getInstructions())
                                    .withMedicationStatus(unauthenticatedRequest.getMedicationStatus())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideAddMedicationActivity().handleRequest(request)
        );
    }
}
