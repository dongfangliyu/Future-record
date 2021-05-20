package top.goodz.future.application.process.csv;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  @Description TODO
 *  @Author Yajun.Zhang
 *  @Date 2021/5/19 21:54
 */

@Service
public class CSVImportProcessService {

    public void CSVImportData(InputStream inputStream){
        List importData = CSVImportDataUtils.CSVImportData(inputStream);

        System.out.printf("" + importData);


    }

}
