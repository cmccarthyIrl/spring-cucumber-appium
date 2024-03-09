#!/bin/bash

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
$ADB devices

echo "Emulator started"
