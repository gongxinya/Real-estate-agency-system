package stacs.estate.cs5031p3code;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The SpringBoot starter.
 *
 * @author 220032952
 * @version 0.0.1
 */
@SpringBootApplication
@MapperScan("stacs.estate.cs5031p3code.mapper")
public class Cs5031p3codeApplication {
    public static void main(String[] args) {
        SpringApplication.run(Cs5031p3codeApplication.class, args);
    }
}
