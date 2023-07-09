import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


public class Main {
    public static void appendToTree(Path currentFile, Path root, Tree<Integer, String> tree){
        int depthLevel = currentFile.getNameCount() - root.getNameCount();
        tree.addChild(depthLevel, depthLevel + 1, currentFile.getFileName().toString());
    }
    public static void main(String[] args) throws IOException {
        Path currentDirectory = new File(new File("").getAbsolutePath()).toPath();
        FileWriter fileWriter = new FileWriter("test.txt", false);
        Tree<Integer, String> tree = new Tree<>(0, "Start of tree");

        Files.walkFileTree(currentDirectory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                appendToTree(dir, currentDirectory, tree);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                appendToTree(file, currentDirectory, tree);
                return FileVisitResult.CONTINUE;
            }
        });
        fileWriter.write(tree.subtreeToString(1));
        fileWriter.close();
    }
}