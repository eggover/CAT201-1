import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Path;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    private PDFParser extractData;
    private PDFTextStripper stripper;
    private PDDocument newPdf;
    private COSDocument cosDoc;

    private String textForm;
    //   private String filePath;
    private File file;

    public Main() {
    }

    public String toText() throws IOException {
        this.stripper = null;
        this.newPdf = null;
        this.cosDoc = null;

        //set the location of the file
        file = new File(setFilePath());
        extractData = new PDFParser(new RandomAccessFile(file, "r")); //
        update for PDFBox V 2.0

        extractData.parse();

        //place the extract text to temporary pdf file
        cosDoc = extractData.getDocument();
        stripper = new PDFTextStripper();

        //to read the whole document from start page to last
        newPdf = new PDDocument(cosDoc);
        newPdf.getNumberOfPages();
        stripper.setStartPage(0);
        stripper.setEndPage(newPdf.getNumberOfPages());

        //set the text to a variable and return it
        textForm = stripper.getText(newPdf);
        return textForm;
    }

    // read the file name from fileName.txt
    public static String readTxtFile() {
        String data = null;
        try {
            File txt = new File(Paths.get("").toAbsolutePath().toString() +
                    "/fileName.txt");
            Scanner readTXT = new Scanner(txt);
            data = readTXT.nextLine();
            readTXT.close();

        }
        catch (FileNotFoundException e)  {
            System.out.println("An error occured.");
            e.printStackTrace();
        }

        return data;
    }
    // the path to the uploadedFile folder contains uploaded file
    public static String setFilePath() {
        Path cwd = Paths.get("");
        return  cwd.toAbsolutePath().toString() + "/uploadedFile/" +
                readTxtFile() + ".pdf" ;
    }

    public static String createConvertedFile() {
        Path cwd = Paths.get("");
        return  cwd.toAbsolutePath().toString() + "/convertedFile/" +
                readTxtFile() + "_converted.txt" ;
    }

    public static void writeToTXT(String file_name, String content) {

        try {
            FileWriter myWriter = new FileWriter(file_name);
            myWriter.write(content);
            myWriter.close();
            System.out.println(" and file successfully converted.");

        } catch (IOException e) {
            e.printStackTrace();
        }
//        }
    }
    public static void main(String[] args) throws IOException {
        Main Main = new Main();

        String namaFail = readTxtFile();

        String text = Main.toText();

        writeToTXT(createConvertedFile(), text);

    }
}