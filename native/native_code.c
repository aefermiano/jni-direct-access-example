// Copyright (C) 2022 Antonio Fermiano
//
// This file is part of jni-direct-access-example.
//
// jni-direct-access-example is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// jni-direct-access-example is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with jni-direct-access-example.  If not, see <http://www.gnu.org/licenses/>.

#include "com_afermiano_jnidirectaccessexample_NativeBridge.h"

#include <ctype.h>

JNIEXPORT jdouble JNICALL Java_com_afermiano_jnidirectaccessexample_NativeBridge_calculateMean
  (JNIEnv * env, jclass obj, jlong address, jint sizeInSamples)
{
    int *ptr = (int *) address;
    int sum = 0;
    for (int i = 0; i < sizeInSamples; i++, ptr++)
    {
        sum += *ptr;
    }

    return ((double) sum) / sizeInSamples;
}

JNIEXPORT jint JNICALL Java_com_afermiano_jnidirectaccessexample_NativeBridge_convertStringToUpperCase
  (JNIEnv *env, jclass obj, jlong address)
{
    char *ptr = (char *)address;
    // Dangerous because it should receive a limit parameter, but this is just an example.
    for(; *ptr != 0x00; ptr++){
        *ptr = toupper(*ptr);
    }
    
   return (ptr - (char *)address);
}
