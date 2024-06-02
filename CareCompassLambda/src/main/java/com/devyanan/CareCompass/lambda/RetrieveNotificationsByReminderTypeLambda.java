package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.RetrieveNotificationsByReminderTypeRequest;
import com.devyanan.CareCompass.activity.results.RetrieveNotificationsByReminderTypeResult;

public class RetrieveNotificationsByReminderTypeLambda extends LambdaActivityRunner<RetrieveNotificationsByReminderTypeRequest,
        RetrieveNotificationsByReminderTypeResult> implements RequestHandler<AuthenticatedLambdaRequest<RetrieveNotificationsByReminderTypeRequest>,
        LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<RetrieveNotificationsByReminderTypeRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    RetrieveNotificationsByReminderTypeRequest unauthenticatedLambdaRequest = input.fromPath(path ->
                            RetrieveNotificationsByReminderTypeRequest.builder()
                                    .withReminderType(path.get("reminderType"))
                                    .build());
                    return input.fromUserClaims(claims ->
                            RetrieveNotificationsByReminderTypeRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .withReminderType(unauthenticatedLambdaRequest.getReminderType())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideRetrieveNotificationsByReminderTypeActivity().handleRequest(request)
        );
    }
}
