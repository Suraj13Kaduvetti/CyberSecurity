import java.io.IOException;
import java.nio.file.*;
import java.util.Timer;
import java.util.TimerTask;

public class FileManager {

    private static final String DIRECTORY_NAME = "files";
    private static final String CONTENT = "Lorem ipsum odor amet, consectetuer adipiscing elit. Mus lectus lacinia tempor adipiscing cubilia; phasellus nisl tortor. 
	Penatibus ultricies pretium morbi senectus sociosqu, massa viverra cras. Felis morbi imperdiet hac feugiat laoreet. Sagittis mus mattis praesent habitant turpis enim finibus hendrerit. 
	Sodales nulla hac ante massa vehicula penatibus ornare fames. Nec fusce posuere diam bibendum curae varius.";
    private static final int FILE_COUNT = 10;
    private static final long FILE_CREATION_INTERVAL_MS = 30000; // 30 seconds
    private static final long FILE_DELETION_DELAY_MS = 120000; // 120 seconds

    public static void main(String[] args) {
        Path directoryPath = Paths.get(DIRECTORY_NAME);

        try {
            if (!Files.exists(directoryPath)) {
                Files.createDirectory(directoryPath);
            }
        } catch (IOException e) {
            System.err.println("Error creating directory: " + e.getMessage());
            return;
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            private int fileCount = 0;

            @Override
            public void run() {
                if (fileCount >= FILE_COUNT) {
                    timer.cancel();
                    return;
                }

                String fileName = DIRECTORY_NAME + "/file_" + fileCount + ".txt";
                try {
                    Files.write(Paths.get(fileName), getRepeatedContent().getBytes());
                    System.out.println("Created file: " + fileName);

                    // Schedule file deletion
                    Timer deletionTimer = new Timer();
                    deletionTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                Files.delete(Paths.get(fileName));
                                System.out.println("Deleted file: " + fileName);
                            } catch (IOException e) {
                                System.err.println("Error deleting file: " + e.getMessage());
                            }
                        }
                    }, FILE_DELETION_DELAY_MS);

                } catch (IOException e) {
                    System.err.println("Error creating file: " + e.getMessage());
                }
                fileCount++;
            }
        }, 0, FILE_CREATION_INTERVAL_MS);
    }

    private static String getRepeatedContent() {
        return CONTENT.repeat(10);
    }
}
