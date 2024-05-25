package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.AddMedicationRequest;
import com.devyanan.CareCompass.activity.results.AddMedicationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddMedicationLambda extends LambdaActivityRunner<AddMedicationRequest, AddMedicationResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddMedicationRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddMedicationRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<AddMedicationRequest> received");
        return super.runActivity(
                () -> {
                    AddMedicationRequest unauthenticatedRequest = input.fromBody(AddMedicationRequest.class);
                    return input.fromUserClaims(claims ->
                            AddMedicationRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .withMedicationName(unauthenticatedRequest.getMedicationName())
                                    .withPrescription(unauthenticatedRequest.getPrescription())
                                    .withInstructions(unauthenticatedRequest.getInstructions())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideAddMedicationActivity().handleRequest(request)
        );
    }
}
