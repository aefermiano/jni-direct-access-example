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

import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;

import java.nio.ByteBuffer;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class App {
    private static final int NUMBER_OF_ELEMENTS = 50;
    private static final int UPPER_BOUND = 100;
    private static final double PRECISION = 0.1;
    private static final int INT_SIZE_IN_BYTES = Integer.SIZE / 8;
    private static final String TEST_STRING = "this is a test string";
    private static final MutableDirectBuffer buffer;

    static {
        final int bufferSize = Math.max(NUMBER_OF_ELEMENTS * INT_SIZE_IN_BYTES, TEST_STRING.length() + 1);
        buffer = new UnsafeBuffer(ByteBuffer.allocateDirect(bufferSize));
    }

    public static void meanTest(){
        final Random random = new Random();

        int sum = 0;
        int offset = 0;
        for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
            final int value = random.nextInt(UPPER_BOUND);
            sum += value;
            // If you want a more fancy serialization, you can use SBE (https://github.com/real-logic/simple-binary-encoding),
            // which integrates easily with agrona buffers.
            buffer.putInt(offset, value);
            offset += INT_SIZE_IN_BYTES;
        }

        final double javaMean = ((double) sum) / NUMBER_OF_ELEMENTS;
        // Provides memory address directly, so data is shared among Java and C code.
        final double nativeMean = NativeBridge.calculateMean(buffer.addressOffset(), NUMBER_OF_ELEMENTS);

        assertEquals(javaMean, nativeMean, PRECISION);

        System.out.println("Mean test success!");
    }

    public static void stringTest(){
        int offset = 0;
        offset += buffer.putStringWithoutLengthAscii(0, TEST_STRING);
        // Make it end with zero so it can be directly interpreted as string in C code.
        buffer.putByte(offset, (byte)0);

        final String javaUppercase = TEST_STRING.toUpperCase();

        // Depending on your context you may choose to just keep working with the content inside the buffer
        // instead of keep spawning new strings.
        // Provides memory address directly, so data is shared among Java and C code.
        final int stringSize = NativeBridge.convertStringToUpperCase(buffer.addressOffset());
        final String nativeUppercase = buffer.getStringWithoutLengthAscii(0, stringSize);

        assertEquals(javaUppercase, nativeUppercase);

        System.out.println("Uppercase test success!");
    }

    public static void main(String[] args) {
        meanTest();
        stringTest();
    }
}
