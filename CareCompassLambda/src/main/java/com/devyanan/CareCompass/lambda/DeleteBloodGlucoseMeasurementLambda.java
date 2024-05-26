package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.DeleteBloodGlucoseMeasurementRequest;
import com.devyanan.CareCompass.activity.results.DeleteBloodGlucoseMeasurementResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lambda function handler for deleting blood glucose measurements.
 */
public class DeleteBloodGlucoseMeasurementLambda extends LambdaActivityRunner<DeleteBloodGlucoseMeasurementRequest, DeleteBloodGlucoseMeasurementResult>
implements RequestHandler<AuthenticatedLambdaRequest<DeleteBloodGlucoseMeasurementRequest>,LambdaResponse> {
    private final Logger log = LogManager.getLogger();

    /**
     * Handles the Lambda request for deleting blood glucose measurements.
     *
     * @param input   The input request containing the authenticated request and context.
     * @param context The Lambda execution context.
     * @return A LambdaResponse indicating the result of the operation.
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteBloodGlucoseMeasurementRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<DeleteBloodGlucoseMeasurementRequest> received");
        return super.runActivity(
                () -> {
                    DeleteBloodGlucoseMeasurementRequest unauthenticatedRequest = input.fromPath(path ->
                            DeleteBloodGlucoseMeasurementRequest.builder()
                                    .withActualCheckTime(path.get("actualCheckTime"))
                                    .build());
                    return input.fromUserClaims(claims ->
                            DeleteBloodGlucoseMeasurementRequest.builder()
                            .withPatientId(claims.get("email"))
                            .withActualCheckTime(unauthenticatedRequest.getActualCheckTime())
                            .build());
                },
                ((request, serviceComponent) -> serviceComponent.provideDeleteBloodGlucoseMeasurementActivity().handleRequest(request)));
    }
}
