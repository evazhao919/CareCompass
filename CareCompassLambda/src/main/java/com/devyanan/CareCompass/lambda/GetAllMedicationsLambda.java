package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.GetAllMedicationsRequest;
import com.devyanan.CareCompass.activity.results.GetAllMedicationsResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lambda function handler for retrieving all medications.
 */
public class GetAllMedicationsLambda  extends LambdaActivityRunner<GetAllMedicationsRequest, GetAllMedicationsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllMedicationsRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();

    /**
     * Handles the Lambda request for retrieving all medications.
     *
     * @param input   The input request containing the authenticated request and context.
     * @param context The Lambda execution context.
     * @return A LambdaResponse indicating the result of the operation.
     */
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