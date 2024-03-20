package dbttools;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.sound.midi.Soundbank;
import java.io.*;

import java.sql.SQLOutput;
import java.util.*;

public class CompareTwoExcel {
    public static void main(String[] args) {

        FileInputStream fileInputStream = null;
        XSSFSheet sheet = null;
        FileInputStream fileInputStreamComp = null;
        XSSFSheet sheetComp = null;
        try {
            fileInputStream = new FileInputStream("/Users/mac/Downloads/有赞er图 (1).xlsx");
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

//            System.out.println(map);
//            Set<Map.Entry> set = map.entrySet();
//            for (Map.Entry entry : set) {
//                System.out.print(entry.getKey()+":");
//                System.out.println(entry.getValue());
//            }



            HashMap result = new HashMap<String,ArrayList>();
            fileInputStreamComp = new FileInputStream("/Users/mac/Downloads/有赞指标体系2.xlsx");
            XSSFWorkbook sheetsComp = new XSSFWorkbook(fileInputStreamComp);
            for(int i = 0; i < sheetsComp.getNumberOfSheets(); i++ ){ //循环遍历excel中的sheet
                sheetComp = sheetsComp.getSheetAt(i);
                String sheetName = sheetComp.getSheetName(); //表名称
                String[] split = sheetName.split("-");//拆分表明和注释
                String modelTableName;
                if("交易基础信息".equals(split[0])){
                     modelTableName = "trades_full_order_info_order_info";
                }else if("交易订单明细".equals(split[0])){
                     modelTableName = "trades_full_order_info_orders";
                }else if("交易买家".equals(split[0])){
                     modelTableName = "trades_full_order_info_buyer_info";
                }else if("交易优惠信息".equals(split[0])){
                     modelTableName = "trades_order_promotion_order";
                }
                else{
                     modelTableName = split[1];
                }
//                System.out.println("模型表注释:"+split[0]);
//                System.out.println("模型表名称:"+modelTableName);
                int totalRows = sheetComp.getPhysicalNumberOfRows(); //获取单个sheet中的行数
                ArrayList coluResult = new ArrayList();

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

                    if(er_table == "新增") continue;
                    ArrayList columns = (ArrayList) map.get(er_table);
//                    if("user_weixin_followers_info".equals(modelTableName)) {
//                        System.out.println("user_weixin_followers_info");
//                        System.out.println(er_table);
//                        System.out.println(columns);
//                    }
                    if(columns == null || !columns.contains(rowData.getCell(0).toString())){
                        coluResult.add(rowData.getCell(0).toString());
                    }

                }
//                System.out.println(sheetName);
//                System.out.println(coluResult);
                result.put(modelTableName,coluResult);

            }
            Set<Map.Entry> set = result.entrySet();
            for (Map.Entry entry : set) {
                System.out.print(entry.getKey()+":");
                System.out.println(entry.getValue());
            }

//            System.out.println(result);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("zheli");
        }





    }


    public static void writeText(String res) throws Exception{
        Writer writer = new FileWriter(new File("/Users/mac/Downloads/modelsql.txt"));
        writer.write(res);
        writer.close();

    }








}
