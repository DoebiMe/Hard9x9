package be.doubbel.hard9x9.backend;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileService {
    public  List<String> getResourceFolderFiles () throws IOException {
        List<String> resultList = new ArrayList<>();

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] resources = resolver.getResources("META-INF/resources/sudos/*.txt");

        for (Resource file : resources) {
            resultList.add(file.getFilename());
            System.out.println(file.getFilename());
        }
        return resultList;
    }


    public  Integer[][] readSudo(String sudoName) {
        Integer[][] result = new Integer[9][9];
        try {
            getResourceFolderFiles();
            System.out.println("Loading " + sudoName);
            InputStream inputStream =
                    this.getClass()
                            .getClassLoader()
                            .getResourceAsStream("META-INF/resources/sudos/" + sudoName);
            Scanner scanner = new Scanner(inputStream);
            String stringBuffer = "";
            Integer row = 0;
            while (scanner.hasNext() && row<9) {
                String stringFromFile = scanner.nextLine().replaceAll(" ","");
                stringBuffer.concat(stringFromFile);
                for (Integer col=0;col<9;col++) {
                    result[row][col] = Character.getNumericValue(stringFromFile.charAt(col));
                    System.out.print(result[row][col]);
                }
                System.out.println();
                row++;
            }
            stringBuffer.replaceAll(" ","");
            stringBuffer.replaceAll(",","");
            System.out.println(stringBuffer);
            return result;
        } catch (Exception ex) {
            System.out.println("Something went wrong while reading " + sudoName);
        }
        return null;
    }
}
