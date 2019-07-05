package com.hanxuanliang.logprocessing.util;

import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * @author 22545
 */
public class HBaseUtils {

    private HBaseAdmin admin = null;
    private Configuration configuration = null;

    private HBaseUtils() throws IOException {
        configuration = new Configuration();
        configuration.set("hbase.zookeeper.quorum", "hadoop000:2181");
        configuration.set("hbase.rootdir", "hdfs://hadoop000:8020/hbase");

        admin = new HBaseAdmin(configuration);
    }

    private static HBaseUtils instance = null;

    public static synchronized HBaseUtils getInstance() throws IOException {
        if(null == instance) { instance = new HBaseUtils(); }
        return instance;
    }

    public HTable getTable(String tableName) throws IOException {
        HTable table = null;
        table = new HTable(configuration, tableName);
        return table;
    }

    public void put(String tableName, String rowkey, String cf, String column, String value) throws IOException {
        HTable hTable = getTable(tableName);
        Put put = new Put(Bytes.toBytes(rowkey));
        put.add(Bytes.toBytes(cf), Bytes.toBytes(column), Bytes.toBytes(value));
        hTable.put(put);
    }

    public static void main(String[] args) throws Exception {
        /**
         * 检测是否能连接到HBase
         * HTable table = HBaseUtils.getInstance().getTable("logcourse_clickcount");
         * System.out.println(table.getName().getNameAsString());
         */

        String tableName = "logcourse_clickcount";
        String rowkey = "20190705_368";
        String cf = "info";
        String column = "click_count";
        String value = "2";

        HBaseUtils.getInstance().put(tableName, rowkey, cf, column, value);

    }
}
