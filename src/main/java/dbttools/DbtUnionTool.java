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

public class DbtUnionTool {
    public static void main(String[] args) {

        writeSource();
//        writrModel();



    }

    public static void writeSource(){
        FileInputStream fileInputStream = null;
        XSSFSheet sheet = null;
        try {
            fileInputStream = new FileInputStream("/Users/mac/Downloads/指标体系 (1).xlsx");
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            //获取sheet
            // TODO 循环获取sheet处理
            String flag = "{{ var('source').dianshang_union[0]";
            int numberOfSheets = sheets.getNumberOfSheets();
            String result = "version: 2\n" +
                    "\n" +
                    "sources:\n" +
                    "  - name: youzan \n" +
                    "    database: \""+ flag +".databases }}\"\n" +
                    "    schema: public\n" +
                    "    tables:\n";
            for(int i = 0; i < numberOfSheets; i++ ){ //循环遍历excel中的sheet
                sheet = sheets.getSheetAt(i);
                String sheetName = sheet.getSheetName(); //sheet名称
                System.out.println(sheetName);
                String[] split = sheetName.split("~");//拆分表明和注释
                String  tableNmae = split[1];
                String tableComment = split[0];
//                System.out.println(sheetName);
//                System.out.println(tableNmae);
//                System.out.println(tableComment);
                String ss = "      - name: " + tableNmae + "  #single源表\n" +
                        "        identifier: \"" + flag + ".table_prefix }}" + tableNmae + "\"\n" +
                        "        description: "+ tableComment + "\n";
                result += ss;

            }


            System.out.println(result);

            writeText(result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("zheli");
        }
    }

    public static void writrModel(){
        FileInputStream fileInputStream = null;
        XSSFSheet sheet = null;
        try {
            fileInputStream = new FileInputStream("/Users/mac/Downloads/电商指标体系.xlsx");
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
                ss+="  - name: union_" + split[1] + "\n";
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
                ss += "      - name: _yyzn_emitted_at2\n" +
                        "        description: 数据生成时间"  + "\n";
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
