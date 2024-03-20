package dbttools;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws Exception{
        String columns = "num\n" +
                "oid\n" +
                "tid\n" +
                "remark\n" +
                "item_id\n" +
                "payment\n" +
                "post_fee\n" +
                "is_refund\n" +
                "shop_name\n" +
                "order_time\n" +
                "order_type\n" +
                "share_qita\n" +
                "item_amount\n" +
                "settle_time\n" +
                "settle_type\n" +
                "share_qudao\n" +
                "shop_coupon\n" +
                "income_total\n" +
                "order_amount\n" +
                "payout_total\n" +
                "platform_fee\n" +
                "share_amount\n" +
                "business_type\n" +
                "refund_amount\n" +
                "settle_acount\n" +
                "settle_amount\n" +
                "subsidy_daren\n" +
                "share_tuiguang\n" +
                "subsidy_douyin\n" +
                "share_zhaoshang\n" +
                "subsidy_pingtai\n" +
                "share_qudao_remark\n" +
                "subsidy_douyinyingxiao\n" +
                "_yyzn_emitted_at";

        String[] split = columns.split("\n");
        String res = "";

        String res2 = "";
        for (String s : split) {
            if ( s.equals("_yyzn_emitted_at") ){
                res = res + "        {\"o_name\":\"" +  s + "\",\"type\":\"timestamp\",\"n_name\":\"" + s + "\"},\n";
            }else {
                res = res + "        {\"o_name\":\"" +  s + "\",\"type\":\"text\",\"n_name\":\"" + s + "\"},\n";
                res2 = res2 + "\t\"" + s + "\",\n";
            }
        }

        res2 += "    CURRENT_TIMESTAMP as \"_yyzn_emitted_at\"";

        System.out.println(res);
        System.out.println("\n");
        System.out.println(res2);
    }
}
