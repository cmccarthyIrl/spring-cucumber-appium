#!/bin/bash
echo "y" | $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --install "system-images;android-31;default;x86_64" --verbose
echo "no" | $ANDROID_HOME/cmdline-tools/latest/bin/avdmanager -v create avd \
  -n testRunnner \
  -k "system-images;android-31;default;x86_64" \
  --force
echo "Emulators:"
$ANDROID_HOME/emulator/emulator -list-avds

nohup $ANDROID_HOME/emulator/emulator -avd testRunnner \
  -skin 1080x1920 \
  -no-snapshot \
  -no-audio \
  -no-boot-anim \
  -accel auto \
  -gpu auto
  -qemu -lcd-density 420 > /dev/null 2>&1 & \
  $ANDROID_HOME/platform-tools/adb wait-for-device shell 'while [[ -z $(getprop sys.boot_completed | tr -d '\r') ]]; do sleep 1; done'
$ANDROID_HOME/platform-tools/adb devices
echo "Emulator started"all google-chrome-stable