package com.weige;

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

public class Start {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int i = 0;
        String fileName = "";
        String m = "-1";
        LinkedList<LinkedList<String>> res = new LinkedList<LinkedList<String>>();
        while (!"t".equalsIgnoreCase(m)) {

            if (i == 0) {
                System.out.println("请输入文件路径：");
                fileName = s.nextLine();
            } else {
//                System.out.println("请输入第" + i + "个数字,若输入完毕，请输入t");
//                m = s.next();
//                if (!"t".equalsIgnoreCase(m)) {
//                    arr.add(m);
//                }
                System.out.println("请输入要统计的行数字,以空格间隔");
                m = s.nextLine();
                break;
            }
//            if ("t".equalsIgnoreCase(m)) {
//                System.out.println("感谢'威哥'使用");
//                break;
//            }
            i++;
        }
 //      System.out.println(fileName);
//        System.out.println(arr.toString());
//        System.out.println(m);
        List<String> arr = Arrays.asList(m.split(" "));
//        for (String aa: arr
//             ) {
//            System.out.println(aa);
//        }
        FileInputStream fileInputStream = null;
        XSSFSheet sheet = null;
        try {
            fileInputStream = new FileInputStream(fileName);
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            //获取sheet
            sheet = sheets.getSheet("Table 1");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("zheli");
        }

        int totalRows = sheet.getPhysicalNumberOfRows();
        System.out.println("行数"+totalRows);
        String rowKey = "qq";
        String rowValue = "qq";
        int j = 0;
        ArrayList<ArrayList<String>> dataList = new ArrayList();
        for(int p = 1; p <= totalRows; p++ ){
            XSSFRow rowData = sheet.getRow(p-1);
               //System.out.println(rowData.getCell(0));
               //System.out.println(rowData.getCell(1));
                rowKey = rowData.getCell(0).toString();
                rowValue = rowData.getCell(1).toString();

                ArrayList data = new ArrayList();
                data.add(rowKey);
                data.add(rowValue);
                dataList.add(data);
        }

        System.out.println("------------");
//        System.out.println(arr);
        int max = 0;
        for(int k =0;k <= arr.size()-1;k++) {
            if (k!=arr.size()-1) {
                String index = arr.get(k+1);
                double sumResult = 0;
                LinkedList<String> r1 = new LinkedList<String>();
                r1.add(dataList.get(max).get(0));
                r1.add(dataList.get(max).get(1));
                String result = dataList.get(max).get(0) + " " + dataList.get(max).get(1) + " ";
//            System.out.println(result);
                for (int p = max; !index.equals(dataList.get(p).get(0)); p++) {
//                   System.out.println(dataList.get(p).get(0));
//                   System.out.println(index);
                    dataList.get(p);
                    sumResult += Double.valueOf(dataList.get(p).get(1));
                    max = p + 1;
//                    System.out.println(sumResult);
                }
                result = result + sumResult;
//                System.out.println(max);
//                System.out.println(result);
                r1.add(Double.toString(sumResult));
                res.add(r1);
            }else{
                double sumResult = 0;
                String result = dataList.get(max).get(0) + " " + dataList.get(max).get(1) + " ";
//            System.out.println(result);
                for (int p = max; p <= dataList.size()-1; p++) {
//                   System.out.println(dataList.get(p).get(0));
//                   System.out.println(index);
                    dataList.get(p);
                    sumResult += Double.valueOf(dataList.get(p).get(1));
//                    max = p + 1;
//                    System.out.println(sumResult);
                }
                result = result + sumResult;
//                System.out.println(max);
//                System.out.println(result);
                LinkedList<String> r1 = new LinkedList<String>();
                r1.add(dataList.get(max).get(0));
                r1.add(dataList.get(max).get(1));
                r1.add(Double.toString(sumResult));
                res.add(r1);
            }
        }
//        for (LinkedList a: res
//             ) {
//            System.out.println(a.toString());
//        }
        writeExcel(res,fileName);
    }


    public static void writeExcel(List<LinkedList<String>> dataList, String finalXlsxPath){
        OutputStream out = null;
        try {

            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(1);

            //删除原有数据，除了属性列
            int rowNumber = sheet.getLastRowNum();
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }

            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);

            //写一行
            for (int j = 0; j < dataList.size(); j++) {
                Row row = sheet.createRow(j);

                //写一列
                List<String> datas = dataList.get(j);
                for (int k=0; k<datas.size(); k++) {
                    row.createCell(k).setCellValue(datas.get(k));
                }
            }

            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
            System.out.println("数据导出成功");
        } catch (Exception e) {
            System.out.println("请创建一个空文件");
            e.printStackTrace();
        } finally{
            try {
                if(out != null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    private static Workbook getWorkbok(File file) throws IOException{
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if(file.getName().endsWith(EXCEL_XLS)){     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }







}
