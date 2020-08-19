package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
//import org.apache.commons.io.FileUtils.cleanDirectory;


public class ReadConfigFolders {

//private static String writePath = "C:\\Apache24\\htdocs\\";
//private static String dataSource ="C:\\Apache24\\htdocs\\DataSource\\";


//private static String sourcePath ="C:\\Apache24\\htdocs\\DataSource\\";
//private static String destinationPath ="C:\\Apache24\\htdocs\\Comparison\\leftMenudata";




    public void listFilesForFolder(String sourcePath, String destinationPath)
            throws IOException {
        File folder = new File(sourcePath);

        List<String> leftMenuList = new ArrayList<String>();
        Map<String, List<String>> dataMap = new HashMap<String, List<String>>();
        Map<String, Set<String>> dataFileKeys = new HashMap<String, Set<String>>();
        Map<String, String> dataFilePathKeys = new HashMap<String, String>();

        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
// To read folder Structure and save
                leftMenuList.add(fileEntry.getName());

// Reading Config File
                String logFilePath = fileEntry.getPath();

                File logRoot = new File(logFilePath);
                for (File filesInRoot : logRoot.listFiles()) {
                    if (filesInRoot.isFile()) {
                        String fileName = filesInRoot.getName();
                        if (fileName != null) {
                            String fileExtension = getFileExtension(fileName);
                            if (fileExtension != null && fileExtension.equalsIgnoreCase(".txt")) {

                                List<String> dataList = readDataFromFile(filesInRoot.getPath());
                                System.out.println("==============|| Folder : " + fileEntry.getName() + "  ||====================");
                                for (String dataLine : dataList) {
                                    if (dataLine.contains("#")) {
                                        String[] dataArray = dataLine.split("#");
                                        String subFolderName = dataArray[dataArray.length - 1];
                                        String dataFilePath = logFilePath .concat("\\").concat(subFolderName);
                                       System.out.println("subFolderName:::"+ subFolderName);
                                        System.out.println("logFilePath:::"+ dataFilePath);

                                        File dataSource = new File(dataFilePath);
                                        if (dataSource != null && dataSource.listFiles() != null) {

                                            for (File file : dataSource
                                                    .listFiles()) {

                                                String logFileExtension = getFileExtension(file
                                                        .getName());
                                                if (logFileExtension != null && logFileExtension.equalsIgnoreCase(".log")) {
                                                    System.out.println("Loge Data path File"+ file.getPath());
                                                    String XMLFileName = fileEntry.getName().concat("_").concat(subFolderName);
                                                    String dataField = prepareDataFormatt(file.getPath(),XMLFileName,dataFilePath);
                                                    String directoryKey = fileEntry.getName();
                                                     
                                                    if (dataMap.containsKey(directoryKey)) {
                                                        List<String> data = dataMap.get(directoryKey);
                                                        data.add(dataField);
                                                        dataMap.put(directoryKey,data);
                                                    } else {
                                                        List<String> data = new ArrayList<String>();
                                                        data.add(dataField);
                                                        dataMap.put(directoryKey,data);
                                                    }

                                                } else if (logFileExtension != null
                                                        && logFileExtension
                                                        .equalsIgnoreCase(".html")) {

                                                    String directoryKey = fileEntry
                                                            .getName()
                                                            .concat("\\")
                                                            .concat(subFolderName);
                                                    String fileKey = "";

                                                    fileKey = file.getName();
                                                    if (dataFileKeys
                                                            .containsKey(directoryKey)) {
                                                        Set<String> fileKeys = dataFileKeys
                                                                .get(directoryKey);
                                                        fileKeys.add(fileKey);
                                                        dataFileKeys.put(
                                                                directoryKey,
                                                                fileKeys);
                                                    } else {
                                                        Set<String> fileKeys = new HashSet<String>();
                                                        fileKeys.add(fileKey);
                                                        dataFileKeys.put(
                                                                directoryKey,
                                                                fileKeys);
                                                    }

                                                    if (!dataFilePathKeys
                                                            .containsKey(directoryKey)) {
                                                        dataFilePathKeys.put(
                                                                directoryKey,
                                                                logFilePath);
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }

                            }

                        }

                    }
                }

            }
        }

        writeIntoFile(destinationPath.concat("\\leftMenu.txt"),
                prepareLeftMenu(leftMenuList));

        Set<String> fieleSet = dataMap.keySet();

        for (String key : fieleSet) {
            List<String> dataList = dataMap.get(key);
            String dataString = "";
            for (String tempDataString : dataList) {
                //Collections.sort(dataList);

                dataString = dataString.concat(tempDataString);
            }
// dataString= dataString.concat(dataString);
            String tableFileName = destinationPath.concat("\\").concat(
                    key.concat(".txt"));
// String tableFileName1
// ="C:\\Apache24\\htdocs\\Comparison\\leftMenudata\\".concat(key.concat(".txt"));
String imgtext = "<div><table><tr>"+




System.out.print("===========>*******"+fileName);

"<td> <img src='leftMenudata/Datasource/"+fileName+"/img/ACCEPTANCE.jpg' alt='Italian"+
"Trulli' width='200' height='200'></td>"+

"<td> <img src='leftMenudata/Datasource/"+fileName+"/img/DPL.jpg' alt='Italian"+
"Trulli' width='200' height='200'></td>"+

"<td> <img src='leftMenudata/Datasource/"+fileName+"/img/FP.jpg' alt='Italian"+
"Trulli' width='200' height='200'></td>"+

"<td> <img src='leftMenudata/Datasource/"+fileName+"/img/IPL.jpg' alt='Italian"+
"Trulli' width='200' height='200'></td>"+

"<td> <img src='leftMenudata/Datasource/"+fileName+"/img/ZSIM.jpg' alt='Italian"+
"Trulli' width='200' height='200'></td>"+





"</tr></table></div>";

            writeToFileByPath(
                    tableFileName,
                    imgtext+"<div><b>"+key+" Verifier Test Status</b></div><table class='statustable' width=100%><tr><th>Module</th><th>BRANCH</th><th>FW VERSION</th><th>TOTAL</th><th>PASS</th><th>FAIL</th><th></th><th></th><th></th><tr>"
                            .concat(dataString).concat("</table>"));

// writeToFileByPath(writePath.concat("/").concat(key).concat(".txt"),"<table class='statustable' width=100%><tr><th>BRANCH</th><th>FW VERSION</th><th>TOTAL</th><th>PASS2</th><th>FAIL<tr>".concat(dataString).concat("</table>"));

            System.out.println("done For " + key);

        }

// dataFilePathKeys dataFileKeys

// dataFilePathKeys dataFileKeys




        for(String fileKey :dataFilePathKeys.keySet()){
            Set<String> dataFilesFinalList = dataFileKeys.get(fileKey);
            String htmlFilePath = "";
            String fileContentHeader = "<!DOCTYPE html><html><head></head><body>";
            List<String> list = new ArrayList<String>(dataFilesFinalList);


            int faildVal=list.size();


            int startVal = 1;
            int j = 1;
            for(int i=1;i<=faildVal;){
                if(i+(groupingVal-1)>faildVal){
                    // dropdownVal=dropdownVal.concat("<option value="+j+">"+startVal+" to "+faildVal+"</option>");
                    try{
                        List<String> sublist= list.subList((startVal-1), faildVal);
                        prepareFinalData(sublist,destinationPath,fileKey,sourcePath,fileContentHeader,String.valueOf(j));
                    }catch(Exception exception){
                        exception.printStackTrace();
                    }

                }else{
                    // dropdownVal=dropdownVal.concat("<option value="+j+">"+startVal+" to "+(i+(groupingVal-1))+"</option>");
                    try{
                        List<String> sublist= list.subList((startVal-1), (i+(groupingVal-1)));
                        prepareFinalData(sublist,destinationPath,fileKey,sourcePath,fileContentHeader,String.valueOf(j));
                    }catch(Exception exception){
                        exception.printStackTrace();
                    }
                }
                j++;
                i= i+groupingVal;
                startVal=startVal+groupingVal;
            }

  /*for( String fileSubName :dataFilesFinalList){
   htmlFilePath=sourcePath.concat("\\").concat(fileKey).concat("\\").concat(fileSubName);
   String destSubFolderPath =  destinationPath.concat("\\").concat(fileKey.replace("\\", "_")).concat("\\");
   File subFolder = new File(destSubFolderPath);
   if (!subFolder.exists()) {
   subFolder.mkdir();
   }
   String htmlFilabsPath =  destSubFolderPath.concat(fileSubName);
   moveFileTODestination(htmlFilePath, htmlFilabsPath);
  //  fileContentHeader=fileContentHeader.concat("<iframe src='"+htmlFilabsPath+"' width='1000' height='1000'  frameborder='0'></iframe><hr>");
   fileContentHeader= fileContentHeader.concat("<div>"+fileSubName+"</div>");
  fileContentHeader=fileContentHeader.concat("<iframe src='"+fileKey.replace("\\", "_").concat("\\").concat(fileSubName)+"' width='1300' height='500'  frameborder='0'></iframe><hr>");
  }
  fileContentHeader= fileContentHeader.concat("</body></html>");
  writeToFileByPath(destinationPath.concat("//").concat(fileKey.replace("\\", "_")).concat(".html"),fileContentHeader);*/

        }

    }



    public static void  prepareFinalData(List<String> dataFilesFinalList,String destinationPath,String fileKey,String sourcePath,String fileContentHeader,String fileIndex){
        String htmlFilePath = "";
        for( String fileSubName :dataFilesFinalList){

            htmlFilePath=sourcePath.concat("\\").concat(fileKey).concat("\\").concat(fileSubName);
            String destSubFolderPath =  destinationPath.concat("\\").concat(fileKey.replace("\\", "_")).concat("\\");
            File subFolder = new File(destSubFolderPath);
            if (!subFolder.exists()) {
                subFolder.mkdir();
            }
            String htmlFilabsPath =  destSubFolderPath.concat(fileSubName);
            try {
                moveFileTODestination(htmlFilePath, htmlFilabsPath);
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
            //  fileContentHeader=fileContentHeader.concat("<iframe src='"+htmlFilabsPath+"' width='1000' height='1000'  frameborder='0'></iframe><hr>");
            fileContentHeader= fileContentHeader.concat("<div>"+fileSubName+"</div>");
            fileContentHeader=fileContentHeader.concat("<iframe src='"+fileKey.replace("\\", "_").concat("\\").concat(fileSubName)+"' width='1300' height='500'  frameborder='0'></iframe><hr>");


        }
        fileContentHeader= fileContentHeader.concat("</body></html>");
        writeToFileByPath(destinationPath.concat("//").concat(fileKey.replace("\\", "_")).concat(fileIndex+"_.html"),fileContentHeader);
        fileContentHeader = "<!DOCTYPE html><html><head></head><body>";

    }



    private List<String> prepareLeftMenu(List<String> leftMenuData) {

        List<String> sortedDate = sortLeftMenu(leftMenuData);
        List<String> finalList = new ArrayList<String>();
        if (sortedDate != null && !sortedDate.isEmpty()) {
            for (String fileIndex : sortedDate) {
                finalList.add("<a href='#' class='contentData'  val='"
                        + fileIndex + "'>" + fileIndex + "</a>");
            }
        }
        return finalList;
    }


    public static void writeIntoFile(String fileName, List<String> data) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
            if (data != null && !data.isEmpty() && writer != null) {
                for (String leftLine : data) {
                    writer.println(leftLine);
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static void writeToFileByPath(String fileName, String data) {
        PrintWriter writer = null;
        try {

            File tempFile = new File(fileName);
            boolean exists = tempFile.exists();

            if (!exists) {
                writer = new PrintWriter(fileName, "UTF-8");
                writer.println(data);
            } else {
                File fileToDelete = new File(fileName);
                fileToDelete.delete();
                writer = new PrintWriter(fileName, "UTF-8");
                writer.println(data);

            }

        } catch (Exception exception) {
            exception.printStackTrace();

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private String getFileExtension(String name) {
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }

    public static List<String> readDataFromFile(String filePath) {
        List<String> dataList = new ArrayList<String>();
        BufferedReader br = null;
        try {
            File file = new File(filePath);
            br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null)
                dataList.add(st);

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return dataList;

    }

    public static String readFileString(String filePath) {

        String fileString = "";
        BufferedReader br = null;
        try {
            File file = new File(filePath);
            br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {
                fileString = fileString.concat(st);

            }

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return fileString;

    }


    public static String prepareDataFormatt(String filePath, String fileName,String dataFilePath) {
        String dataFormate = "";
        List<String> fileData = readDataFromFile(filePath);

        String TITLE_VAL = "";// Verifier-ZSIM RESULTS
        String BRANCH_VAL = "";// :rel_x10.15.3
        String FW_VERSION_VAL = "";// :H10.15.017439
        String TOTAL_VAL = "";// :20
        String PASS2_VAL = "";// :0
        String FAIL_VAL = "";// :20
        for (String field : fileData) {
            if (field != null && field.contains(":")) {
                String[] fieldValue = field.split(":");
                if (fieldValue != null) {
                    if ("TITLE_VAL".equalsIgnoreCase(fieldValue[0])) {
                        TITLE_VAL = fieldValue[1];
                    } else if ("BRANCH_VAL".equalsIgnoreCase(fieldValue[0])) {
                        BRANCH_VAL = fieldValue[1];
                    } else if ("FW_VERSION_VAL".equalsIgnoreCase(fieldValue[0])) {
                        FW_VERSION_VAL = fieldValue[1];
                    } else if ("TOTAL_VAL".equalsIgnoreCase(fieldValue[0])) {
                        TOTAL_VAL = fieldValue[1];
                    } else if ("PASS2_VAL".equalsIgnoreCase(fieldValue[0])) {
                        PASS2_VAL = fieldValue[1];
                    } else if ("FAIL_VAL".equalsIgnoreCase(fieldValue[0])) {
                        FAIL_VAL = fieldValue[1];
                    }
                }
            }// onClick='getXML();'
        }

double totalRec = 0;
double sucessRec = 0;
double faildRec = 0;
if (!PASS2_VAL.isEmpty()) {
try {
sucessRec = Integer.parseInt(PASS2_VAL);
} catch (Exception ex) {
sucessRec = 0;

}
}

if (!FAIL_VAL.isEmpty()) {
try {
faildRec = Integer.parseInt(FAIL_VAL);
} catch (Exception ex) {
faildRec = 0;
}
}

if (!TOTAL_VAL.isEmpty()) {
try {
totalRec = Integer.parseInt(TOTAL_VAL);
} catch (Exception ex) {
totalRec = 0;
}
}

int passper = (int) ((sucessRec / totalRec) * 100);
int faildPer = (int) ((faildRec / totalRec) * 100);

String htmlFilePath ="#";
String xlsFilePath ="#";

String HTMLfileName = "";
String XLSfileName = "";

String tempDataFilePath = "";
tempDataFilePath = dataFilePath.replace("C:\\Apache24\\htdocs", "C:\\httpd-2.4.39-win64-VC15\\Apache24\\htdocs\\verifier");
          try{
File htmlFolder = new File(dataFilePath.concat("\\html"));
File xlsFolder = new File(dataFilePath.concat("\\xls"));

if(htmlFolder != null && htmlFolder.listFiles().length!=0){
HTMLfileName =(htmlFolder.listFiles()[0]).getName();    

 
 htmlFilePath =tempDataFilePath.concat("\\html\\").concat(HTMLfileName);  
}

if(xlsFolder != null && xlsFolder.listFiles().length!=0){
XLSfileName =(xlsFolder.listFiles()[0]).getName();  
xlsFilePath =tempDataFilePath.concat("\\xls\\").concat(XLSfileName);  
}


          }catch(Exception exception){
         
          }
       /* dataFormate = "<tr><td>"+TITLE_VAL+"</td><td>"
                + BRANCH_VAL + "</td><td>" + FW_VERSION_VAL + "</td><td>"
                + TOTAL_VAL + "</td><td>" + PASS2_VAL + "</td><td>" + FAIL_VAL+"    "+ prepareDropGroupDropDown(Integer.parseInt(FAIL_VAL))
                + "</td> <td><div class='progress' val='"+ fileName + "'><div class='progress-bar progress-bar-danger' role='progressbar'"+ "style='width:"+faildPer+"%'>"+"</div><div class='progress-bar progress-bar-success' role='progressbar'"+ "style='width:"+passper+"%'>"+passper+"%"+"</div></td><td><a href='#'>Result Summary</a></td></tr>"; */
String finalXlsPath = "";
String finalHtmlPath ="";
try{
 finalXlsPath = "leftMenudata".concat(xlsFilePath.split("htdocs")[1]);
 finalHtmlPath = "leftMenudata".concat(htmlFilePath.split("htdocs")[1]);
}catch(Exception ex){
finalXlsPath = "";
 finalHtmlPath ="";
}
dataFormate = "<tr><td>"+TITLE_VAL+"</td><td>"
            + BRANCH_VAL + "</td><td>" + FW_VERSION_VAL + "</td><td>"
            + TOTAL_VAL + "</td><td>" + PASS2_VAL + "</td><td>" + FAIL_VAL+"    "+ prepareDropGroupDropDown(Integer.parseInt(FAIL_VAL))
            + "</td> <td><div class='progress' val='"+ fileName + "'>" +            
             "<div class='progress-bar progress-bar-success' role='progressbar'"+ "style='width:"+passper+"%'>" +passper+"%"+"</div>" +
             "<div class='progress-bar progress-bar-danger' role='progressbar'"+ "style='width:"+faildPer+"%'></div>" +
             "</td><td><div class='xlsfileclass' val='"+finalXlsPath+"'><a href='"+finalXlsPath+"'>XLS</a></div></td><td><div class='htmlclass' val = '"+finalHtmlPath+"'> <a href='"+finalHtmlPath+"'>Result Summary</a></div></td></tr>";




        return dataFormate;

    }

    static  Integer groupingVal = 150;
    private static String prepareDropGroupDropDown(int faildVal){
       

        int startVal = 1;
        int j = 1;
String  tempOption = "";
        for(int i=1;i<=faildVal;){
            if(i+(groupingVal-1)>faildVal){
               /* dropdownVal=dropdownVal.concat("<option value="+j+">"+startVal+" to "+faildVal+"</option>"); */
            }else{
                tempOption=tempOption.concat("<option value="+j+">"+startVal+" to "+(i+(groupingVal-1))+"</option>");
            }
            j++;
            i= i+groupingVal;
            startVal=startVal+groupingVal;
        }


String dropdownVal =""; /*"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select class='grpIndVal'> " ; */

           if(!tempOption.isEmpty()){
   dropdownVal = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select class='grpIndVal'> " ;
 
           dropdownVal=dropdownVal.concat(tempOption);
   dropdownVal=dropdownVal.concat("</select>");
  }


       
        return dropdownVal;
    }






    public static void moveFileTODestination(String source, String destination)
            throws IOException {
// Path temp = Files.move(Paths.get(source),Paths.get(destination));
        File file = new File(destination);
        if (file != null && file.exists()) {
            file.delete();
        }
        Path temp = Files.copy(Paths.get(source), Paths.get(destination));
        if (temp != null) {
            System.out.println(destination
                    + "    File renamed and moved successfully to "
                    + destination);
        } else {
            System.out.println("Failed to move the file");
        }
    }



    public List<String> sortLeftMenu(List<String> leftmenudata) {
        java.util.ArrayList<Date> date = new java.util.ArrayList<Date>();
        java.util.ArrayList<String> sortedData = new java.util.ArrayList<String>();
        try {
            SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
            for (String givenDate : leftmenudata) {
                date.add(dateFormate.parse(givenDate));
            }
// Collections.sort(date);
            Collections.sort(date, Collections.reverseOrder());

            for (Date tempDate : date) {
                sortedData.add(dateFormate.format(tempDate));
            }

        } catch (Exception exception) {

        }
        if (sortedData.isEmpty()) {
            return leftmenudata;
        } else {
            return sortedData;
        }

    }

    public static void main(String[] args) throws IOException {
// File folder = new File("C:\\Apache24\\htdocs\\DataSource");
ReadConfigFolders readConfigFolder = new ReadConfigFolders();
// readConfigFolder.deleteFileInFolder(destinationPath);
groupingVal = 150;

String sourcePath = "C:\\httpd-2.4.39-win64-VC15\\Apache24\\htdocs\\Datasource";
String destinationPath = "C:\\httpd-2.4.39-win64-VC15\\Apache24\\htdocs\\verifier\\leftMenudata";

//String sourcePath = "C:\\Apache24\\htdocs\\DataSource";
//String destinationPath = "C:\\Apache24\\htdocs\\verifier\\leftMenudata";

readConfigFolder.listFilesForFolder(sourcePath, destinationPath);
}



}

