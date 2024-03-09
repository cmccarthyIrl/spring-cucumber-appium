#!/bin/bash

# Install Android SDK Platform Tools if not already installed
if ! command -v adb &> /dev/null; then
    echo "Installing Android SDK Platform Tools..."
    yes | $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --install "platform-tools" --verbose > /dev/null
fi

# Find adb executable
ADB=$(find $ANDROID_HOME -name adb -type f -print -quit)

if [ -z "$ADB" ]; then
    echo "ADB not found in $ANDROID_HOME or its subdirectories."
    exit 1
fi

# Start emulator
nohup $EMULATOR -avd testRunner \
  -skin 1080x1920 \
  -no-snapshot \
  -no-audio \
  -no-boot-anim \
  -accel auto \
  -gpu auto \
  -qemu -lcd-density 420 > /dev/null 2>&1 &

# Wait for device to boot completely
$ADB wait-for-device shell 'while [[ -z $(getprop sys.boot_completed | tr -d '\r') ]]; do sleep 1; done'

# List connected devices
echo "Connected Devices:"
$ADB devices > /dev/null

echo "Emulator started"
