#!/bin/bash

check_status() {
  if [ $? -ne 0 ]; then
    echo "Error: Command '$1' failed."
    exit 1
  fi
}

# Set version to a non-snapshot build
./gradlew prepareRelease
check_status "./gradlew prepareRelease"
# Push updated version commit to remote
git push
check_status "git push"
# Release the build using jReleaser on Homebrew
./gradlew release
check_status "./gradlew release"
# Update version to a snapshot build
./gradlew nextIteration
check_status "./gradlew nextIteration"
# Push updated version commit to remote
git push --no-verify
check_status "git push --no-verify"
