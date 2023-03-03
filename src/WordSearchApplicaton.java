/*It is the main class where I am taking the filepath as an input from user and checking the presence of seached word*/

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Main class of the Application
 */
public class WordSearchApplicaton {

    /**
     * Reading the commandline inputs given by user
     * @param args
     */
     synchronized public static void main(String[] args) {
        String filepath = null;
        String searchedWord = null;
        if (args.length == Constants.TOTAL_NO_OF_ARGUMENTS) {
            filepath = args[0];
            searchedWord = args[1];
        }
        System.out.println("Processing....");
        ExecutorService executableThreadPool = Executors.newFixedThreadPool(1);
        Future<Integer> welcomeChildThread = executableThreadPool.submit(new FileReader(filepath, searchedWord));
        int repetitionOfSearchedWord = 0;
        try {
            repetitionOfSearchedWord = welcomeChildThread.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        displayResults(filepath, searchedWord, repetitionOfSearchedWord);
        executableThreadPool.close();
    }

    /**
     * Displaying the result regards word presence
     * @param filepath
     * @param searchedWord
     * @param repetitionOfSearchedWord
     */
    public static void displayResults(String filepath, String searchedWord, int repetitionOfSearchedWord) {
        try {
            DataBaseHelper databaseObject = new DataBaseHelper();
            if (repetitionOfSearchedWord != 0) {
                System.out.println("got the word, It is  present " + repetitionOfSearchedWord + " times inside the file");
                databaseObject.storeDataToDataBase(searchedWord, filepath, Constants.SUCCESSFUL_EXECUTION, repetitionOfSearchedWord, "");
            } else {
                System.out.println(Constants.IF_WORD_IS_NOT_PRESENT);
                databaseObject.storeDataToDataBase(searchedWord, filepath, Constants.IF_ANY_ERROR_FOUND, 0, Constants.IF_WORD_IS_NOT_PRESENT);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Displaying error message if the file path is invalid
     */
    public void errorToDataBase(String filepath) throws SQLException {
        try {
            System.out.println(Constants.IF_PATH_IS_NOT_VALID);
            DataBaseHelper object = new DataBaseHelper();
            object.storeDataToDataBase("", filepath, Constants.IF_ANY_ERROR_FOUND, 0, Constants.IF_PATH_IS_NOT_VALID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
