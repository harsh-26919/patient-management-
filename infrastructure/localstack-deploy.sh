#!/bin/bash
set -e

export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=us-east-1

# Optional but helpful: Clean lingering containers/volumes
docker rm -f $(docker ps -aq --filter "name=localstack_") 2>/dev/null || true
docker volume prune -f

# Re-deploy the CloudFormation stack
aws --endpoint-url=http://localhost:4566 cloudformation delete-stack --stack-name patient-management

sleep 5

aws --endpoint-url=http://localhost:4566 cloudformation deploy \
    --stack-name patient-management \
    --template-file "./cdk.out/localStack.template.json"

# Check if the load balancer is up
aws --endpoint-url=http://localhost:4566 elbv2 describe-load-balancers \
    --query "LoadBalancers[0].DNSName" --output text
