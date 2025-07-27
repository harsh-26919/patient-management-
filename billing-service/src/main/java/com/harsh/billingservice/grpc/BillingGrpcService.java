package com.harsh.billingservice.grpc;


import billing.BillingResponse;
import billing.BillingServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase
{
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(billing.BillingRequest billingRequest,
                                     io.grpc.stub.StreamObserver<billing.BillingResponse>
                                             responseObserver)
    {
        log.info("createBillingAccount request received {}", billingRequest.toString());

        //Business logic - e.g. save to database , perform calculations etc.

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("1234")
                .setStatus("ACTIVE")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
