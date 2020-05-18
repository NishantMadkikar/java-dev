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

//
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.*;
import org.apache.pdfbox.cos.COSDictionary;
import java.util.ArrayList;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.contentstream.operator.Operator;
import java.io.OutputStream;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;

public class pdfFunction {
	         
		
		public static void GetImg(String PDFpath, String Outputpath) {
			 try {
				PDDocument document = PDDocument.load(new File(PDFpath));
				PDPageTree list = document.getPages();
			    for (PDPage page : list) {
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

		   public static void cropPdf(int coordinate,String file,int pageNo){

            try {
                PDDocument outdoc = new PDDocument();
                PDDocument doc = PDDocument.load(new File(file));
                PDPage page = (PDPage) doc.getDocumentCatalog().getPages().get(pageNo);

                // List allPages = doc.getDocumentCatalog().getAllPages();
                // PDPage page = (PDPage)allPages.get(pageNo);


                PDRectangle cropBox = page.getCropBox();
                float upperRightY = cropBox.getUpperRightY();
                float lowerLeftY = cropBox.getLowerLeftY();

                System.out.println("upperRightY"+upperRightY);

                cropBox.setLowerLeftY(coordinate);

                page.setCropBox(cropBox);

                outdoc.importPage(page);

                outdoc.save("crop1.pdf");
                outdoc = new PDDocument();
                cropBox = page.getCropBox();
                cropBox.setUpperRightY(coordinate);
                cropBox.setLowerLeftY(lowerLeftY);
                page.setCropBox(cropBox);
                outdoc.importPage(page);

                outdoc.save("crop2.pdf");
                outdoc.close();


                doc.close();
                System.out.println("splitPageInHalf");
            } catch (Exception e) {
                System.out.println("splitPageInHalf"+e);
            }
           }
           public static void splitPage(String inputFile,ArrayList<String> list,boolean initFlag,boolean conditionFlag,String file,int pageNo){


                           try
                           {

                            boolean deleteFlag = initFlag;
                            boolean headerFound = false;
                            PDDocument outdoc = new PDDocument();
                            PDDocument document = PDDocument.load(new File(inputFile));
                              // document = PDDocument.load( args[0] );
                               if( document.isEncrypted() )
                               {
                                   System.err.println( "Error: Encrypted documents are not supported for this example." );
                                   System.exit( 1 );
                               }
                              // List allPages = document.getDocumentCatalog().getPages();
                               //List allPages = document.getDocumentCatalog().getAllPages();
                             //  for ( PDPage page : document.getPages() ){

                                PDPage page = (PDPage) document.getDocumentCatalog().getPages().get(pageNo);
                                PDFStreamParser parser = new PDFStreamParser(page);
                                parser.parse();

                                List tokens = parser.getTokens();
                                List newTokens = new ArrayList();
                                for( int j=0; j<tokens.size(); j++) {
                                    Object token = tokens.get( j );
                                    if( token instanceof Operator )
                                    {
                                        Operator op = (Operator)token;
                                        if(op.getName().equals( "Tj" ))
                                        {
                                            //remove the one argument to this operator
                                           //  newTokens.remove( newTokens.size() -1 );
                                           //continue;
                                           COSString previous = (COSString) tokens.get(j - 1);
                                           String string = previous.getString();
                                           System.out.println("+++++++++++++++");
                                           System.out.println(string);
                                           if(string.equals("STvCT")){
                                             deleteFlag = conditionFlag;
                                           }
                                           if(deleteFlag){
                                             newTokens.remove( newTokens.size() -1 );
                                             continue;
                                           }
                                        } else if(op.getName().equals( "TJ")) {

                                        ///////////////////////////////////////
                                        String str= "";
                                        String tempString = "";

                                        COSArray previous = (COSArray) tokens.get(j - 1);
                                        for (int k = 0; k < previous.size(); k++)
                                        {
                                            Object arrElement = previous.getObject(k);
                                            if (arrElement instanceof COSString)
                                            {
                                                COSString cosString = (COSString) arrElement;
                                                String string = cosString.getString();
                                                tempString += string;
                                                int index = list.indexOf(string);
                                                 if(index>-1 && !headerFound){
                                                  // System.out.println("%%%%%%%"+str);
                                                   headerFound=checkHeader(tokens,j,list);
                                                   if(headerFound){
                                                     System.out.println("%%%%%%%%% Hrader Found %%%%%%%");
                                                   }

                                                 }
                                            }
                                        }

                                         ///////////////////////////////////
                                        // COSArray previous = (COSArray) tokens.get(j - 1);
                                        //  COSString temp = new COSString();

                                        //  String tempString = "";
                                        //  String str= "";
                                        //  for (int t = 0; t < previous.size(); t++) {

                                        //      if (previous.get(t) instanceof COSString) {
                                        //          tempString += ((COSString) previous.get(t)).getString();
                                        //          str=((COSString) previous.get(t)).getString();
                                        //        // System.out.println("-----------");
                                        //         //System.out.println(str);
                                        //          int index = list.indexOf(str);
                                        //          if(index>-1 && !headerFound){
                                        //           // System.out.println("%%%%%%%"+str);
                                        //            headerFound=checkHeader(tokens,j,list);
                                        //            if(headerFound){
                                        //              System.out.println("%%%%%%%%% Hrader Found %%%%%%%");
                                        //            }

                                        //          }


                                        //      }
                                        //  }

                                        //  temp.append(tempString.getBytes("ISO-8859-1"));
                                        //  tempString = "";
                                        //  tempString = temp.getString();
                                         System.out.println("-----------");
                                         System.out.println(tempString);
                                         if(headerFound){
                                           // if(tempString.equals("STvCT")){

                                           deleteFlag = conditionFlag;
                                         }
                                         if(deleteFlag){
                                           newTokens.remove( newTokens.size() -1 );
                                           continue;
                                         }
                                        }
                                    }
                                    newTokens.add( token );

                                }
                                // PDStream newContents = new PDStream( document );
                                // ContentStreamWriter writer = new ContentStreamWriter( newContents.createOutputStream() );
                                // writer.writeTokens( newTokens );
                                // newContents.addCompression();
                                // page.setContents( newContents );
                                /////////////////////////////////////////////////
                                PDStream newContents = new PDStream( document );
                                PDStream updatedStream = new PDStream(document);
                                OutputStream out = updatedStream.createOutputStream(COSName.FLATE_DECODE);
                                ContentStreamWriter writer = new ContentStreamWriter(out);
                                writer.writeTokens(newTokens);
                                out.close();
                                page.setContents(updatedStream);
                             //  }

                              document.save(file);
                          } catch (Exception e) {
                            //TODO: handle exception
                          }
                          // finally
                          // {
                          //     if( document != null )
                          //     {
                          //         document.close();
                          //     }
                          // }
           }


           public static boolean checkHeader(List tokens,int l, ArrayList list) {

            boolean isHeader = false;
            int count =0;
            int headerSize = list.size();
            ////////////////////////////////////////////////
            for( int j=l; j<tokens.size(); j++) {
                Object token = tokens.get( j );
                if( token instanceof Operator )
                {
                    Operator op = (Operator)token;
                    if(op.getName().equals( "TJ")) {

                    ///////////////////////////////////////
                    String str= "";
                    String tempString = "";

                    COSArray previous = (COSArray) tokens.get(j - 1);
                    for (int k = 0; k < previous.size(); k++)
                    {
                        Object arrElement = previous.getObject(k);
                        if (arrElement instanceof COSString)
                        {
                            COSString cosString = (COSString) arrElement;
                            String string = cosString.getString();
                            tempString += string;
                            int index = list.indexOf(string);
                            if(index>-1){
                              count=count+1;
                             System.out.println("***************"+string+"---"+count);
                              if(count==headerSize){
                                isHeader=true;
                              }
                            }
                        }
                    }
                    }
                }

            }
            /////////////////////////////////////////////////
            // for( int j=k; j<tokens.size(); j++){

            //   Object token = tokens.get( j );
            //   if( token instanceof PDFOperator ){
            //       PDFOperator op = (PDFOperator)token;
            //       if(op.getOperation().equals( "TJ")) {
            //         COSArray previous = (COSArray) tokens.get(j - 1);
            //         COSString temp = new COSString();

            //         String tempString = "";
            //         String str= "";
            //         for (int t = 0; t < previous.size(); t++) {

            //             if (previous.get(t) instanceof COSString) {
            //                 tempString += ((COSString) previous.get(t)).getString();
            //                 str=((COSString) previous.get(t)).getString();
            //               // System.out.println("-----------");
            //                //System.out.println(str);
            //                 int index = list.indexOf(str);
            //                 if(index>-1){
            //                   count=count+1;
            //                  // System.out.println("***************"+str+"---"+count);
            //                   if(count==headerSize){
            //                     isHeader=true;
            //                   }
            //                 }


            //             }
            //         }
            //        }
            //   }

            // }
             System.out.println("!!!!!!!!!!!!!--"+headerSize+"count"+count);
             return isHeader;
           }

           public static void splitPdf(String inpuFile,int fromPage,int toPage,String outPutFile) {
             try {
              File file = new File(inpuFile);

              PDDocument document = PDDocument.load(file);
              System.out.println("Hello, World!");

              Splitter splitter = new Splitter();

              splitter.setStartPage(fromPage);
              splitter.setEndPage(toPage);
              splitter.setSplitAtPage(toPage - fromPage +1 );

              List<PDDocument> lst =splitter.split(document);

              PDDocument pdfDocPartial = lst.get(0);
              File f = new File(outPutFile);
              pdfDocPartial.save(f);
              System.out.println("Multiple PDF’s created---");
              document.close();
             } catch (Exception e) {
               //TODO: handle exception
             }
           }
		
		public static void main(String[] args) throws IOException {
		  
		//insert("D:/pruthvi/t.pdf","D:/outputIMG/output1.pdf","Roots",1,10,0);
			
		//GetImg("D:/pruthvi/t.pdf","D:/outputIMG/");
			
		GetCharLocationAndSize charInfo = new GetCharLocationAndSize();
		String info[] = {"D:/pruthvi/t.pdf","D:/pruthvi/j.json"};
		charInfo.getCharInfo(info);
		
		
		ReplaceText deleteData = new ReplaceText();
		String deleteInfo[] = {"D:/pruthvi/t.pdf","delete string","replace string","D:/pruthvi/output.pdf"};
		deleteData.deleteData(deleteInfo);
		
		}
		
}
