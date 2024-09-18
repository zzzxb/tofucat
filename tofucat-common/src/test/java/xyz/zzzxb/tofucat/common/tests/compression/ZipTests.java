package xyz.zzzxb.tofucat.common.tests.compression;

import org.junit.jupiter.api.Test;
import xyz.zzzxb.tofucat.common.compression.CompressFileInfo;
import xyz.zzzxb.tofucat.common.compression.FileInfo;
import xyz.zzzxb.tofucat.common.compression.Zip;

import java.util.LinkedList;
import java.util.List;

/**
 * zzzxb
 * 2024/9/13
 */
public class ZipTests {
    private final String outPath = "src/test/resources/donkey.zip";

    @Test
    public void compressFilePath() {
        Zip zip = new Zip();
        LinkedList<String> filePahtList = new LinkedList<>(List.of("src/test/resources/.gitkeep"));
        CompressFileInfo compress1 = zip.compress(filePahtList, outPath, true, 1024);
        System.out.println("compress1 = " + compress1);

        CompressFileInfo compress2 = zip.compress(filePahtList, outPath, false, 1024);
        System.out.println("compress2 = " + compress2);

        CompressFileInfo compress3 = zip.compress(filePahtList, outPath, true);
        System.out.println("compress3 = " + compress3);

        CompressFileInfo compress4 = zip.compress(filePahtList, outPath, false);
        System.out.println("compress4 = " + compress4);

        CompressFileInfo compress5 = zip.compress(filePahtList, outPath);
        System.out.println("compress5 = " + compress5);
    }


    @Test
    public void compressFileByte() {
        Zip zip = new Zip();
        LinkedList<FileInfo> lf = new LinkedList<>();
        lf.add(new FileInfo("a.txt", new byte[]{'a'}));
        lf.add(new FileInfo("b.txt", new byte[]{'b'}));
        CompressFileInfo compressFileInfo = zip.compressFile(lf, outPath, true);
        System.out.println(compressFileInfo);

        CompressFileInfo compressFileInfo1 = zip.compressFile(lf, outPath, true);
        System.out.println("compressFileInfo1 = " + compressFileInfo1);

        CompressFileInfo compressFileInfo2 = zip.compressFile(lf, outPath, true,  1024);
        System.out.println("compressFileInfo2 = " + compressFileInfo2);
    }
}
