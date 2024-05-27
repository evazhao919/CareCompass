package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.RetrieveCurrentMedicationsRequest;
import com.devyanan.CareCompass.activity.results.RetrieveCurrentMedicationsResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RetrieveCurrentMedicationsLambda extends LambdaActivityRunner<RetrieveCurrentMedicationsRequest,
        RetrieveCurrentMedicationsResult> implements RequestHandler<LambdaRequest<RetrieveCurrentMedicationsRequest>,
        LambdaResponse> {
    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<RetrieveCurrentMedicationsRequest> input, Context context) {
        log.info("LambdaRequest received");
        return super.runActivity(
                () -> {
                    log.info("RetrieveCurrentMedicationsRequest created from user request");
                    return input.fromPath(path -> RetrieveCurrentMedicationsRequest.builder()
                            .withMedicationStatus(path.get("medicationStatus")).build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideRetrieveCurrentMedicationsActivity().handleRequest(request)
        );
    }
}