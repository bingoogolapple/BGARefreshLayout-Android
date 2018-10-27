#!/bin/bash +x

./gradlew :library:clean :library:build :library:bintrayUpload -PpublishAar