import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.pdfbox.text.TextPosition;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.io.FileWriter;
/**
* This is an example on how to get the x/y coordinates and size of each character in PDF
*/
public class GetCharLocationAndSize extends PDFTextStripper {
    public GetCharLocationAndSize() throws IOException {
    }
    /**
     * @throws IOException If there is an error parsing the document.
     */
    public static final JSONArray messages = new JSONArray();
    public static void getCharInfo( String[] args ) throws IOException {
        PDDocument document = null;
        String fileName = args[0];
        try {
            document = PDDocument.load( new File(fileName) );
            PDFTextStripper stripper = new GetCharLocationAndSize();
            stripper.setSortByPosition( true );
            stripper.setStartPage( 0 );
            stripper.setEndPage( document.getNumberOfPages() );
            Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
            stripper.writeText(document, dummy);
            FileWriter file = new FileWriter(args[1]);
            file.write(messages.toJSONString());
            file.close();
        }
        finally {
            if( document != null ) {
                document.close();
            }
        }
    }
    /**
     * Override the default functionality of PDFTextStripper.writeString()
     */
    
    @Override
    protected void writeString(String string, List<TextPosition> textPosition) throws IOException {
//    	System.out.println("string"+string);
//    	System.out.println("textPosition"+textPosition);
    	
    	
    	for (TextPosition text : textPosition) {
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("char",text.getUnicode());
    		jsonObject.put("x",text.getXDirAdj());
    		jsonObject.put("y",text.getYDirAdj());
    		jsonObject.put("height",text.getHeightDir());
    		jsonObject.put("width",text.getWidthDirAdj());
    		
    		messages.add(jsonObject.toJSONString());
            
//            System.out.println(text.getUnicode()+ " [(X=" + text.getXDirAdj() + ",Y=" +
//                    text.getYDirAdj() + ") height=" + text.getHeightDir() + " width=" +
//                    text.getWidthDirAdj() + "]");
//            System.out.println(text.getFont().getFontDescriptor().getFontName());
        }
       
  
    }
}