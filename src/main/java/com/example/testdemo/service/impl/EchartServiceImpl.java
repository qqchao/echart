package com.example.testdemo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.example.testdemo.bean.EChartConfigurations;
import com.example.testdemo.service.IEchartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EchartServiceImpl implements IEchartService {

    @Value("${file.path}")
    private String filePath;

    private SimpleDateFormat dateFormatFile = new SimpleDateFormat("yyyy-MM-dd-HH");

    @Autowired
    private EChartConfigurations eChartConfigurations;

    @Override
    public List<Map<String, Object>> getCoordinate(){
        List<Map<String, Object>> results = new ArrayList<>();

        String fileName = filePath + dateFormatFile.format(new Date()) + ".txt";

        List<String> result = readLastNLine(new File(fileName), 100);

        for(int i = 0; i < result.size(); i++){
            Map<String, Object> coordinates = new HashMap();
            String[] message = result.get(i).split(",");
            JSONArray coordinate = new JSONArray();
            coordinate.add(message[0]);
            try {
                coordinate.add(Double.parseDouble(message[8]));
            }catch (Exception e){
                coordinate.add(0.1);
            }

//            coordinates.put("time", message[0]);
//            coordinates.put("value", message[8]);
            coordinates.put("name", "");
            coordinates.put("value", coordinate);

            String[] value = new String[2];
            value[0] = message[0];
            value[1] = message[8];

            results.add(coordinates);
        }


        return  results;
    }

    private static List<String> readLastNLine(File file, long numRead)
    {
        // 定义结果集
        List<String> result = new ArrayList<String>();
        //行数统计
        long count = 0;

        // 排除不可读状态
        if (!file.exists() || file.isDirectory() || !file.canRead())
        {
            return null;
        }

        // 使用随机读取
        RandomAccessFile fileRead = null;
        try
        {
            //使用读模式
            fileRead = new RandomAccessFile(file, "r");
            //读取文件长度
            long length = fileRead.length();
            //如果是0，代表是空文件，直接返回空结果
            if (length == 0L)
            {
                return result;
            }
            else
            {
                //初始化游标
                long pos = length - 1;
                while (pos > 0)
                {
                    pos--;
                    //开始读取
                    fileRead.seek(pos);
                    //如果读取到\n代表是读取到一行
                    if (fileRead.readByte() == '\n')
                    {
                        //使用readLine获取当前行
                        String line = fileRead.readLine();
                        //保存结果
                        result.add(line);

                        //打印当前行
//                        System.out.println(line);

                        //行数统计，如果到达了numRead指定的行数，就跳出循环
                        count++;
                        if (count == numRead)
                        {
                            break;
                        }
                    }
                }
                if (pos == 0)
                {
                    fileRead.seek(0);
                    result.add(fileRead.readLine());
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (fileRead != null)
            {
                try
                {
                    //关闭资源
                    fileRead.close();
                }
                catch (Exception e)
                {
                }
            }
        }

        return result;
    }
}
