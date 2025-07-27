package com.harsh.patientservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpcClient
{
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient (
            @Value("${billing.service.address:localhost}") String serveraddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort
    )
    {
        log.info("Connecting to Billing Service GRPC server at {}:{}", serveraddress, serverPort);

        ManagedChannel channel= ManagedChannelBuilder
                .forAddress(serveraddress, serverPort)
                .usePlaintext()
                .build();

        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patientId, String name,
                                                String email)
    {
        BillingRequest request =
                BillingRequest.newBuilder()
                        .setPatientId(patientId)
                        .setName(name)
                        .setEmail(email)
                        .build();

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Billing response received from billing service via GRPC: {}", response.toString());
        return response;
    }
}
