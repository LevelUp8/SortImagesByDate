import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

public class Main {

    public static void main(String[] args) {
        // Change these values to match the directory containing the image files and the output directory
        File inputDir = new File("/home/user/Temp/picture_source");
        File outputDir = new File("/home/user/Temp/picture_destination");

        // Get a list of all the PNG and JPG files in the input directory
        File[] files = inputDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg"));

        // Move the files to the output directory, preserving their original names
        for (File file : files) {

            String folderName = getFolderName(file);

            File outputSubDir = new File(outputDir + File.separator + folderName);
            // Create the output directory if it doesn't exist
            if (!outputSubDir.exists()) {
                outputSubDir.mkdirs();
            }
            File outputFile = new File(outputSubDir, file.getName());

            try {
                Files.copy(file.toPath(), outputFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Done!");
    }

    private static String getFolderName(File file) {

        SimpleDateFormat sd = new SimpleDateFormat("dd_MM_yyyy");
        BasicFileAttributes attrs = null;
        try {
            attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String res = sd.format(attrs.creationTime().toMillis());

        return res;
    }
}
