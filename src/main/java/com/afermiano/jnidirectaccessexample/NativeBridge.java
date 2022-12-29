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

package com.afermiano.jnidirectaccessexample;

public class NativeBridge {
    private static final String NATIVE_LIBRARY_NAME = "nativecode";

    static {
        System.loadLibrary(NATIVE_LIBRARY_NAME);
    }

    native public static double calculateMean(long address, int sizeInSamples);
    native public static int convertStringToUpperCase(long address);
}
