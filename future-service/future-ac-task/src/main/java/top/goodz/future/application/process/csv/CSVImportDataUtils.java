package top.goodz.future.application.process.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  @Description TODO
 *  @Author Yajun.Zhang
 *  @Date 2021/5/19 22:37
 */
public class CSVImportDataUtils {


    //最终单元格中的内容正则表达式为： ([^"]*"{2})*[^"]*
    private static final Pattern p = Pattern.compile("^\"([^\"]*\"{2})*[^\"]*(\",|\"$)");//双引号包围的单元格结束符号一定是",和"$


    public static List CSVImportData(InputStream inputStream){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
            List<List<String>> list = new ArrayList<>();
            List<String> row = new ArrayList<>();
            String line;
            String str = "";
            while ((line = br.readLine()) != null) {
                line = str + line;
                if (!"".equals(line.trim())) {
                    str = getCells(line, row);
                }
                if ("".equals(str)) {//一条完整的行结束
                    list.add(row);
                    row = new ArrayList<>();
                } else {
                    str += "\r\n";
                }
            }

            System.out.printf("data" + list);

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String getCells(String line, List<String> row) {
        String cell;
        if (line.startsWith("\"")) {//有双引号包围的单元格
            Matcher m = p.matcher(line);
            if (m.find()) {
                cell = m.group();
                if (cell.endsWith(",")) {
                    cell = cell.substring(0, cell.length() - 1);
                }
                if (cell.startsWith("\"") && cell.endsWith("\"")) {
                    cell = cell.substring(1, cell.length() - 1);
                    cell = cell.replaceAll("\"\"", "\"");
                }
                row.add(cell);
                line = line.substring(m.end());
                if ("".equals(line)) {//一条完整的行结束
                    return "";
                }
                return getCells(line, row);
            } else {
                return line;
            }
        } else {//无双引号包围的单元格
            if (line.contains(",")) {
                cell = line.substring(0, line.indexOf(","));
                row.add(cell.trim());
                line = line.substring(line.indexOf(",") + 1);
                return getCells(line, row);
            } else {//一条完整的行结束
                row.add(line.trim());
                return "";
            }
        }
    }

}
