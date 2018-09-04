add_library(ndk_demo SHARED hello_ndk.cpp)

target_link_libraries(ndk_demo
    android
    log)
