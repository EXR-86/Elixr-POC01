import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;

/**
 * All the file operation happening in background
 */
class FileReader implements Callable<Integer> {
    private final String searchedWord;
    private final String filepath;
    private int repetitionOfSearchedWord;
    private String contentOfFile = "";

    FileReader(String filepath, String searchedWord) {
        this.filepath = filepath;
        this.searchedWord = searchedWord;
    }

    /**
     * Overridden call() from Callable
     * @return
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception {
        validateFilePath();

        return this.repetitionOfSearchedWord;
    }


    /**
     * File Path validation
     */
    public void validateFilePath() {
        if (this.filepath.endsWith(Constants.TXT_EXTENSION) || this.filepath.endsWith(Constants.JSON_EXTENSION)) {
            File checkFileAvailability = new File(this.filepath);
            if (checkFileAvailability.exists()) {
                readFileContent(checkFileAvailability);
            }
        } else {
            WordSearchApplicaton object = new WordSearchApplicaton();
            try {
                object.errorToDataBase(this.filepath);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Reading and Storing of file content
     * @param filepath
     */
    public void readFileContent(File filepath) {

        try {
            for (String temporaryMemoryToFileContent : Files.readAllLines(filepath.toPath())) {
                contentOfFile = contentOfFile+temporaryMemoryToFileContent;
            }
            contentOfFile = contentOfFile.replaceAll("[$&+,:;=?@#|'<>.^*()%!]","");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (contentOfFile != null) {
                System.out.println("got the file");
                isWordExists();
            } else {
                System.out.println(Constants.IF_FILE_IS_EMPTY);
                DataBaseHelper databaseHelperObject = new DataBaseHelper();
                databaseHelperObject.storeDataToDataBase(searchedWord, this.filepath, Constants.IF_ANY_ERROR_FOUND, 0, Constants.IF_FILE_IS_EMPTY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Searching of the word and counting its occurrence
     */
    public void isWordExists() {
        StringTokenizer t = new StringTokenizer(contentOfFile);
        String word = "";
        while (t.hasMoreTokens()) {
            word = t.nextToken();
            if (word.equals(searchedWord)) {
                this.repetitionOfSearchedWord++;
            }
        }
    }
}


