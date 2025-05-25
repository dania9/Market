/*package utils;

import java.io.IOException;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;

public class TableSaw {
    public static void main(String[] args) throws IOException {
        String csvFile = "data/lidl_2025-05-01.csv";
        Table table = Table.read().csv(
                CsvReadOptions.builder(csvFile)
                        .separator(';')
                        .build()
        );
        System.out.println(table.print());
    }
}*/