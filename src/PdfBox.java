//package com.zetcode;
//import java.nio.file.*;
import java.io.File;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

//import org.apache.pdfbox.text.PDFTextStripper;
import javax.imageio.ImageIO;
//import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
//import org.apache.pdfbox.pdmodel.common.PDMetadata;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.BufferedReader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDCIDFont;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
public class PdfBox {

	public static void main(String[] args) throws IOException {

//		
//		
	
	//public static void testPDFBoxExtractImages() throws Exception {
	    PDDocument document = PDDocument.load(new File("D:/pruthvi/t.pdf"));
//	    File file = new File("sample.pdf");
//        PDDocument document = PDDocument.load(file);
//	    int p = 0;
//        for (int i = 0; i < document.getNumberOfPages(); ++i)
//        {
//            PDPage page = document.getPage(i);
//            PDResources res = page.getResources();
//            for (COSName fontName : res.getFontNames())
//            {
//            	p++;
//                PDFont font = res.getFont(fontName);
//                System.out.println(font.getName());
//
//            }
//        }
//        System.out.println("p-->"+p);
	    //	    PDFTextStripper stripper = new GetCharLocationAndSize();
//	    stripper.setSortByPosition( true );
//	    stripper.setStartPage( 0 );
//	    stripper.setEndPage( document.getNumberOfPages() );
	    PDFTextStripper stripper = new PDFTextStripper();
//	    PDPage textPage = document.getPage(2);
//	    PDPageContentStream contentStream = new PDPageContentStream(document, textPage,PDPageContentStream.AppendMode.APPEND, false);
//	    contentStream.beginText();
//	    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
//	    contentStream.newLineAtOffset(0, 0);
//	    contentStream.showText("Gibots");
//	    contentStream.endText();
//	    contentStream.close();
//	    document.save("D:/outputIMG/output1.pdf");
//	    System.out.println(stripper.getFonts());
//	    String font = stripper.getFonts(document); need to test
//	    String text = stripper.getText(document);
	    
//        String text1 = stripper.getText(document);
	    
//	    Vector<List<TextPosition>> list = stripper.myGetCharactersByArticle();
//	    
//        for (TextPosition text : textPositions) {
//            System.out.println(text.getUnicode()+ " [(X=" + text.getXDirAdj() + ",Y=" +
//                    text.getYDirAdj() + ") height=" + text.getHeightDir() + " width=" +
//                    text.getWidthDirAdj() + "]");
//        }
//        System.out.println("test is hear->>"+text);
//	    PDPageTree list = document.getPages();
//	    for (PDPage page : list) {
////	    	String font = page.getResources().getFonts(); need to check
//	        PDResources pdResources = page.getResources();
//	        for (COSName c : pdResources.getXObjectNames()) {
//	            PDXObject o = pdResources.getXObject(c);
//	            if (o instanceof org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject) {
//	                File file = new File("D:/outputIMG/" + System.nanoTime() + ".png");
//	                ImageIO.write(((org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject)o).getImage(), "png", file);
//	            }
//	        }
//	    }
//	    document.close();
	}

//}
	 protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
	        for (TextPosition text : textPositions) {
	            System.out.println(text.getUnicode()+ " [(X=" + text.getXDirAdj() + ",Y=" +
	                    text.getYDirAdj() + ") height=" + text.getHeightDir() + " width=" +
	                    text.getWidthDirAdj() + "]");
	        }
	    }
	

}