#!/bin/bash

docker image build -t zhukovsd/maze-generator:latest .
docker push zhukovsd/maze-generator:latest
