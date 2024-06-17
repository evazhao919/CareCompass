package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.UpdateMedicationDetailsRequest;
import com.devyanan.CareCompass.activity.results.UpdateMedicationDetailsResult;

public class UpdateMedicationDetailsLambda extends LambdaActivityRunner<UpdateMedicationDetailsRequest, UpdateMedicationDetailsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateMedicationDetailsRequest>, LambdaResponse> {

    /**
     * Handles the Lambda request for updating medication details.
     *
     * @param input   The input request containing the authenticated request and context.
     * @param context The Lambda execution context.
     * @return A LambdaResponse indicating the result of the operation.
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateMedicationDetailsRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateMedicationDetailsRequest unauthenticatedRequest = input.fromBody(UpdateMedicationDetailsRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateMedicationDetailsRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .withMedicationId(unauthenticatedRequest.getMedicationId())
                                    .withMedicationName(unauthenticatedRequest.getMedicationName())
                                    .withPrescription(unauthenticatedRequest.getPrescription())
                                    .withInstructions(unauthenticatedRequest.getInstructions())
                                    .withMedicationStatus(unauthenticatedRequest.getMedicationStatus())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateMedicationDetailsActivity().handleRequest(request)
        );
    }
}