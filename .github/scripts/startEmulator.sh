#!/bin/bash

echo "Install Android SDK Platform Tools if not already installed..."
if ! command -v adb &> /dev/null; then
    echo "Installing Android SDK Platform Tools..."
    yes | "$ANDROID_HOME"/cmdline-tools/latest/bin/sdkmanager --install "platform-tools" --verbose
    export PATH=$ANDROID_HOME/platform-tools/:$PATH
fi

# Find adb executable
ADB=$(find "$ANDROID_HOME" -name adb -type f -print -quit)

if [ -z "$ADB" ]; then
    echo "ADB not found in $ANDROID_HOME or its subdirectories."
    exit 1
fi

# Add adb directory to PATH
export PATH="$(dirname "$ADB"):$PATH"

echo "Start the ADB server"
$ADB start-server

# List available system images
echo "Available System Images:"
"$ANDROID_HOME"/cmdline-tools/latest/bin/sdkmanager --list --verbose | grep "system-images"

# Install the chosen system image
echo "Install SDK System Image if not already installed..."
echo "y" | "$ANDROID_HOME"/cmdline-tools/latest/bin/sdkmanager --install "system-images;android-31;default;x86_64" --verbose

echo "Create AVD..."
echo "no" | "$ANDROID_HOME"/cmdline-tools/latest/bin/avdmanager -v create avd \
  -n testRunnner \
  -k "system-images;android-31;default;x86_64" \
  -f \
  --force

echo "Emulators:"
"$ANDROID_HOME"/emulator/emulator -list-avds


nohup "$ANDROID_HOME"/emulator/emulator -avd testRunnner \
  -skin 1080x1920 \
  -no-snapshot \
  -no-audio \
  -no-boot-anim \
  -accel auto \
  -gpu auto \
  -qemu -lcd-density 420 > /dev/null 2>&1 &

# Wait for the emulator to fully boot
tries=0
while [ $tries -lt 20 ]; do
    if $ADB devices | grep -q emulator; then
        break
    fi
    echo "Waiting for emulator to be detected..."
    sleep 10
    tries=$((tries + 1))
done

if ! $ADB devices | grep -q emulator; then
    echo "Emulator not detected after multiple attempts. Exiting."
    exit 1
fi

# Additional sleep for stability
sleep 10

echo "Connected Devices:"
$ADB devices
echo "Emulator started"
