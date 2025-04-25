package cn.tofucat.common.compression;


import cn.tofucat.common.utils.CheckParamUtils;
import cn.tofucat.common.utils.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * zzzxb
 * 2023/8/10
 */
public class Zip implements CompressionFile {

    @Override
    public CompressFileInfo compress(List<String> filepathList, String outPath) {
        return compress(filepathList, outPath, false, 0);
    }

    @Override
    public CompressFileInfo decompress(String filepath) {
        return null;
    }

    /**
     * 压缩文件
     * @param filepathList 文件路径列表
     * @param outPath 输出路径
     * @param serial 是否按顺序生成文件名
     */
    public CompressFileInfo compress(Collection<String> filepathList, String outPath, boolean serial, int custom) {
        CheckParamUtils.collectionIsEmpty(filepathList).throwMessage("filepath is empty");
        FileUtils.exists(filepathList);

        final HashSet<String> filePathSet = new HashSet<>(filepathList);
        final LinkedList<FileInfo> fileInfos = new LinkedList<>();
        for (String s : filePathSet) {
            File file = new File(s);
            fileInfos.add(new FileInfo(file.getName(), FileUtils.read(file)));
        }

        return compressFile(fileInfos, outPath, serial, custom);
    }

    /**
     * 压缩文件, 压缩包内文件名字按顺序从 1 生成
     * @param filepathList 文件路径列表
     * @param outPath 输出路径
     * @param serial 是否按顺序生成文件名
     */
    public CompressFileInfo compress(LinkedList<String> filepathList, String outPath, boolean serial) {
        return compress(filepathList, outPath, serial, 1);
    }

    /**
     * 压缩文件, 压缩包内文件名字按顺序从 1 生成
     * @param fileInfos 文件基础信息
     * @param outPath 输出路径
     * @param serial 是否按顺序生成文件名
     */
    public CompressFileInfo compressFile(LinkedList<FileInfo> fileInfos, String outPath, boolean serial) {
        return compressFile(fileInfos, outPath, serial, 1);
    }

    /**
     * 压缩文件, 压缩包内文件名字按顺序从 1 生成
     * @param fileInfos 文件基础信息
     * @param outPath 输出路径
     * @param serial 是否按顺序生成文件名
     * @param custom 自定义序列号
     * @return CompressFileInfo
     */
    public CompressFileInfo compressFile(Collection<FileInfo> fileInfos, String outPath, boolean serial, int custom) {
        CheckParamUtils.collectionIsEmpty(fileInfos).throwMessage("filepath is empty");

        final LinkedList<String> fileNameList = new LinkedList<>();
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(Paths.get(outPath)))) {
            int index = custom;
            for (FileInfo fileInfo : fileInfos) {
                String filename = serial ?
                        (fileInfo.getFileType().isEmpty() ? String.valueOf(index) : index  + "." + fileInfo.getFileType()) :
                        fileInfo.getFileFullName();
                fileNameList.add(filename);
                zipOutputStream.putNextEntry(new ZipEntry(filename));
                // 压缩大文件的话，这里可以使用缓冲流
                zipOutputStream.write(fileInfo.getFileContent());
                zipOutputStream.closeEntry();
                index++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File file = new File(outPath);
        return new CompressFileInfo(file.getName(), file.length(), file.getAbsolutePath(), fileNameList);
    }

    public ByteArrayOutputStream compressFileToByte(Collection<FileInfo> fileInfos, boolean serial, int custom) {
        CheckParamUtils.collectionIsEmpty(fileInfos).throwMessage("filepath is empty");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(baos)) {
            int index = custom;
            for (FileInfo fileInfo : fileInfos) {
                String filename = serial ?
                        (fileInfo.getFileType().isEmpty() ? String.valueOf(index) : index  + "." + fileInfo.getFileType()) :
                        fileInfo.getFileFullName();
                zipOutputStream.putNextEntry(new ZipEntry(filename));
                // 压缩大文件的话，这里可以使用缓冲流
                zipOutputStream.write(fileInfo.getFileContent());
                zipOutputStream.closeEntry();
                index++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return baos;
    }

}
