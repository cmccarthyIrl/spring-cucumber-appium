#!/bin/bash

# Set up Android SDK environment variables
export ANDROID_HOME=/usr/local/lib/android/sdk
export PATH=$ANDROID_HOME/emulator/:$PATH
export PATH=$ANDROID_HOME/platform-tools/:$PATH
export PATH=$ANDROID_HOME/cmdline-tools/latest/bin/:$PATH

echo "Install Android SDK Platform Tools if not already installed..."
if ! command -v adb &> /dev/null; then
    echo "Installing Android SDK Platform Tools..."
    yes | "$ANDROID_HOME"/cmdline-tools/latest/bin/sdkmanager --install "platform-tools" --verbose
fi

# Find adb executable
ADB=$(find "$ANDROID_HOME" -name adb -type f -print -quit)

if [ -z "$ADB" ]; then
    echo "ADB not found in $ANDROID_HOME or its subdirectories."
    exit 1
fi

# Add adb directory to PATH
export PATH="$(dirname $ADB):$PATH"

# Function to check if a package is installed and install it if missing
check_and_install_package() {
    if ! command -v "$1" &>/dev/null; then
        echo "$1 is not installed. Installing it now..."
        "$ANDROID_HOME"/cmdline-tools/latest/bin/sdkmanager --install "$2" --verbose
    fi
}

# Check and install required packages
check_and_install_package "sdkmanager" "platform-tools"
check_and_install_package "avdmanager" "system-images;android-31;default;x86_64"
check_and_install_package "emulator" "emulator"

# Start the ADB server
adb start-server

# Create AVD
echo "Creating AVD..."
echo "no" | avdmanager -v create avd \
  -n testRunnner \
  -k "system-images;android-31;default;x86_64" \
  -f \
  --force

# Start emulator
echo "Starting emulator..."
nohup emulator -avd testRunnner \
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
    if adb devices | grep -q emulator; then
        break
    fi
    echo "Waiting for emulator to be detected..."
    sleep 10
    tries=$((tries + 1))
done

if ! adb devices | grep -q emulator; then
    echo "Emulator not detected after multiple attempts. Exiting."
    exit 1
fi

echo "Emulator started successfully."
