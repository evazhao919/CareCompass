package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.GetAllMedicationsRequest;
import com.devyanan.CareCompass.activity.results.GetAllMedicationsResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetAllMedicationsLambda  extends LambdaActivityRunner<GetAllMedicationsRequest, GetAllMedicationsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllMedicationsRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllMedicationsRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<GetAllMedicationsRequest> received");
        return super.runActivity(
                () -> {
                    return input.fromUserClaims(claims ->
                            GetAllMedicationsRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetAllMedicationsActivity().handleRequest(request)
        );
    }
}

//public class GetPlaylistLambda
//        extends LambdaActivityRunner<GetPlaylistRequest, GetPlaylistResult>
//        implements RequestHandler<LambdaRequest<GetPlaylistRequest>, LambdaResponse> {
//
//    private final Logger log = LogManager.getLogger();
//
//    @Override
//    public LambdaResponse handleRequest(LambdaRequest<GetPlaylistRequest> input, Context context) {
//        log.info("handleRequest");
//        return super.runActivity(
//            () -> input.fromPath(path ->
//                    GetPlaylistRequest.builder()
//                            .withId(path.get("id"))
//                            .build()),
//            (request, serviceComponent) ->
//                    serviceComponent.provideGetPlaylistActivity().handleRequest(request)
//        );
//    }
//}
