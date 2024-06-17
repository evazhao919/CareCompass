package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.devyanan.CareCompass.activity.requests.DeleteVitalSignsRequest;
import com.devyanan.CareCompass.activity.results.DeleteVitalSignsResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lambda function handler for deleting vital signs.
 */
public class DeleteVitalSignsLambda extends LambdaActivityRunner<DeleteVitalSignsRequest, DeleteVitalSignsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteVitalSignsRequest>,LambdaResponse> {
    private final Logger log = LogManager.getLogger();

    /**
     * Handles the Lambda request for deleting vital signs.
     *
     * @param input   The input request containing the authenticated request and context.
     * @param context The Lambda execution context.
     * @return A LambdaResponse indicating the result of the operation.
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteVitalSignsRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<DeleteVitalSignsRequest> received");
        return super.runActivity(
                () -> {
                    DeleteVitalSignsRequest.Builder requestBuilder = DeleteVitalSignsRequest.builder();
                    DeleteVitalSignsRequest unauthenticatedRequest = input.fromUserClaims(claims -> requestBuilder
                            .withPatientId(claims.get("email"))
                            .build());
                    return input.fromPath(path -> requestBuilder
                            .withPatientId(unauthenticatedRequest.getPatientId())
                            .withActualCheckTime(path.get("actualCheckTime"))
                            .build());
                },
                ((request, serviceComponent) -> serviceComponent.provideDeleteVitalSignsActivity().handleRequest(request)));
    }
}