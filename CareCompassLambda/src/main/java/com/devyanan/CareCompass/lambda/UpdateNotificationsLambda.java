package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.UpdateNotificationsRequest;
import com.devyanan.CareCompass.activity.results.UpdateNotificationsResult;

/**
 * Lambda function handler for updating notifications.
 */
public class UpdateNotificationsLambda extends LambdaActivityRunner<UpdateNotificationsRequest, UpdateNotificationsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateNotificationsRequest>, LambdaResponse> {

    /**
     * Handles the Lambda request for updating notifications.
     *
     * @param input   The input request containing the authenticated request and context.
     * @param context The Lambda execution context.
     * @return A LambdaResponse indicating the result of the operation.
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateNotificationsRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateNotificationsRequest unauthenticatedRequest = input.fromBody(UpdateNotificationsRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateNotificationsRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .withNotificationTitle(unauthenticatedRequest.getNotificationTitle())
                                    .withReminderType(unauthenticatedRequest.getReminderType())
                                    .withReminderContent(unauthenticatedRequest.getReminderContent())
                                    .withAdditionalNotes(unauthenticatedRequest.getAdditionalNotes())
                                    .withReminderTime(unauthenticatedRequest.getReminderTime())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateNotificationsActivity().handleRequest(request)
        );
    }
}
