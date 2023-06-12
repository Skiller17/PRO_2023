cmake_minimum_required(VERSION 3.22.1)

project("firstclient")

add_library(firstclient SHARED native-lib.cpp)

add_library(spdlog STATIC IMPORTED)
set_target_properties(spdlog PROPERTIES IMPORTED_LOCATION
        /home/gallaann/AndroidStudioProjects/libs/spdlog/build/${ANDROID_ABI}/libspdlog.a)

add_library(mbedcrypto SHARED IMPORTED)
set_target_properties(mbedcrypto PROPERTIES IMPORTED_LOCATION
        ${CMAKE_CURRENT_SOURCE_DIR}/../nativeLibs/${ANDROID_ABI}/libmbedcrypto.so)

target_include_directories(firstclient PUBLIC
        /home/gallaann/AndroidStudioProjects/libs/spdlog/spdlog/include
        /home/gallaann/AndroidStudioProjects/libs/mbedtls/mbedtls/include
        )

find_library(log-lib log)

target_link_libraries(
        firstclient
        spdlog
        mbedcrypto
        ${log-lib} )
