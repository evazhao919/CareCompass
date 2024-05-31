package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.DeleteNotificationRequest;
import com.devyanan.CareCompass.activity.results.DeleteNotificationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lambda function handler for deleting notifications.
 */
public class DeleteNotificationLambda extends LambdaActivityRunner<DeleteNotificationRequest, DeleteNotificationResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteNotificationRequest>,LambdaResponse> {
    private final Logger log = LogManager.getLogger();

    /**
     * Handles the Lambda request for deleting notifications.
     *
     * @param input   The input request containing the authenticated request and context.
     * @param context The Lambda execution context.
     * @return A LambdaResponse indicating the result of the operation.
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteNotificationRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<DeleteNotificationRequest> received");
        return super.runActivity(
                () -> {
                    DeleteNotificationRequest unauthenticatedRequest = input.fromPath(path ->
                            DeleteNotificationRequest.builder()
                                    .withNotificationId(path.get("notificationId"))
                                    .build());
                    return input.fromUserClaims(claims ->
                            DeleteNotificationRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .withNotificationId(unauthenticatedRequest.getNotificationId())
                                    .build());
                },
                ((request, serviceComponent) -> serviceComponent.provideDeleteNotificationActivity().handleRequest(request)));
    }
}