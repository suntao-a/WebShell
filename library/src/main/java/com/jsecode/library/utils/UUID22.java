package com.jsecode.library.utils;

import java.util.UUID;

public class UUID22 {

    /**
     * 采用URL Base64字符，即把“+/”换成“-_”
     */
    static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_=".toCharArray();

    /**
     * Base64 编码
     *
     * @param data byte[]
     * @return char[]
     */
    private char[] encode(byte[] data) {
        char[] out = new char[((data.length + 2) / 3) * 4];
        boolean quad, trip;
        for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
            quad = trip = false;
            int val = (0xFF & (int) data[i]);
            val <<= 8;
            if ((i + 1) < data.length) {
                val |= (0xFF & (int) data[i + 1]);
                trip = true;
            }
            val <<= 8;
            if ((i + 2) < data.length) {
                val |= (0xFF & (int) data[i + 2]);
                quad = true;
            }
            out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 1] = alphabet[val & 0x3F];
            val >>= 6;
            out[index] = alphabet[val & 0x3F];
        }
        return out;
    }

    /**
     * 转成字节
     *
     * @return byte[]
     */
    private byte[] toBytes() {
        UUID uuid = UUID.randomUUID();
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) ((msb >>> 8 * (7 - i)) & 0xFF);
            buffer[i + 8] = (byte) ((lsb >>> 8 * (7 - i)) & 0xFF);
        }
        return buffer;
    }

    /**
     * UUID
     *
     * @return UUID
     */
    public String getUUID() {
        char[] res = encode(toBytes());
        return new String(res, 0, res.length - 2);
    }

    /**
     * 将随机UUID转换成22位字符串
     *
     * @return UUID22
     */
    public static String getUUID22() {
        UUID uuid = UUID.randomUUID();
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        char[] out = new char[24];
        int tmp, idx = 0;
        // 基础写法
        /*
         * tmp = (int) ((msb >>> 40) & 0xffffff); out[idx + 3] = alphabet[tmp &
		 * 0x3f]; tmp >>= 6; out[idx + 2] = alphabet[tmp & 0x3f]; tmp >>= 6;
		 * out[idx + 1] = alphabet[tmp & 0x3f]; tmp >>= 6; out[idx] =
		 * alphabet[tmp & 0x3f]; idx += 4;
		 * 
		 * tmp = (int) ((msb >>> 16) & 0xffffff); out[idx + 3] = alphabet[tmp &
		 * 0x3f]; tmp >>= 6; out[idx + 2] = alphabet[tmp & 0x3f]; tmp >>= 6;
		 * out[idx + 1] = alphabet[tmp & 0x3f]; tmp >>= 6; out[idx] =
		 * alphabet[tmp & 0x3f]; idx += 4;
		 * 
		 * tmp = (int) (((msb & 0xffff) << 8) | (lsb >>> 56 & 0xff)); out[idx +
		 * 3] = alphabet[tmp & 0x3f]; tmp >>= 6; out[idx + 2] = alphabet[tmp &
		 * 0x3f]; tmp >>= 6; out[idx + 1] = alphabet[tmp & 0x3f]; tmp >>= 6;
		 * out[idx] = alphabet[tmp & 0x3f]; idx += 4;
		 * 
		 * tmp = (int) ((lsb >>> 32) & 0xffffff); out[idx + 3] = alphabet[tmp &
		 * 0x3f]; tmp >>= 6; out[idx + 2] = alphabet[tmp & 0x3f]; tmp >>= 6;
		 * out[idx + 1] = alphabet[tmp & 0x3f]; tmp >>= 6; out[idx] =
		 * alphabet[tmp & 0x3f]; idx += 4;
		 * 
		 * tmp = (int) ((lsb >>> 8) & 0xffffff); out[idx + 3] = alphabet[tmp &
		 * 0x3f]; tmp >>= 6; out[idx + 2] = alphabet[tmp & 0x3f]; tmp >>= 6;
		 * out[idx + 1] = alphabet[tmp & 0x3f]; tmp >>= 6; out[idx] =
		 * alphabet[tmp & 0x3f]; idx += 4;
		 * 
		 * tmp = (int) (lsb & 0xff); out[idx + 3] = alphabet[64]; out[idx + 2] =
		 * alphabet[64]; tmp <<= 4; out[idx + 1] = alphabet[tmp & 0x3f]; tmp >>=
		 * 6; out[idx] = alphabet[tmp & 0x3f]; idx += 4;
		 */

        // 循环写法
        int bit = 0, bt1 = 8, bt2 = 8;
        int mask, offsetM, offsetL;

        for (; bit < 16; bit += 3, idx += 4) {
            offsetM = 64 - (bit + 3) * 8;
            tmp = 0;

            if (bt1 > 3) {
                mask = (1 << 8 * 3) - 1;
            } else if (bt1 >= 0) {
                mask = (1 << 8 * bt1) - 1;
                bt2 -= 3 - bt1;
            } else {
                mask = (1 << 8 * ((bt2 > 3) ? 3 : bt2)) - 1;
                bt2 -= 3;
            }
            if (bt1 > 0) {
                bt1 -= 3;
                tmp = (int) ((offsetM < 0) ? msb : (msb >>> offsetM) & mask);
                if (bt1 < 0) {
                    tmp <<= Math.abs(offsetM);
                    mask = (1 << 8 * Math.abs(bt1)) - 1;
                }
            }
            if (offsetM < 0) {
                offsetL = 64 + offsetM;
                tmp |= ((offsetL < 0) ? lsb : (lsb >>> offsetL)) & mask;
            }

            if (bit == 15) {
                out[idx + 3] = alphabet[64];
                out[idx + 2] = alphabet[64];
                tmp <<= 4;
            } else {
                out[idx + 3] = alphabet[tmp & 0x3f];
                tmp >>= 6;
                out[idx + 2] = alphabet[tmp & 0x3f];
                tmp >>= 6;
            }
            out[idx + 1] = alphabet[tmp & 0x3f];
            tmp >>= 6;
            out[idx] = alphabet[tmp & 0x3f];
        }

        return new String(out, 0, 22);
    }
}