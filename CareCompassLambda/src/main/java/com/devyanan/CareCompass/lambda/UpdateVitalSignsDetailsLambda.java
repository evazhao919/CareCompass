package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.UpdateVitalSignsDetailsRequest;
import com.devyanan.CareCompass.activity.results.UpdateVitalSignsDetailsResult;

public class UpdateVitalSignsDetailsLambda extends LambdaActivityRunner<UpdateVitalSignsDetailsRequest, UpdateVitalSignsDetailsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateVitalSignsDetailsRequest>, LambdaResponse> {

    /**
     * Handles the Lambda request for updating notification details.
     *
     * @param input   The input request containing the authenticated request and context.
     * @param context The Lambda execution context.
     * @return A LambdaResponse indicating the result of the operation.
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateVitalSignsDetailsRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateVitalSignsDetailsRequest unauthenticatedRequest = input.fromBody(UpdateVitalSignsDetailsRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateVitalSignsDetailsRequest.builder()
                                    .withPatientId(claims.get("email"))
                                    .withActualCheckTime(unauthenticatedRequest.getActualCheckTime())
                                    .withTemperature(unauthenticatedRequest.getTemperature())
                                    .withHeartRate(unauthenticatedRequest.getHeartRate())
                                    .withPulse(unauthenticatedRequest.getPulse())
                                    .withRespiratoryRate(unauthenticatedRequest.getRespiratoryRate())
                                    .withSystolicPressure(unauthenticatedRequest.getSystolicPressure())
                                    .withDiastolicPressure(unauthenticatedRequest.getDiastolicPressure())
                                    .withMeanArterialPressure(unauthenticatedRequest.getMeanArterialPressure())
                                    .withWeight(unauthenticatedRequest.getWeight())
                                    .withPatientPosition(unauthenticatedRequest.getPatientPosition())
                                    .withBloodOxygenLevel(unauthenticatedRequest.getBloodOxygenLevel())
                                    .withOxygenTherapy(unauthenticatedRequest.getOxygenTherapy())
                                    .withFlowDelivered(unauthenticatedRequest.getFlowDelivered())
                                    .withPatientActivity(unauthenticatedRequest.getPatientActivity())
                                    .withComments(unauthenticatedRequest.getComments())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateVitalSignsDetailsActivity().handleRequest(request)
        );
    }
}
