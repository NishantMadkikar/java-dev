import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;

public class pdfFunction {
	
		
		public static void GetImg(String PDFpath, String Outputpath) {
			 try {
				PDDocument document = PDDocument.load(new File(PDFpath));
				PDPageTree list = document.getPages();
			    for (PDPage page : list) {
//			    	String font = page.getResources().getFonts(); need to check
			        PDResources pdResources = page.getResources();
			        for (COSName c : pdResources.getXObjectNames()) {
			            PDXObject o = pdResources.getXObject(c);
			            if (o instanceof org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject) {
			                File file = new File(Outputpath + System.nanoTime() + ".png");
			                ImageIO.write(((org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject)o).getImage(), "png", file);
			            }
			        }
			    }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		public static void insert(String PDFpath,String outputPath,String text,int page,int x,int y) {
		    try {
				PDDocument document = PDDocument.load(new File(PDFpath));
				 PDPage textPage = document.getPage(page);
				    PDPageContentStream contentStream = new PDPageContentStream(document, textPage,PDPageContentStream.AppendMode.APPEND, false);
				    contentStream.beginText();
				    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
				    contentStream.newLineAtOffset(x, y);
				    contentStream.showText(text);
				    contentStream.endText();
				    contentStream.close();
				    document.save(outputPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		public static void main(String[] args) throws IOException {
		
		insert("D:/pruthvi/t.pdf","D:/outputIMG/output1.pdf","Gibots",0,0,0);
		GetImg("D:/pruthvi/t.pdf","D:/outputIMG/");
		}
		
}
