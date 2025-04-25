package xyz.zzzxb.tofucat.note;

import xyz.zzzxb.tofucat.note.process.ConfigProcess;
import xyz.zzzxb.tofucat.note.process.DirProcess;
import xyz.zzzxb.tofucat.note.process.Process;

/**
 * @author zzzxb
 * 2024/9/18
 */
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.exec(new DirProcess());
        main.exec(new ConfigProcess());
    }

    public void exec(Process process) {
        process.init();
    }
}
