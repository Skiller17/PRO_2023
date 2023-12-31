
#!/bin/bash

#ABI=armaebi-v7a
#ABI=x86
ABI=arm64-v8a
#ABI=x86_64

ANDROID_NDK=$HOME/Library/Android/Sdk/ndk
TOOL_CHAIN=$HOME/Android/Sdk/ndk/23.1.7779620/build/cmake/android.toolchain.cmake
CMAKE=$HOME/Android/Sdk/cmake/3.22.1/bin/cmake

mkdir -p ${ABI}
cd ${ABI}

${CMAKE} ../../spdlog -DCMAKE_SYSTEM_NAME=Android -DCMAKE_SYSTEM_VERSION=21 \
-DANDROID_ABI=${ABI} -DCMAKE_TOOLCHAIN_FILE=${TOOL_CHAIN} -DCMAKE_CXX_FLAGS=-DSPDLOG_COMPILED_LIB

${CMAKE} --build .
