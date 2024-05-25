package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.AddNotificationRequest;
import com.devyanan.CareCompass.activity.results.AddNotificationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lambda function handler for adding notifications.
 */
public class AddNotificationLambda extends LambdaActivityRunner<AddNotificationRequest, AddNotificationResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddNotificationRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();

    /**
     * Handles the Lambda request for adding a notification.
     *
     * @param input   The input request containing the authenticated request and context.
     * @param context The Lambda execution context.
     * @return A LambdaResponse indicating the result of the operation.
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddNotificationRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<NotificationRequest> received");
        return super.runActivity(
                () -> {
                    AddNotificationRequest unauthenticatedRequest = input.fromBody(AddNotificationRequest.class);
                    return input.fromUserClaims(claims ->
                            AddNotificationRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .withNotificationId(unauthenticatedRequest.getNotificationId())
                                    .withNotificationTitle(unauthenticatedRequest.getNotificationTitle())
                                    .withReminderContent(unauthenticatedRequest.getReminderContent())
                                    .withReminderTime(unauthenticatedRequest.getReminderTime())
                                    .withReminderType(unauthenticatedRequest.getReminderType())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideAddNotificationActivity().handleRequest(request)
        );
    }
}

