package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.UpdateBloodGlucoseMeasurementDetailsRequest;
import com.devyanan.CareCompass.activity.results.UpdateBloodGlucoseMeasurementDetailsResult;

public class UpdateBloodGlucoseMeasurementDetailsLambda extends LambdaActivityRunner<UpdateBloodGlucoseMeasurementDetailsRequest, UpdateBloodGlucoseMeasurementDetailsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateBloodGlucoseMeasurementDetailsRequest>, LambdaResponse> {


    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateBloodGlucoseMeasurementDetailsRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateBloodGlucoseMeasurementDetailsRequest unauthenticatedRequest = input.fromBody(UpdateBloodGlucoseMeasurementDetailsRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateBloodGlucoseMeasurementDetailsRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .withActualCheckTime(unauthenticatedRequest.getActualCheckTime())
                                    .withGlucoseLevel(unauthenticatedRequest.getGlucoseLevel())
                                    .withGlucoseContext(unauthenticatedRequest.getGlucoseContext())
                                    .withComments(unauthenticatedRequest.getComments())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateBloodGlucoseMeasurementDetailsActivity().handleRequest(request)
        );
    }
}
