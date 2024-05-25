package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.devyanan.CareCompass.activity.requests.DeleteVitalSignsRequest;
import com.devyanan.CareCompass.activity.results.DeleteVitalSignsResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteVitalSignsLambda extends LambdaActivityRunner<DeleteVitalSignsRequest, DeleteVitalSignsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteVitalSignsRequest>,LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteVitalSignsRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<DeleteVitalSignsRequest> received");
        return super.runActivity(
                () -> {
                    DeleteVitalSignsRequest unauthenticatedRequest = input.fromPath(path ->
                            DeleteVitalSignsRequest.builder()
                                    .withActualCheckTime(path.get("actualCheckTime"))
                                    .build());
                    return input.fromUserClaims(claims ->
                            DeleteVitalSignsRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .withActualCheckTime(unauthenticatedRequest.getActualCheckTime())
                                    .build());
                },
                ((request, serviceComponent) -> serviceComponent.provideDeleteVitalSignsActivity().handleRequest(request)));
    }
}