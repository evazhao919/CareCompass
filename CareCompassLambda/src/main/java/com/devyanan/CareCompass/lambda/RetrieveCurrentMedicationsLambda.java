package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.RetrieveCurrentMedicationsRequest;
import com.devyanan.CareCompass.activity.results.RetrieveCurrentMedicationsResult;

public class RetrieveCurrentMedicationsLambda extends LambdaActivityRunner<RetrieveCurrentMedicationsRequest,
        RetrieveCurrentMedicationsResult> implements RequestHandler<AuthenticatedLambdaRequest<RetrieveCurrentMedicationsRequest>,
        LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<RetrieveCurrentMedicationsRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    RetrieveCurrentMedicationsRequest unauthenticatedLambdaRequest = input.fromQuery(query ->
                            RetrieveCurrentMedicationsRequest.builder()
                                    .withMedicationStatus(query.get("medicationStatus"))
                                    .build());
                    return input.fromUserClaims(claims ->
                            RetrieveCurrentMedicationsRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .withMedicationStatus(unauthenticatedLambdaRequest.getMedicationStatus())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideRetrieveCurrentMedicationsActivity().handleRequest(request)
        );
    }
}