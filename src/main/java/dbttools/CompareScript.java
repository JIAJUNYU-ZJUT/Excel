package dbttools;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.*;

public class CompareScript {
    public static void main(String[] args) throws IOException {

        String inp = args[0];
        String inp2 = args[1];
        String[] split = args[1].split("\\.");
        String oup = null;
        if (split.length >1){
            oup = split[0]+"2."+split[1];
        }else {
            oup = split[0]+"2";
        }
        System.out.println(inp);
        System.out.println(inp2);
        System.out.println(oup);

        FileInputStream fileInputStream = null;
        XSSFSheet sheet = null;
        FileInputStream fileInputStreamComp = null;
        XSSFSheet sheetComp = null;
        OutputStream output = null;
        try {
            fileInputStream = new FileInputStream(inp);
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            //获取sheet
            // TODO 循环获取sheet处理
            int numberOfSheets = sheets.getNumberOfSheets();
            HashMap map = new HashMap<String ,ArrayList>();

            for(int i = 0; i < numberOfSheets; i++ ){ //循环遍历excel中的sheet
                sheet = sheets.getSheetAt(i);
                String sheetName = sheet.getSheetName(); //表名称
                List list = new ArrayList(); //用来放字段
                int totalRows = sheet.getPhysicalNumberOfRows(); //获取单个sheet中的行数

                for(int p = 1; p < totalRows; p++ ){
                    XSSFRow rowData = sheet.getRow(p);
                    XSSFCell cell = rowData.getCell(0);
                    if(cell == null || cell.toString() == ""){
                        break;
                    }
                    String columnName = cell.toString();
                    list.add(columnName);
                }

                map.put(sheetName,list);

            }

            HashMap result = new HashMap<String,ArrayList>();
            fileInputStreamComp = new FileInputStream(inp2);
            output = new FileOutputStream(oup);
            XSSFWorkbook sheetsComp = new XSSFWorkbook(fileInputStreamComp);
            for(int i = 0; i < sheetsComp.getNumberOfSheets(); i++ ){ //循环遍历excel中的sheet
                sheetComp = sheetsComp.getSheetAt(i);
                int totalRows = sheetComp.getPhysicalNumberOfRows(); //获取单个sheet中的行数

                for(int p = 1; p < totalRows; p++ ){
                    XSSFRow rowData = sheetComp.getRow(p);
                    if(rowData.getCell(0) == null || rowData.getCell(0).toString() == ""){
                        break;
                    }
                    String er_table;
                    if(rowData.getCell(5) == null){
                        er_table = null;
                    }else{
                        er_table =  rowData.getCell(5).toString();
                    }

                    if("新增".equals(er_table)) continue;
                    ArrayList columns = (ArrayList) map.get(er_table);
                    if(columns == null || !columns.contains(rowData.getCell(0).toString())){
                        rowData.createCell(7).setCellValue("否");
                    }

                }
//                System.out.println(sheetName);
//                System.out.println(coluResult);

            }

            sheetsComp.write(output);
            output.flush();
            output.close();



        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("zheli");
        }




    }











}
