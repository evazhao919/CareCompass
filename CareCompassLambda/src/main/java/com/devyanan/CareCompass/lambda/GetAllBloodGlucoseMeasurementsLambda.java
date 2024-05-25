package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.GetAllBloodGlucoseMeasurementsRequest;
import com.devyanan.CareCompass.activity.results.GetAllBloodGlucoseMeasurementsResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetAllBloodGlucoseMeasurementsLambda extends LambdaActivityRunner<GetAllBloodGlucoseMeasurementsRequest, GetAllBloodGlucoseMeasurementsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllBloodGlucoseMeasurementsRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllBloodGlucoseMeasurementsRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<GetAllBloodGlucoseMeasurementsRequest> received");
        return super.runActivity(
                () -> {
                    return input.fromUserClaims(claims ->
                            GetAllBloodGlucoseMeasurementsRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetAllBloodGlucoseMeasurementsActivity().handleRequest(request)
        );
    }
}
