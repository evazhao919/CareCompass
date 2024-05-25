package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.DeleteNotificationRequest;
import com.devyanan.CareCompass.activity.results.DeleteNotificationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteNotificationLambda extends LambdaActivityRunner<DeleteNotificationRequest, DeleteNotificationResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteNotificationRequest>,LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteNotificationRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<DeleteNotificationRequest> received");
        return super.runActivity(
                () -> {
                    DeleteNotificationRequest unauthenticatedRequest = input.fromPath(path ->
                            DeleteNotificationRequest.builder()
                                    .withReminderTime(path.get("reminderTime"))
                                    .build());
                    return input.fromUserClaims(claims ->
                            DeleteNotificationRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .withReminderTime(unauthenticatedRequest.getReminderTime())
                                    .build());
                },
                ((request, serviceComponent) -> serviceComponent.provideDeleteNotificationActivity().handleRequest(request)));
    }
}