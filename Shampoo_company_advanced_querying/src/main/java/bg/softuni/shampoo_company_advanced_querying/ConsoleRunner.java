package bg.softuni.shampoo_company_advanced_querying;


import bg.softuni.shampoo_company_advanced_querying.entities.Shampoo;
import bg.softuni.shampoo_company_advanced_querying.entities.Size;
import bg.softuni.shampoo_company_advanced_querying.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    public final ShampooService shampooService;

    @Autowired
    public ConsoleRunner(ShampooService shampooService) {
        this.shampooService = shampooService;
    }


    @Override
    public void run(String... args) throws Exception {
      //  for (Shampoo shampoo :this.shampooService.findByBrand("Cotton Fresh")) {
     //      System.out.println(shampoo.getId());
     //   }
    //    for (Shampoo shampoo :this.shampooService.findByBrandAndSize("Cotton Fresh", parsed)) {
    //        System.out.println(shampoo.getId());
   //     }
        Scanner scanner = new Scanner(System.in);
        String size = scanner.nextLine();
        long labelId =Long.parseLong(scanner.nextLine());


        for (Shampoo shampoo :this.shampooService.findBySizeOrLabelIdOrderByIdAsc( size)) {
                   System.out.println(shampoo.toString());
                }
    }
}
