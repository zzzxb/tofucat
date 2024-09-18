package xyz.zzzxb.tofucat.common.compression;

/**
 * zzzxb
 * 2023/8/10
 */
public class FileInfo {
    public String fileFullName;
    public String filename;
    public String fileType;
    public long fileSize;
    public byte[] fileContent;

    public FileInfo() {
    }

    FileInfo(String fileFullName, long fileSize) {
        this.fileSize = fileSize;
        this.fileType = fileFullName.substring(fileFullName.lastIndexOf(".") + 1);
        this.filename = fileFullName.substring(0, fileFullName.lastIndexOf("."));
        if(filename.isEmpty()) {
            this.filename = fileFullName;
            this.fileType = "";
        }
        this.fileFullName = fileFullName;
    }

    public FileInfo(String fileFullName, byte[] data) {
        this(fileFullName, data.length);
        this.fileContent = data;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileFullName() {
        return fileFullName;
    }

    public void setFileFullName(String fileFullName) {
        this.fileFullName = fileFullName;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
