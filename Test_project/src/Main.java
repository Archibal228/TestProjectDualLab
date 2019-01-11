import enums.ServiceName;
import models.Service;
import services.ServicesListService;
import services.impl.ServicesListServiceImpl;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ServicesListService servicesListService = new ServicesListServiceImpl();


//        Scanner in = new Scanner(System.in);
//        System.out.println("Enter the file path" );
//        String path = in.nextLine();
        List<Service> services = servicesListService.getAllServices("input.txt");

        services = servicesListService.isEffectiveService(services);

        services = servicesListService.sort(services);
        List<Service> posh = servicesListService.getServicesByName(services, ServiceName.POSH);
        List<Service> grotty = servicesListService.getServicesByName(services, ServiceName.GROTTY);

        try {
            servicesListService.createOutputFile(posh, grotty);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }


    }
}
