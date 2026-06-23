package com.mdm.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class ExcelUtil {

    private ExcelUtil() {}

    public static <T> void export(HttpServletResponse response, String fileName,
                                   Class<T> clazz, List<T> data) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String encoded = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encoded + ".xlsx");
        EasyExcel.write(response.getOutputStream(), clazz).sheet("数据").doWrite(data);
    }

    public static <T> List<T> importExcel(MultipartFile file, Class<T> clazz) throws IOException {
        List<T> result = new ArrayList<>();
        EasyExcel.read(file.getInputStream(), clazz, new PageReadListener<T>(result::addAll))
                .sheet().doRead();
        return result;
    }

    public static <T> void importExcel(MultipartFile file, Class<T> clazz,
                                        Consumer<List<T>> batchConsumer) throws IOException {
        EasyExcel.read(file.getInputStream(), clazz, new PageReadListener<>(batchConsumer))
                .sheet().doRead();
    }
}
