#!/bin/sh

# Builds all necessary images for a local run.
# Note: before building, create .env files in the root and in notes-storage-app directories.

echo "Build nginx"
cd ./nginx
./build.sh

echo "Build notes service"
cd ..
./build_notes_service.sh

echo "Build notes app"
cd ./webclient/notes-storage-app/
./build.sh
