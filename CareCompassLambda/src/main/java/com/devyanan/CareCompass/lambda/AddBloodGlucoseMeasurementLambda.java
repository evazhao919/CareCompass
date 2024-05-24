package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.AddBloodGlucoseMeasurementRequest;
import com.devyanan.CareCompass.activity.results.AddBloodGlucoseMeasurementResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddBloodGlucoseMeasurementLambda
        extends LambdaActivityRunner<AddBloodGlucoseMeasurementRequest, AddBloodGlucoseMeasurementResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddBloodGlucoseMeasurementRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddBloodGlucoseMeasurementRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<AddBloodGlucoseMeasurementRequest> received");
        return super.runActivity(
                () -> {
                    AddBloodGlucoseMeasurementRequest unauthenticatedRequest = input.fromBody(AddBloodGlucoseMeasurementRequest.class);
                    return input.fromUserClaims(claims ->
                            AddBloodGlucoseMeasurementRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .withActualCheckTime(unauthenticatedRequest.getActualCheckTime())
                                    .withGlucoseLevel(unauthenticatedRequest.getGlucoseLevel())
                                    .withGlucoseContext(unauthenticatedRequest.getGlucoseContext())
                                    .withComments(unauthenticatedRequest.getComments())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideAddBloodGlucoseMeasurementActivity().handleRequest(request)
        );
    }
}