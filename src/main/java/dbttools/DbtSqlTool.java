package dbttools;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class DbtSqlTool {
    public static void main(String[] args) {

        FileInputStream fileInputStream = null;
        XSSFSheet sheet = null;
        try {
            fileInputStream = new FileInputStream("/Users/mac/Downloads/指标体系 (1).xlsx");
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            //获取sheet
            // TODO 循环获取sheet处理
            int numberOfSheets = sheets.getNumberOfSheets();
            String result = "";
            for(int i = 0; i < numberOfSheets; i++ ){ //循环遍历excel中的sheet
                sheet = sheets.getSheetAt(i);
                String sheetName = sheet.getSheetName(); //表名称
                String[] split = sheetName.split("-");//拆分表明和注释
                int totalRows = sheet.getPhysicalNumberOfRows(); //获取单个sheet中的行数
                System.out.println("行数"+totalRows);
                String ss = "select\n";
                String rowKey = "";
                int j = 0;
                for(int p = 1; p < totalRows; p++ ){
                    XSSFRow rowData = sheet.getRow(p);
                    if(rowData.getCell(0) == null || rowData.getCell(0).toString() == ""){
                        ss += "from {{source('ods','"+split[1]+"')}}" + "\n";
                        break;
                    }
                    if(p == 1){
                        rowKey ="    " + rowData.getCell(0).toString() + "\n";
                    }
                    else{
                        rowKey ="    ," + rowData.getCell(0).toString() + "\n";
                    }

                    ss += rowKey;
                    if( p == totalRows - 1){
                        ss += "from {{source('ods','"+split[1]+"')}}" + "\n";
                        break;
                    }
                }
                result += ss + "\n" + "\n";
            }

            System.out.println(result);

            writeText(result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("zheli");
        }


    }


    public static void writeText(String res) throws Exception{
        Writer writer = new FileWriter(new File("/Users/mac/Downloads/doudianmodelsql.txt"));
        writer.write(res);
        writer.close();

    }








}
