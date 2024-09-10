package com.enjoypartytime.testdemo.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


public class Buffers {
    public static FloatBuffer makeInterleavedBuffer(
            float[] tData,
            int triangles) {

        int dataLength = tData.length;

        final FloatBuffer interleavedBuffer = ByteBuffer.allocateDirect(dataLength * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        int tOffset = 0;

        for (int i = 0; i < triangles; i++) {
            for (int j = 0; j < 3; j++) {
                /**
                 * This doesn't seem to do make much sense for one array, but we might need to scale it up
                 */
                interleavedBuffer.put(tData, tOffset, 1);
                tOffset += 1;
            }
        }

        interleavedBuffer.position(0);
        return interleavedBuffer;
    }
}
