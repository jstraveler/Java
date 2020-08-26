package age;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class Exec {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String input_string = "";
        try {
            input_string = reader.readLine();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        Age age = new Age(input_string);
        Period result = age.getAge();

        if(result != null) {
            if(result.getYears()==11 || result.getYears()==12 || result.getYears()==13 || result.getYears()==14) {
                System.out.println(Format.yearsMonthsDays2.print(result));
            }
            else if(result.getYears()%10==2 || result.getYears()%10==3 || result.getYears()%10==4) {
                System.out.println(Format.yearsMonthsDays.print(result));
            }
            else if(result.getYears()%10==1) {
                System.out.println(Format.yearsMonthsDays3.print(result));
            }
            else {
                System.out.println(Format.yearsMonthsDays2.print(result));
            }
        }
        else {
            System.out.println("Enter the correct date");
        }
    }
}