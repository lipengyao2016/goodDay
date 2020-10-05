package com.tpw.goo.test;

import com.alibaba.fastjson.JSONObject;
import com.tpw.goo.bean.UrlInfo;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Properties;

public class HdfsDemo {
    private Configuration conf;
    private FileSystem fs = null;
    private String hdfsurl = "hdfs://localhost:9000";
    String filePath = "/demo2/data/kk.txt";

    private HdfsDemo() {
        this.conf = new Configuration();
// 程序配置
        conf.set("fs.default.name", this.hdfsurl);
        conf.set("fs.default.name", this.hdfsurl);
        conf.setBoolean("dfs.support.append", true);
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");    //修改属性参数
        //config.set("hadoop.job.ugi", "feng,111111");
        //config.set("hadoop.tmp.dir", "/tmp/hadoop-fengClient");
        //config.set("dfs.replication", "1");
        //config.set("mapred.job.tracker", "master:9001");
        try {
            this.fs = FileSystem.get(new URI(this.hdfsurl), this.conf, "Administrator");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        String uri = filePath;
        FileSystem fs = null;
        InputStream in = null;
        try {
            fs = FileSystem.get(URI.create(uri), conf);
            //in = fs.open( new Path(uri));
            // IOUtils.copyBytes(in, System.out, 4096, false);
            FSDataInputStream hdfsInStream = fs.open(new Path(uri));
            byte[] bytes = new byte[1024];
            int len = -1;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            while ((len = hdfsInStream.read(bytes)) != -1) {
                stream.write(bytes, 0, len);
            }
            hdfsInStream.close();
            stream.close();
            System.out.println("read ok data:" +  new String(bytes,"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(in);
        }
    }

    public void write() {

        String inpath = "E:\\data\\db\\apolloportaldb.sql";
        try {
            //FileSystem fs = FileSystem.get(conf);
            //  Path f = new Path("hdfs://localhost/demo1/data/ttt.txt");
            Path f = new Path(filePath);
            ////  System.out.println(fs.isFile(f));
            // System.out.println(f.toString());
          //  FSDataOutputStream os = this.fs.create(f, true);
            FSDataOutputStream os = fs.append(f);
            String data = "\r\ntinghua\r\n";
            os.write(data.getBytes());
            os.flush();
            os.close();

            //要追加的文件流，inpath为文件
//            InputStream in = new
//                    BufferedInputStream(new FileInputStream(inpath));
//            OutputStream out = fs.append(f);
//            IOUtils.copyBytes(in, out, 4096, true);

            System.out.println("write ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile() {
        String localSrc = "E:\\data\\db\\sqkdj_com.sql";
        String dst = "/demo2/data/sqkdj_com.sql";
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(localSrc));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        long lCur = System.currentTimeMillis();
        OutputStream out = null;
        try {

            //  fs.delete(new Path(dst));


            out = fs.create(new Path(dst), new Progressable() {
                public void progress() {
                    System.out.print(".");
                }
            });

            IOUtils.copyBytes(in, out, 1024 * 1024 * 4, true);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("writeFile ok tm:" + (System.currentTimeMillis() - lCur));


    }

    public static void main(String[] args) {
        // System.setProperty("HADOOP_USER_NAME","root");
        new HdfsDemo().write();
        new HdfsDemo().read();
        //  new HdfsDemo().writeFile();
    }

}
