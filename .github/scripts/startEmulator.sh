#!/bin/bash

echo "Install Android SDK Platform Tools if not already installed..."
if ! command -v adb &> /dev/null; then
    echo "Installing Android SDK Platform Tools..."
    yes | $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --install "platform-tools" --verbose
fi

# Find adb executable
ADB=$(find "$ANDROID_HOME" -name adb -type f -print -quit)

if [ -z "$ADB" ]; then
    echo "ADB not found in $ANDROID_HOME or its subdirectories."
    exit 1
fi

echo "Start the ADB server"
$ADB start-server

echo "y" | $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --install "system-images;android-31;default;x86_64" --verbose
echo "no" | $ANDROID_HOME/cmdline-tools/latest/bin/avdmanager -v create avd \
  -n testRunnner \
  -k "system-images;android-31;default;x86_64" \
  -f \
  --force
echo "Emulators:"
$ANDROID_HOME/emulator/emulator -list-avds

nohup $ANDROID_HOME/emulator/emulator -avd testRunnner \
  -skin 1080x1920 \
  -no-snapshot \
  -no-audio \
  -no-boot-anim \
  -accel auto \
  -gpu auto \
  -qemu -lcd-density 420 > /dev/null 2>&1 & \
  $ADB wait-for-device shell 'while [[ -z $(getprop sys.boot_completed | tr -d '\r') ]]; do sleep 1; done'

echo "Connected Devices:"
$ADB devices
echo "Emulator started"