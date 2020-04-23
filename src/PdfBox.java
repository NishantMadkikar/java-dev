//package com.zetcode;
//import java.nio.file.*;
import java.io.File;

import java.io.IOException;
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
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.text.PDFTextStripper;
public class PdfBox {

	public static void main(String[] args) throws IOException {
//		// TODO Auto-generated method stub
//		System.out.println("Pruthvi");
//		
//		
	
	//public static void testPDFBoxExtractImages() throws Exception {
	    PDDocument document = PDDocument.load(new File("D:/pruthvi/t.pdf"));
	    PDFTextStripper stripper = new PDFTextStripper();
	    //System.out.println(stripper.getFonts());
	   // String font = stripper.getFonts(document); need to test
//	    String text = stripper.getText(document);
	    //PDFTextStripper stripper = new PDFTextStripper();
        //String text = stripper.getText(document);
//        System.out.println("test is hear->>"+text);
//	    PDDocumentCatalog catalog = document.getDocumentCatalog();
//	    PDMetadata metadata = catalog.getMetadata();
//	    
//	    System.out.println("meta data is"+metadata); // got null
//	    try(InputStream is = metadata.createInputStream();
//        InputStreamReader isr = new InputStreamReader(is);
//        BufferedReader br = new BufferedReader(isr)) {
//    
//    br.lines().forEach(System.out::println);}
	    PDPageTree list = document.getPages();
	    for (PDPage page : list) {
//	    	String font = page.getResources().getFonts(); need to check
	        PDResources pdResources = page.getResources();
	        for (COSName c : pdResources.getXObjectNames()) {
	            PDXObject o = pdResources.getXObject(c);
	            if (o instanceof org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject) {
	                File file = new File("D:/outputIMG/" + System.nanoTime() + ".png");
	                ImageIO.write(((org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject)o).getImage(), "png", file);
	            }
	        }
	    }
	}

//}

}