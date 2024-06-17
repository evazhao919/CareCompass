package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.UpdateNotificationDetailsRequest;
import com.devyanan.CareCompass.activity.results.UpdateNotificationDetailsResult;

/**
 * Lambda function handler for updating notifications.
 */
public class UpdateNotificationDetailsLambda extends LambdaActivityRunner<UpdateNotificationDetailsRequest, UpdateNotificationDetailsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateNotificationDetailsRequest>, LambdaResponse> {

    /**
     * Handles the Lambda request for updating notification details.
     *
     * @param input   The input request containing the authenticated request and context.
     * @param context The Lambda execution context.
     * @return A LambdaResponse indicating the result of the operation.
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateNotificationDetailsRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateNotificationDetailsRequest unauthenticatedRequest = input.fromBody(UpdateNotificationDetailsRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateNotificationDetailsRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .withNotificationId(unauthenticatedRequest.getNotificationId())
                                    .withNotificationTitle(unauthenticatedRequest.getNotificationTitle())
                                    .withReminderContent(unauthenticatedRequest.getReminderContent())
                                    .withScheduledTime(unauthenticatedRequest.getScheduledTime())
                                    .withReminderType(unauthenticatedRequest.getReminderType())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateNotificationDetailsActivity().handleRequest(request)
        );
    }
}