# OpenTelemetry Audit Environment

This is a set of sample applications and configuration to assist in the
OpenTelemetry audit process.

## Sample Details

The sample applications are based on the documentation from
[opentelemetry.io](https://opentelemetry.io/), a basic HTTP server with a single
route, `/rolldice`. Each sample is designed to function in, more or less, the
same way vis a vis OpenTelemetry instrumentation.

- Instrumentation libraries are used to instrument existing frameworks (e.g.,
  Spring or Flask)
- SDK configuration is performed in-code using defaults, and overriden at
  runtime via environment variables.
- Each SDK is configured to send data to an OpenTelemetry Collector via gRPC.

### OpenTelemetry Dependencies by Language

This lists all versions of OpenTelemetry dependencies used in sample
applications. If a version is marked 'Experimental', this means that it (or a
transitive dependency) is not yet stable.

#### .NET

- OpenTelemetry 1.8.1

#### Go

- OpenTelemetry 1.26.0
- OpenTelemetry HTTP Instrumentation 0.51.0 (Experimental)

#### Java

- OpenTelemetry 1.37.0

#### Python

- OpenTelemetry 1.24.0
- Instrumentations (Flask, WSGI) 0.45b0

## Scripts

`roll-forever-docker.sh` runs constant traffic against the sample applications
when running in local Docker.

## Docker Compose (local)

To run the sample applications locally, run the following:

```shell
cd deploy/docker
docker compose up
```

## Cloud Deployment

This repository also contains the necessary configuration to deploy the sample applications to a cloud environment. We are using Oracle Cloud Infrastructure (OCI) as the cloud provider. The specification of the environment is:

### Instances

- Oracle Linux 8 on ARM64 (Ampere)
- Gateway 2 OCPU, 16GB RAM
- Worker 1 OCPU, 8GB RAM

### Networking

- VCN with public and private subnets
- Public subnet (10.0.0.0/24) with Internet Gateway
- Private subnet (10.0.1.0/24) with NAT Gateway

### Security

- TCP 22 (SSH) allowed
- TCP 4317-4318 (OpenTelemetry Collector OTLP) allowed
- TCP 8080 (Sample Application) allowed

### Configuration

_Please see the cloud-init scripts in `deploy/vm` for details._

### Deployment Notes

The sample applications are deployed to one (or more) worker instances. The worker runs an OpenTelemetry Collector that listens for host telemetry and forwards it to the gateway instance. This instance also contains a collector. The gateway and worker collectors are secured using mTLS as well as OAuth/OIDC.

For convenience, all instances are placed into the public subnet (to avoid having bastion hosts for SSH) -- in a true production environment, the gateway collectors would not be exposed to the public internet.

### Accessing the environment

Check with Austin in Slack for access. The username for each node is 'ocp' with key-based authentication. The keys are located in a 1Password vault.

### Running a container

Be sure to run the container with the `--network host` flag to ensure that the container can communicate with the host's network stack.

```shell
docker run --network host -e OTEL_EXPORTER_OTLP_ENDPOINT=http://localhost:4317 -e OTEL_SERVICE_NAME=go -d ghcr.io/austinlparker/otel-audit:<language>
```

Each service runs on port 8080, so either run one at a time or map the container port to a different host port.
