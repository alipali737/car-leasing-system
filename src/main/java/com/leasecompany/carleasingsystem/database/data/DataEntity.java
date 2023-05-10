package com.leasecompany.carleasingsystem.database.data;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.converter.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface DataEntity<T extends Serializable> {
    /**
     * Generate table columns from a list of fieldNames for the class
     *
     * @param fieldNames fields within the class that should be created as columns
     * @return Array of table columns
     */
    default TableColumn[] getTableColumns(List<String> fieldNames) {
        List<TableColumn<Object, ?>> columns = new ArrayList<>();
        for (String fieldName : fieldNames) {
            try {
                Field field = this.getClass().getDeclaredField(fieldName);

                TableColumn<Object, ?> column = null;

                switch (field.getType().getTypeName()) {
                    case "java.lang.String":
                        TableColumn<Object, String> columnStr = new TableColumn<>(field.getName());
                        columnStr.setCellFactory(TextFieldTableCell.forTableColumn());
                        column = columnStr;
                        break;
                    case "java.lang.Boolean":
                        TableColumn<Object, Boolean> columnBool = new TableColumn<>(field.getName());
                        columnBool.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
                        column = columnBool;
                        break;
                    case "double":
                        TableColumn<Object, Double> columnDouble = new TableColumn<>(field.getName());
                        columnDouble.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
                        column = columnDouble;
                        break;
                    case "int":
                        TableColumn<Object, Integer> columnInt = new TableColumn<>(field.getName());
                        columnInt.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
                        column = columnInt;
                        break;
                    case "Long":
                        TableColumn<Object, Long> columnLong = new TableColumn<>(field.getName());
                        columnLong.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
                        column = columnLong;
                    case "java.util.Date":
                        TableColumn<Object, Date> columnDate = new TableColumn<>(field.getName());
                        columnDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
                        column = columnDate;
                    default:
                        break;
                }

                if (column == null) {
                    System.err.println("Column with fieldName: " + fieldName + ", and field type: " + field.getType().getTypeName() + ", was found as null");
                    continue;
                }

                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));

                Text headerText = new Text(fieldName);
                double textWidth = headerText.getBoundsInLocal().getWidth();
                double padding = 50.0;
                column.setPrefWidth(textWidth + padding);

                columns.add(column);
            } catch (NoSuchFieldException e) {
                System.err.println("Field "+fieldName+" not found in class " + this.getClass());
            }

        }
        return columns.toArray(new TableColumn[0]);
    }

    /**
     * Generate a report file for the object
     * @param path path to write file to
     */
    default void generateReportFile(String path, Label statusLabel) {}

}
