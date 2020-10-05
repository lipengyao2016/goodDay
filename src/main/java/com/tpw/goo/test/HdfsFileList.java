package com.tpw.goo.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.net.URI;
import java.util.Iterator;
import java.util.Map;

public class HdfsFileList {

    public static void main(String[] args) {
        FileSystem hdfs = null;
        try {
            Configuration config = new Configuration();
// 程序配置
            //config.set("fs.default.name", "hdfs://127.0.0.1:9000");

            //配置参数个数
            System.out.println(config.size());

            //查看配置参数信息
            Iterator<Map.Entry<String, String>> it = config.iterator();
            while(it.hasNext()) {
                Map.Entry<String, String> en = it.next();
                String key = en.getKey();
                String value = en.getValue();
                System.out.println("key=" + key + "  value=" + value);
            }



            //config.set("hadoop.job.ugi", "feng,111111");
            //config.set("hadoop.tmp.dir", "/tmp/hadoop-fengClient");
            //config.set("dfs.replication", "1");
            //config.set("mapred.job.tracker", "master:9001");
            hdfs = FileSystem.get(new URI("hdfs://localhost:9000"), config, "user");
            Path path = new Path("/");
            HdfsFileList.ShowPath(hdfs, path);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (hdfs != null) {
                try {
                    hdfs.closeAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void ShowPath(FileSystem hdfs, Path path) {
        try {

            if (hdfs == null || path == null) {
                return;
            }
            //获取文件列表
            FileStatus[] files = hdfs.listStatus(path);

//展示文件信息
            for (int i = 0; i < files.length; i++) {
                try {
                    if (files[i].isDirectory()) {
                        System.out.println(">>>" + files[i].getPath()
                                + ", dir owner:" + files[i].getOwner());
//递归调用
                        ShowPath(hdfs, files[i].getPath());
                    } else if (files[i].isFile()) {
                        System.out.println("   " + files[i].getPath()
                                + ", length:" + files[i].getLen()
                                + ", owner:" + files[i].getOwner());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
