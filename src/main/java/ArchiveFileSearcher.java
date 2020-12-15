import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
public class ArchiveFileSearcher extends SimpleFileVisitor<Path>{
    public static void main(String[] args) throws IOException {
        EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        final ArchiveFileSearcher archiveFileSearcher = new ArchiveFileSearcher();
        Files.walkFileTree(Paths.get("C:\\programJava\\find"), options, 20, archiveFileSearcher);

        List<String> result = archiveFileSearcher.getArchived();
        System.out.println("All archived files:");
        for (String path : result) {
            System.out.println("\t" + path);
        }

        List<String> failed = archiveFileSearcher.getFailed();
        System.out.println("All failed files:");
        for (String path : failed) {
            System.out.println("\t" + path);
        }
    }
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException{
        if (path.getFileName().toString().toLowerCase().endsWith(".rar")||path.getFileName().toString().toLowerCase().endsWith(".zip")){
            archived.add(path.toString());
        }
        return super.visitFile(path, attrs);
    }
    public FileVisitResult  visitFileFailed (Path path, IOException ex){
        failed.add(path.toString());
        return FileVisitResult.SKIP_SUBTREE;
    }

    private List<String> archived = new ArrayList<>();
    private List<String> failed = new ArrayList<>();

    public List<String> getArchived() {
        return archived;
    }

    public List<String> getFailed() {
        return failed;
    }
}
