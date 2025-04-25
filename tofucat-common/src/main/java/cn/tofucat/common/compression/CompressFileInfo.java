package cn.tofucat.common.compression;

import java.util.List;

/**
 * zzzxb
 * 2023/8/10
 */
public class CompressFileInfo extends FileInfo {
    public List<String> fileListInfo;
    public String absoluteFilePath;

    CompressFileInfo(String fileFullName, long fileSize) {
        super(fileFullName, fileSize);
    }

    CompressFileInfo(String fileFullName, long fileSize, String absoluteFilePath) {
        super(fileFullName, fileSize);
        this.absoluteFilePath = absoluteFilePath;
    }

    CompressFileInfo(String fileFullName, long fileSize, String absoluteFilePath, List<String> fileListInfo) {
        super(fileFullName, fileSize);
        this.absoluteFilePath = absoluteFilePath;
        this.fileListInfo = fileListInfo;
    }

    public String getAbsoluteFilePath() {
        return absoluteFilePath;
    }

    public void setAbsoluteFilePath(String absoluteFilePath) {
        this.absoluteFilePath = absoluteFilePath;
    }

    public List<String> getFileListInfo() {
        return fileListInfo;
    }

    public void setFileListInfo(List<String> fileListInfo) {
        this.fileListInfo = fileListInfo;
    }

    @Override
    public String toString() {
        return "CompressFileInfo{" +
                "fileListInfo=" + fileListInfo +
                ", absoluteFilePath='" + absoluteFilePath + '\'' +
                ", filename='" + filename + '\'' +
                ", fileFullName='" + fileFullName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
