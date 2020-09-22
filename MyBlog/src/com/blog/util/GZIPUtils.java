package com.blog.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
 
/**
 * <b>字符串压缩</b>
 * <p>
 * 描述:<br>
 * 
 * @author 威 
 * <br>2020年9月17日 下午4:16:33 
 * @see
 * @since 1.0
 */
public class GZIPUtils {
    /**
     * 使用gzip进行压缩
     */
    public static String compress(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new sun.misc.BASE64Encoder().encode(out.toByteArray());
    }
 
    /**
     * 使用gzip进行解压缩
     */
    public static String uncompress(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        byte[] compressed = null;
        String decompressed = null;
        try {
            compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);
 
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            try {
                out.close();
            } catch (IOException e) {
            }
        }
        return decompressed;
    }
 
    public static void main(String[] args) {
    	String strss = "文件格式说明：10 字节的头，包含幻数、版本号以及时间戳可选的扩展头，如原文件名文件体，包括 DEFLATE 压缩的数据8 字节的尾注，包括 CRC-32 校验和以及未压缩的原始数据长尽管这种文件格式允许多个这样的数据拼接在一起，在解压时也把它们当作拼接在一起的数据，但是通常 gzip 仅仅用来压缩单个文件。多个文件的压缩归 档通常是首先将这些文件合并成一个 tar 文件，然后使用 gzip 进行压缩，最后生成的 .tar.gz 或者 .tgz 文件，这就是所谓的“tar压缩包”或者“tarball”。注意不要将 gzip 和 ZIP 压缩格式混淆。ZIP 也使用 DEFLATE 算法，而且可移植性更好，并且不需要一个外部的归档工具就可以包容多个文件。但是，由于ZIP对每个文件进行单独 压缩而没有利用文件间的冗余信息(固实压缩)，所以 ZIP 的压缩率要稍逊于 tar 压缩包。zlib 是 DEFLATE 算法的实现库，它的 API 同时支持 gzip 文件格式以及一个简化的数据流格式。zlib 数据流格式、DEFLATE 以及 gzip 文件格式均已被标准化成了，分别是 RFC 1950、RFC 1951 以及 RFC 1952。";
        StringBuilder str =
                new StringBuilder(strss);
        for(int i = 0; i < 800; i++) str.append(strss);
        //System.out.println("原字符串：" + str);
        System.out.println("原长度：" + str.length());
        String compress = GZIPUtils.compress(str.toString());
        System.out.println("压缩后字符串：" + compress);
        System.out.println("压缩后字符串长度：" + compress.length());
        //String string = GZIPUtils.uncompress(compress);
//        System.out.println("解压缩后字符串：" + string);
//        System.out.println("解压缩后字符串：" + str);
    }
}