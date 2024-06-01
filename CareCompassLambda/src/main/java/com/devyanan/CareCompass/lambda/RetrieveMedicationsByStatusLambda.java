package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.RetrieveMedicationsByStatusRequest;
import com.devyanan.CareCompass.activity.results.RetrieveMedicationsByStatusResult;

public class RetrieveMedicationsByStatusLambda extends LambdaActivityRunner<RetrieveMedicationsByStatusRequest,
        RetrieveMedicationsByStatusResult> implements RequestHandler<AuthenticatedLambdaRequest<RetrieveMedicationsByStatusRequest>,
        LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<RetrieveMedicationsByStatusRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    RetrieveMedicationsByStatusRequest unauthenticatedLambdaRequest = input.fromQuery(query ->
                            RetrieveMedicationsByStatusRequest.builder()
                                    .withMedicationStatus(query.get("medicationStatus"))
                                    .build());
                    return input.fromUserClaims(claims ->
                            RetrieveMedicationsByStatusRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .withMedicationStatus(unauthenticatedLambdaRequest.getMedicationStatus())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideRetrieveMedicationsByStatusActivity().handleRequest(request)
        );
    }
}