#!/bin/bash

while true; do
    for port in {9000..9003}; do
        sleep $((RANDOM % 5)) # Add a random delay between 0 and 4 seconds
        curl "http://localhost:${port}/rolldice" >/dev/null 2>&1 &
    done
    wait # Wait for all background processes to finish
done