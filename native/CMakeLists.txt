cmake_minimum_required(VERSION 3.10)

project(nativecode)

option(JAVA_HOME "JAVA_HOME" "/usr/lib/jvm/java")

set(CMAKE_C_FLAGS "-O3")

include_directories(nativecode ${JAVA_HOME}/include ${JAVA_HOME}/include/linux)

add_library(nativecode SHARED native_code.c com_afermiano_jnidirectaccessexample_NativeBridge.h)
