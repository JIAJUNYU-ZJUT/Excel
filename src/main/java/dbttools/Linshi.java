package dbttools;

public class Linshi {
    public static void main(String[] args) {
        String columns = "user_id\n" +
                "one_id\n" +
                "union_id\n" +
                "weixin_open_id\n" +
                "nick\n" +
                "sex\n" +
                "name\n" +
                "is_menber\n" +
                "status\n" +
                "relation_source\n" +
                "grade\n" +
                "grade_name\n" +
                "wechat_type\n" +
                "is_fans\n" +
                "gender\n" +
                "province\n" +
                "city\n" +
                "country\n" +
                "platform_type\n" +
                "mobile\n" +
                "address\n" +
                "im_name\n" +
                "user_tag\n" +
                "receiver_name\n" +
                "receiver_state\n" +
                "receiver_city\n" +
                "receiver_district\n" +
                "receiver_mobile\n" +
                "last_trade_time\n" +
                "first_trade_time\n" +
                "follow_time\n" +
                "last_talk_time\n" +
                "unfollow_time\n" +
                "created_time\n" +
                "updated_time\n" +
                "trade_duration\n" +
                "item_num\n" +
                "close_trade_amount\n" +
                "close_trade_count\n" +
                "trade_amount\n" +
                "trade_count\n" +
                "num";

        String[] split = columns.split("\n");
        String res = "";

        String res2 = "";
        for (String s : split) {
            String ss ="	count(" +s + ")/count(1)::numeric as "+s+"_ratio,\n";
            res += ss;
        }

        System.out.println(res);
    }
}
