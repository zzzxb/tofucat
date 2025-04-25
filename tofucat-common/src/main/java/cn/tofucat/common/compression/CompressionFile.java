package cn.tofucat.common.compression;

import java.util.List;

/**
 * zzzxb
 * 2023/8/10
 */
public interface CompressionFile {
    /**
     * 压缩
     * <blockquote><pre>
     *   String compressionType = ".zip";
     *   List<String> filePath = Arrays.toList("/tmp/a.txt", "/tmp/b.scv");
     *   CompressionFile cf = new CompressionFile();
     *   CompressFileInfo cfInfo = cf.compress(filePath, "~/Desktops/a" + compressionType);
     * </pre></blockquote>
     * @param filepath 文件路径
     */
    CompressFileInfo compress(List<String> filepath, String outPath);

    /**
     * 解压
     * @param filepath 压缩包路径
     */
    CompressFileInfo decompress(String filepath);
}
