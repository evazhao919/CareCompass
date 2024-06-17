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
                    DeleteMedicationRequest.Builder requestBuilder = DeleteMedicationRequest.builder();
                    DeleteMedicationRequest unauthenticatedRequest = input.fromUserClaims(claims -> requestBuilder
                            .withPatientId(claims.get("email"))
                            .build());

                    return input.fromPath(path -> requestBuilder
                            .withPatientId(unauthenticatedRequest.getPatientId())
                            .withMedicationId(path.get("medicationId"))
                            .build());
                },
                ((request, serviceComponent) -> serviceComponent.provideDeleteMedicationActivity().handleRequest(request)));
    }
}