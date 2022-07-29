#!/bin/bash
USAGE="$(basename "$0") [ -h ] [ -e env ]
-- Build and publish image to docker registry
-- Flags:
      -h  shows help
      -e  environment [ dev (default), prod, ... ]"

# Default configuration
ENV=0.1.0
REGISTRY=acimmino
IMAGE_NAME=coppola
PLATFORMS=linux/amd64,linux/arm64,linux/arm/v7

# Maven using docker / local
MAVEN_DOCKER=0

# Get configuration
while getopts 'hd:e:' OPTION; do
case "$OPTION" in
    h)
    echo "$USAGE"
    exit 0
    ;;
    e)
    ENV="$OPTARG"
    ;;
esac
done

read -p 'Please include the version: ' APPVERSION

# Do login
docker login

# Multiarch builder
docker buildx use multiplatform

# Build for AMD64/ARM64 & push to private registry
docker buildx build --platform ${PLATFORMS} \
                    --tag ${REGISTRY}/${IMAGE_NAME}:${ENV} \
                    --build-arg UID=1001 --build-arg GID=1001 \
                    --build-arg BUILD_DATE=$(date -u +'%Y-%m-%dT%H:%M:%SZ') \
                    --build-arg BUILD_VERSION=$APPVERSION \
                    -f Dockerfile . --push
docker pull ${REGISTRY}/${IMAGE_NAME}:${ENV}
