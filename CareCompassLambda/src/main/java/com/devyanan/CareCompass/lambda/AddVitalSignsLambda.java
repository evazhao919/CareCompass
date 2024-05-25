package com.devyanan.CareCompass.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.devyanan.CareCompass.activity.requests.AddVitalSignsRequest;
import com.devyanan.CareCompass.activity.results.AddVitalSignsResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddVitalSignsLambda extends LambdaActivityRunner<AddVitalSignsRequest, AddVitalSignsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddVitalSignsRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddVitalSignsRequest> input, Context context) {
        log.info("AuthenticatedLambdaRequest<AddMedicationRequest> received");
        return super.runActivity(
                () -> {
                    AddVitalSignsRequest unauthenticatedRequest = input.fromBody(AddVitalSignsRequest.class);
                    return input.fromUserClaims(claims ->
                            AddVitalSignsRequest.builder()
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
                        serviceComponent.provideAddVitalSignsRequestActivity().handleRequest(request)
        );
    }
}