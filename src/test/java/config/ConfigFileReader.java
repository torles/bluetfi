package config;

import com.bluetfi.model.Fund;
import com.bluetfi.model.FundType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigFileReader {

    public static LinkedHashMap<String, Fund> readFundsFromFile(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            Stream<String> stream = reader.lines().skip(1);
            return stream
                    .map(mapToFund)
                    .collect(Collectors.toMap(Fund::getName,f->f,
                            (v1, v2) -> v1,
                            LinkedHashMap::new));
        } catch (FileNotFoundException e) {
            System.out.println(String.format("File %s not found",
                    path.getFileName().toString()));
            throw e;
        } catch (IOException e) {
            System.out.println(String.format("Couldn't read from %s",
                    path.getFileName().toString()));
            throw e;
        }
    }

    private static Function<String, Fund> mapToFund = (line) -> {
        String[] columns = line.split(",");
        Fund fund = new Fund(
                Long.parseLong(columns[0]),
                FundType.valueOf(columns[1]),
                columns[2]);
        return fund;
    };

}
