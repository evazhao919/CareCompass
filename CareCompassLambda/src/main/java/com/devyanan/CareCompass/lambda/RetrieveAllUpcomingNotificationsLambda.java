package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.RetrieveAllUpcomingNotificationsRequest;
import com.devyanan.CareCompass.activity.results.RetrieveAllUpcomingNotificationsResult;

public class RetrieveAllUpcomingNotificationsLambda extends LambdaActivityRunner<RetrieveAllUpcomingNotificationsRequest,
        RetrieveAllUpcomingNotificationsResult> implements RequestHandler<AuthenticatedLambdaRequest<RetrieveAllUpcomingNotificationsRequest>,
                LambdaResponse> {
        @Override
        public LambdaResponse handleRequest(AuthenticatedLambdaRequest<RetrieveAllUpcomingNotificationsRequest> input, Context context) {
                return super.runActivity(
                        () -> input.fromUserClaims(claims ->
                                RetrieveAllUpcomingNotificationsRequest.builder()
                                        .withPatientId(claims.get("email"))
                                        .build()),
                        (request, serviceComponent) ->
                                serviceComponent.provideRetrieveAllUpcomingNotificationsActivity().handleRequest(request)
                );
        }
}
