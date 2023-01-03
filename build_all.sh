#!/bin/sh

# Build all necessary images for a local run.
# Note: before building create .env files in the root and in notes-storage-app directories.

echo "Build nginx"
./nginx/build.sh

echo "Build notes service"
./build_notes_service.sh

echo "Build notes app"
./webclient/notes-storage-app/build.sh
