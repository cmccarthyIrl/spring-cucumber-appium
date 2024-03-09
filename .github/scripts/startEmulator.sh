#!/bin/bash

# Variables
SDK_MANAGER="$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager"
AVD_MANAGER="$ANDROID_HOME/cmdline-tools/latest/bin/avdmanager"
EMULATOR="$ANDROID_HOME/emulator/emulator"
ADB="$ANDROID_HOME/platform-tools/adb"

# Install necessary system image
echo "y" | $SDK_MANAGER --install "system-images;android-31;default;x86_64" --verbose

# Create AVD
echo "no" | $AVD_MANAGER -v create avd \
  -n testRunner \
  -k "system-images;android-31;default;x86_64" \
  --force

# List available emulators
echo "Available Emulators:"
$EMULATOR -list-avds

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
