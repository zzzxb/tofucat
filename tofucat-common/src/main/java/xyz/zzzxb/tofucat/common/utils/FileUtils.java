package xyz.zzzxb.tofucat.common.utils;


import xyz.zzzxb.tofucat.common.exception.ParamException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * zzzxb
 * 2023/8/10
 */
public final class FileUtils {

    public static boolean canCreateDirectories(String dirPath) {
        if (!exists(dirPath)) {
            try {
                Path path = Files.createDirectories(Paths.get(dirPath));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }

    public static boolean canCreateFile(String filePath, String content) {
        if (!exists(filePath)) {
            write(content.getBytes(StandardCharsets.UTF_8), filePath);
            return true;
        }
        return false;
    }

    public static String joinPath(String... pathNodeNames) {
        return StringUtils.join(File.separator, pathNodeNames);
    }

    public static boolean exists(Collection<String> filepath) {
        for (String path : filepath) {
            File file = new File(path);
            CheckParamUtils.isFalse(exists(file.getAbsolutePath())).throwMessage("{} file not exists", file.getAbsolutePath());
        }
        return true;
    }

    public static boolean exists(String filepath) {
        return Files.exists(Paths.get(filepath));
    }

    public static void write(byte[] bytes, String outPath) {
        File file = new File(outPath);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFirstLine(String filePath) {
        try(Stream<String> lines = Files.lines(new File(filePath).toPath())) {
            Optional<String> first = lines.findFirst();
            return first.orElse("");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static long countLines(String filePath) {
        try(Stream<String> lines = Files.lines(new File(filePath).toPath())) {
            return lines.count();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static List<String> readLineRange(String filePath, int begin, int end) {
        List<String> contentList = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String content;
            int currentLine = 0;
            while ((content = br.readLine()) != null) {
                currentLine++;
                if(currentLine > end) return contentList;

                if (currentLine >= begin) {
                    contentList.add(content);
                }
            }
        } catch (IOException e) {
            throw new ParamException(e.getMessage());
        }
        return contentList;
    }

    public static List<String> readLine(String filePath, Integer ...lines) {
        List<Integer> list = Arrays.asList(lines);
        List<String> contentList = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String content;
            int currentLine = 0;
            while ((content = br.readLine()) != null) {
                currentLine++;
                if (list.contains(currentLine)) {
                    contentList.add(content);
                }
                if(lines.length == contentList.size()) {
                    return contentList;
                }
            }
        } catch (IOException e) {
            throw new ParamException(e.getMessage());
        }
        return contentList;
    }

    public static byte[] read(String filePath) {
        return read(new File(filePath));
    }

    public static byte[] read(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readInternal(String path) {
        InputStream inputStream = FileUtils.class.getResourceAsStream(path);
        if (inputStream == null) throw new RuntimeException(StringUtils.format("File not found: {}",
                new File(path).getAbsoluteFile()));

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(StringUtils.format("An error occurred while reading the file: {}", e.getMessage()));
        }
        return contentBuilder.toString();
    }
}
