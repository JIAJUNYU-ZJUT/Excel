with customer_tag_table as (
    SELECT
        customer_id,
        sum_paid,
        sum_reparation,
        payout_ration,
        renewwal_time,
        risk_time,
        if(datediff(CURRENT_DATE,CONCAT(substr(CURRENT_DATE,0,4),substr(birthday,5,7)))>=0, (substr(CURRENT_DATE,0,4) - substr(birthday,0,4)), (substr(CURRENT_DATE,0,4) - substr(birthday,0,4)-1)) as age,
        gender,
        city as customer_address,
        industry
        FROM dm_db.dm_customer_individual_info_merge_dd
        )
        SELECT
        customer_id,
        level1_item1 as label1,
        CONCAT_WS(',',level2_item1,level2_item2,level2_item3,level2_item4) as label2,
        CONCAT_WS(',',level3_item1,level3_item2) as label3,
        CONCAT_WS(',',level4_item1,level4_item2,level4_item3) as label4,
        CONCAT_WS(',',level5_item1,level5_item2,level5_item3,level5_item4) as label5,
        CONCAT_WS(',',level6_item1,level6_item2,level6_item3) as label6,
        null as label7,
        null as label8,
        null as label9,
        null as label10,
        null as label11,
        null as label12,
        null as label13,
        null as label14,
        null as label15 ,
        CURRENT_DATE as update_date
        FROM (
            SELECT
                CASE WHEN (age>=20 and age<40) and (gender in ('1' )) and (industry in ('15' )) THEN '车险测试:车险A' ELSE NULL END as level1_item1 ,
                CASE WHEN (if (payoutRation.payout_ration IS NULL, 0, cast (payoutRation.payout_ration as double))<50.0000) and (if (riskTime.risk_time IS NULL, 0, cast (riskTime.risk_time as bigint))<=2) THEN '意健险:A级' ELSE NULL END as level2_item1 ,
                CASE WHEN (age>=40 and age<45) and (if (payoutRation.payout_ration IS NULL, 0, cast (payoutRation.payout_ration as double))>=50.0000 and if (payoutRation.payout_ration IS NULL, 0, cast (payoutRation.payout_ration as double))<70.0000) and (if (riskTime.risk_time IS NULL, 0, cast (riskTime.risk_time as bigint))>=1 and if (riskTime.risk_time IS NULL, 0, cast (riskTime.risk_time as bigint))<2) and (if (renewwalTime.renewwal_time IS NULL, 0, cast (renewwalTime.renewwal_time as bigint))>=1 and if (renewwalTime.renewwal_time IS NULL, 0, cast (renewwalTime.renewwal_time as bigint))<20) THEN '意健险:B级' ELSE NULL END as level2_item2 ,
                CASE WHEN (age>=70) or (if (payoutRation.payout_ration IS NULL, 0, cast (payoutRation.payout_ration as double))>=65.5000) or (if (riskTime.risk_time IS NULL, 0, cast (riskTime.risk_time as bigint))>=2) THEN '意健险:C级' ELSE NULL END as level2_item3 ,
                CASE WHEN (if (renewwalTime.renewwal_time IS NULL, 0, cast (renewwalTime.renewwal_time as bigint))>1) THEN '意健险:综合分析' ELSE NULL END as level2_item4 ,
                CASE WHEN (if (sumPaid.sum_paid IS NULL, 0, cast (sumPaid.sum_paid as double))>=10000) and (if (riskTime.risk_time IS NULL, 0, cast (riskTime.risk_time as bigint))<=1) and (if (renewwalTime.renewwal_time IS NULL, 0, cast (renewwalTime.renewwal_time as bigint))>=3) and (if (payoutRation.payout_ration IS NULL, 0, cast (payoutRation.payout_ration as double))<=30.0000) THEN '财产险测试:一级' ELSE NULL END as level3_item1 ,
                CASE WHEN (if (sumPaid.sum_paid IS NULL, 0, cast (sumPaid.sum_paid as double))>=5000 and if (sumPaid.sum_paid IS NULL, 0, cast (sumPaid.sum_paid as double))<10000) and (if (riskTime.risk_time IS NULL, 0, cast (riskTime.risk_time as bigint))>=2) and (if (payoutRation.payout_ration IS NULL, 0, cast (payoutRation.payout_ration as double))<=50.0000) THEN '财产险测试:二级' ELSE NULL END as level3_item2 ,
                CASE WHEN (age>=20 and age<40) and (if (payoutRation.payout_ration IS NULL, 0, cast (payoutRation.payout_ration as double))<60.0000) and (if (renewwalTime.renewwal_time IS NULL, 0, cast (renewwalTime.renewwal_time as bigint))>2) THEN '综合:A' ELSE NULL END as level4_item1 ,
                CASE WHEN (age>=20 and age<60) and (if (payoutRation.payout_ration IS NULL, 0, cast (payoutRation.payout_ration as double))>=60.0000 and if (payoutRation.payout_ration IS NULL, 0, cast (payoutRation.payout_ration as double))<80.0000) THEN '综合:B' ELSE NULL END as level4_item2 ,
                CASE WHEN (age>=20 and age<100) and (if (payoutRation.payout_ration IS NULL, 0, cast (payoutRation.payout_ration as double))>=80.0000 and if (payoutRation.payout_ration IS NULL, 0, cast (payoutRation.payout_ration as double))<99.9000) THEN '综合:C' ELSE NULL END as level4_item3 ,
                CASE WHEN (if (renewwalTime.renewwal_time IS NULL, 0, cast (renewwalTime.renewwal_time as bigint))>=3) THEN '综合分析:A' ELSE NULL END as level5_item1 ,
                CASE WHEN (if (renewwalTime.renewwal_time IS NULL, 0, cast (renewwalTime.renewwal_time as bigint))>=2 and if (renewwalTime.renewwal_time IS NULL, 0, cast (renewwalTime.renewwal_time as bigint))<3) THEN '综合分析:B' ELSE NULL END as level5_item2 ,
                CASE WHEN (if (renewwalTime.renewwal_time IS NULL, 0, cast (renewwalTime.renewwal_time as bigint))>=1 and if (renewwalTime.renewwal_time IS NULL, 0, cast (renewwalTime.renewwal_time as bigint))<2) THEN '综合分析:C' ELSE NULL END as level5_item3 ,
                CASE WHEN (if (renewwalTime.renewwal_time IS NULL, 0, cast (renewwalTime.renewwal_time as bigint))<1) THEN '综合分析:D' ELSE NULL END as level5_item4 ,
                CASE WHEN (if (renewwalTime.renewwal_time IS NULL, 0, cast (renewwalTime.renewwal_time as bigint))>=2) THEN '意健险续保分析:续保大于1次' ELSE NULL END as level6_item1 ,
                CASE WHEN (if (renewwalTime.renewwal_time IS NULL, 0, cast (renewwalTime.renewwal_time as bigint))>=1 and if (renewwalTime.renewwal_time IS NULL, 0, cast (renewwalTime.renewwal_time as bigint))<2) THEN '意健险续保分析:续保1次' ELSE NULL END as level6_item2 ,
                CASE WHEN (if (renewwalTime.renewwal_time IS NULL, 0, cast (renewwalTime.renewwal_time as bigint))<1) THEN '意健险续保分析:脱保' ELSE NULL END as level6_item3 ,customer_id
            FROM customer_tag_table
            lateral view json_tuple(risk_time,'财产险') riskTime as risk_time
            lateral view json_tuple(renewwal_time,'意健险') renewwalTime as renewwal_time
            lateral view json_tuple(payout_ration,'全部') payoutRation as payout_ration
            lateral view json_tuple(sum_paid,'财产险') sumPaid as sum_paid
             ) as aa