#!/bin/bash

# Woo hoo! To the moon… 🚀
./gradlew prepareRelease
git push
./gradlew release
./gradlew nextIteration
git push --no-verify
