upload-config:
	oci os object put --bucket-name config --file ./config/gateway-config.yaml
	oci os object put --bucket-name config --file ./config/worker-config.yaml

upload-collector:
	oci os object put --bucket-name otelcol-audit --file "$$(ls ./collector/distributions/dist/otelcol-audit*.rpm)"
