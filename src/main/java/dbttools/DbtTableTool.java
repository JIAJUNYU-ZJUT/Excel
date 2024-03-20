package dbttools;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class DbtTableTool {
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
                System.out.println(sheetName);
                String ss = "";
                ss+="  - name: single_" + split[1] + "\n";
                ss+="    description: " + split[0] + "\n" + "    columns:" + "\n";
                String rowKey = "qq";
                String rowValue = "qq";
                int j = 0;
                ArrayList<ArrayList<String>> dataList = new ArrayList();
                for(int p = 1; p < totalRows; p++ ){
                    XSSFRow rowData = sheet.getRow(p);
                    if(rowData.getCell(0) == null || rowData.getCell(0).toString() == ""){
                        break;
                    }
                    rowKey ="      - name: " + rowData.getCell(0).toString() + "\n";
                    rowValue ="        description: " + rowData.getCell(2).toString() + "\n";
                    ss += rowKey + rowValue;
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
        Writer writer = new FileWriter(new File("/Users/mac/Downloads/doudianjytable.txt"));
        writer.write(res);
        writer.close();

    }








}
