package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.DeleteMedicationRequest;
import com.devyanan.CareCompass.activity.results.DeleteMedicationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lambda function handler for deleting medications.
 */
public class DeleteMedicationLambda extends LambdaActivityRunner<DeleteMedicationRequest, DeleteMedicationResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteMedicationRequest>,LambdaResponse>  {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteMedicationRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<DeleteMedicationRequest> received");
        return super.runActivity(
                () -> {
                    DeleteMedicationRequest unauthenticatedRequest = input.fromPath(path ->
                            DeleteMedicationRequest.builder()
                                    .withMedicationName(path.get("medicationName"))
                                    .build());
                    return input.fromUserClaims(claims ->
                            DeleteMedicationRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .withMedicationName(unauthenticatedRequest.getMedicationName())
                                    .build());
                },
                ((request, serviceComponent) -> serviceComponent.provideDeleteMedicationActivity().handleRequest(request)));
    }
}
