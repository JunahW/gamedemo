package com.example.gamedemo.common.utils;

import com.example.gamedemo.common.anno.ExcelColumn;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: wengj
 * @date: 2019/5/7
 * @description: 读取Excel数据工具类
 */
public class ExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 读取Excel数据
     *
     * @param clazz
     * @return
     */
    public static <T> List<T> importExcel(Class<T> clazz) {
        InputStream input = getInputStreamByClass(clazz);

        List<T> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(input);
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            // 没有数据，直接返回
            if (rows < 0) {
                return list;
            }
            //获取加ExcelColumn注解的字段
            List<Field> allFields = getMappedFiled(clazz, null);

            // 第一行为表头
            Row rowHead = sheet.getRow(0);
            //获取row下标和Field的映射表
            Map<Integer, Field> rowIndex2FiledMap = getRowIndex2FiledMap(rowHead, allFields);

            // 从第2行开始取数据
            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);
                T entity = null;
                for (int j = 0; j < rowHead.getPhysicalNumberOfCells(); j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    //获取单元格数据
                    String value = getCellValue(cell);

                    if (value == null || value.equals("")) {
                        continue;
                    }
                    entity = (entity == null ? clazz.newInstance() : entity);
                    // 从map中得到对应列的field.
                    Field field = rowIndex2FiledMap.get(j);
                    if (field == null) {
                        continue;
                    }
                    // 取得类型,并根据对象类型设置值.
                    setEntityFieldValue(entity, field, value);
                }
                if (entity != null) {
                    list.add(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 通过class获取输入流，注意Excel名称与类名称一致，并放在resources/resource目录下
     *
     * @param clazz
     * @return
     */
    private static <T> InputStream getInputStreamByClass(Class<T> clazz) {
        String simpleName = clazz.getSimpleName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("classpath:resource/")
                .append(simpleName).append(".xlsx");
        File file = null;
        FileInputStream fileInputStream = null;
        try {
            file = ResourceUtils.getFile(stringBuilder.toString());
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error("{}该文件不存在", stringBuilder.toString());
            e.printStackTrace();
        }
        return fileInputStream;
    }

    /**
     * 获取Excel单元格的数据
     *
     * @param cell
     * @return
     */
    private static String getCellValue(Cell cell) {
        String value = null;
        int cellType = cell.getCellType();

        if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
            DecimalFormat df = new DecimalFormat("0");
            value = df.format(cell.getNumericCellValue());
        } else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
            value = String.valueOf(cell.getBooleanCellValue());
        } else {
            value = cell.getStringCellValue();
        }
        return value;
    }

    /**
     * 获取表头字段和对象属性的映射map
     *
     * @param rowHead
     * @param allFields
     * @return
     */
    private static Map<Integer, Field> getRowIndex2FiledMap(Row rowHead, List<Field> allFields) {
        // 定义一个map用于存放列的序号和field.
        Map<Integer, Field> rowIndex2FiledMap = new HashMap<Integer, Field>(16);
        Map<String, Integer> cellMap = new HashMap<>(16);
        int cellNum = rowHead.getPhysicalNumberOfCells();
        for (int i = 0; i < cellNum; i++) {
            cellMap.put(rowHead.getCell(i).getStringCellValue().toLowerCase(), i);
        }
        for (Field field : allFields) {
            // 将有注解的field存放到map中.
            ExcelColumn attr = field.getAnnotation(ExcelColumn.class);
            // 根据Name来获取相应的failed
            int col = cellMap.get(attr.columnName().toLowerCase());
            field.setAccessible(true);
            rowIndex2FiledMap.put(col, field);
        }
        return rowIndex2FiledMap;
    }

    /**
     * 设置entity的值
     *
     * @param entity
     * @param field
     * @param value
     */
    private static <T> void setEntityFieldValue(T entity, Field field, String value) {
        Class<?> fieldType = field.getType();
        try {
            //判断是否为数组
            if (fieldType.isArray()) {
                String[] rows = value.split("\n");
                int x = 0, y = 0;
                if (rows != null && rows.length > 0) {
                    x = rows.length;
                    String[] columns = rows[0].split(",");
                    if (columns != null && columns.length > 0) {
                        y = columns.length;
                    }
                }
                int[][] array = new int[x][y];
                for (int i = 0; i < rows.length; i++) {
                    String[] columns = rows[i].split(",");
                    for (int j = 0; j < columns.length; j++) {
                        array[i][j] = Integer.parseInt(columns[j]);
                    }
                }
                field.set(entity, array);
            } else if (String.class == fieldType) {
                field.set(entity, String.valueOf(value));
            } else if ((Integer.TYPE == fieldType)
                    || (Integer.class == fieldType)) {
                field.set(entity, Integer.valueOf(value));
            } else if ((Long.TYPE == fieldType)
                    || (Long.class == fieldType)) {
                field.set(entity, Long.valueOf(value));
            } else if ((Float.TYPE == fieldType)
                    || (Float.class == fieldType)) {
                field.set(entity, Float.valueOf(value));
            } else if ((Short.TYPE == fieldType)
                    || (Short.class == fieldType)) {
                field.set(entity, Short.valueOf(value));
            } else if ((Double.TYPE == fieldType)
                    || (Double.class == fieldType)) {
                field.set(entity, Double.valueOf(value));
            } else if (Character.TYPE == fieldType) {
                if (value.length() > 0) {
                    field.set(entity, value.charAt(0));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
        }
    }


    /**
     * 获取实体类（包括父类）通过注解映射了数据表的字段
     *
     * @param clazz
     * @param fields
     * @return
     */
    private static List<Field> getMappedFiled(Class clazz, List<Field> fields) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        // 得到所有定义字段
        Field[] allFields = clazz.getDeclaredFields();
        // 得到所有field并存放到一个list中.
        for (Field field : allFields) {
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                fields.add(field);
            }
        }
        if (clazz.getSuperclass() != null
                && !clazz.getSuperclass().equals(Object.class)) {
            getMappedFiled(clazz.getSuperclass(), fields);
        }
        return fields;
    }

}