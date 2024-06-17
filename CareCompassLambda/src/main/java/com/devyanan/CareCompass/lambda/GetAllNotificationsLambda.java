package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.GetAllNotificationsRequest;
import com.devyanan.CareCompass.activity.results.GetAllNotificationsResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lambda function handler for retrieving all notifications.
 */
public class GetAllNotificationsLambda extends LambdaActivityRunner<GetAllNotificationsRequest, GetAllNotificationsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllNotificationsRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();

    /**
     * Handles the Lambda request for retrieving all notifications.
     *
     * @param input   The input request containing the authenticated request and context.
     * @param context The Lambda execution context.
     * @return A LambdaResponse indicating the result of the operation.
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllNotificationsRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<GetAllNotificationsRequest> received");
        return super.runActivity(
                () -> {
                    return input.fromUserClaims(claims ->
                            GetAllNotificationsRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetAllNotificationsActivity().handleRequest(request)
        );
    }
}