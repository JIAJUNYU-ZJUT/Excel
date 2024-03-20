package dbttools;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.tree.VariableHeightLayoutCache;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class DbtSourceTool {
    public static void main(String[] args) {

        FileInputStream fileInputStream = null;
        XSSFSheet sheet = null;
        try {
            fileInputStream = new FileInputStream("/Users/mac/Downloads/er图_2022_11_30.xlsx");
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            //获取sheet
            // TODO 循环获取sheet处理
            String flag = "{{ var('source').youzan_single[0]";
            int numberOfSheets = sheets.getNumberOfSheets();
            String result = "version: 2\n" +
                    "\n" +
                    "sources:\n" +
                    "  - name: ods #规范\n" +
                    "    database: \""+ flag +".databases }}\"\n" +
                    "    schema: public\n" +
                    "    tables:\n";
            for(int i = 0; i < numberOfSheets; i++ ){ //循环遍历excel中的sheet
                sheet = sheets.getSheetAt(i);
                String sheetName = sheet.getSheetName(); //表名称
                String ss = "      - name: " + sheetName + "  #er图表名\n" +
                        "        identifier: \"" + flag + ".table_prefix }}" + sheetName + "\"\n" +
                        "        description: \"\"\n";
                result += ss;

            }


            System.out.println(result);

            writeText(result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("zheli");
        }


    }


    public static void writeText(String res) throws Exception{
        Writer writer = new FileWriter(new File("/Users/mac/Downloads/table23.txt"));
        writer.write(res);
        writer.close();

    }








}
