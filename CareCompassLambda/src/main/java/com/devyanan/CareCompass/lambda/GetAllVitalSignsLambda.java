package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.GetAllVitalSignsRequest;
import com.devyanan.CareCompass.activity.results.GetAllVitalSignsResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetAllVitalSignsLambda extends LambdaActivityRunner<GetAllVitalSignsRequest, GetAllVitalSignsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllVitalSignsRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllVitalSignsRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<GetAllVitalSignsRequest> received");
        return super.runActivity(
                () -> {
                    return input.fromUserClaims(claims ->
                            GetAllVitalSignsRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetAllVitalSignsActivity().handleRequest(request)
        );
    }
}
