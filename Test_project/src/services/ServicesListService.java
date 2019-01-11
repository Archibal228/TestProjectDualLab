package services;

import enums.ServiceName;
import models.Service;

import java.io.IOException;
import java.util.List;

public interface ServicesListService {
    List getAllServices(String path);

    List<Service> isEffectiveService(List<Service> services);

    List<Service> sort(List<Service> services);

    List<Service> getServicesByName(List<Service> services, ServiceName company);

    void createOutputFile(List<Service> posh, List<Service> grotty) throws IOException;
}
