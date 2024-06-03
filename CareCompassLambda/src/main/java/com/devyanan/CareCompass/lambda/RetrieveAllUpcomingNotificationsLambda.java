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
                        () -> {
                                RetrieveAllUpcomingNotificationsRequest.Builder requestBuilder = RetrieveAllUpcomingNotificationsRequest.builder();
                                RetrieveAllUpcomingNotificationsRequest unauthenticatedRequest = input.fromUserClaims(claims -> requestBuilder
                                                .withPatientId(claims.get("email"))
                                                .build());
                                return input.fromQuery(query -> requestBuilder
                                        .withPatientId(unauthenticatedRequest.getPatientId())
                                        .withScheduledTime(query.get("now"))
                                        .build());
        },
                        (request, serviceComponent) ->
                                serviceComponent.provideRetrieveAllUpcomingNotificationsActivity().handleRequest(request)
                );
        }
}
