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
