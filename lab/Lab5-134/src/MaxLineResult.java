//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.Serializable;

class MaxLineResult implements Serializable {
    public final int lineNumber;
    public final int wordCount;
    public final String lineText;
    public final String sourcePath;

    public MaxLineResult(int lineNumber, int wordCount, String lineText, String sourcePath) {
        this.lineNumber = lineNumber;
        this.wordCount = wordCount;
        this.lineText = lineText;
        this.sourcePath = sourcePath;
    }
}
