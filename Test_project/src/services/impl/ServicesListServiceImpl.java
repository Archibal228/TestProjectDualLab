package services.impl;

import enums.ServiceName;
import models.Service;
import services.ServicesListService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServicesListServiceImpl implements ServicesListService {

    public List<Service> getAllServices(String path) {
        Pattern p = Pattern.compile("(Posh|Grotty)\\s(\\d{2}:\\d{2})\\s(\\d{2}:\\d{2})");

        try (Stream<String> stream = Files.lines(Paths.get(path))) {

            return stream.map(line -> {
                Matcher m = p.matcher(line);

                if (m.matches()) {
                    ServiceName serviceName;

                    if (m.group(1).equals(ServiceName.POSH.getName()))
                        serviceName = ServiceName.POSH;
                    else
                        serviceName = ServiceName.GROTTY;

                    LocalTime arrivalTime = LocalTime.parse(m.group(2));
                    LocalTime departureTime = LocalTime.parse(m.group(3));

                    return new Service(serviceName, arrivalTime, departureTime);
                } else {
                    throw new Error("File not correct");
                }
            }).limit(50).collect(Collectors.toList());
        } catch (IOException e) {
            throw new Error("Error during file parsing: " + e.getMessage());

        }
    }

    public List<Service> sort(List<Service> services) {
        return services.stream().sorted((Comparator.comparing(Service::getFinishTime))).collect(Collectors.toList());
    }

    public void createOutputFile(List<Service> posh, List<Service> grotty) throws IOException {
        File file = new File("output.txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        writeInFile(bufferedWriter, posh);
        bufferedWriter.write("\n");
        writeInFile(bufferedWriter, grotty);

        bufferedWriter.flush();
        bufferedWriter.close();

    }

    private void writeInFile(BufferedWriter bufferedWriter, List<Service> services) throws IOException {
        String[] serviceSTR = services.stream().map(Service::toString).toArray(String[]::new);
        String finalString = String.join("\n", serviceSTR);
        bufferedWriter.write(finalString);


    }


    public List<Service> getServicesByName(List<Service> services, ServiceName company) {

        return services.stream().filter(service -> service.getCompany() == company).collect(Collectors.toList());
    }


    public List<Service> isEffectiveService(List<Service> services) {
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).getFinishTime().minusHours(1).isAfter(services.get(i).getStartTime())) {
                services.remove(i);
                i--;
                continue;
            }
            for (int j = 0; j < services.size(); j++) {
                if (i == j)
                    continue;
                if (services.get(i).getStartTime().equals(services.get(j).getStartTime()) && services.get(i).getFinishTime().equals(services.get(j).getFinishTime()) && services.get(i).getCompany() == ServiceName.GROTTY) {
                    services.remove(i);
                    i--;
                    break;
                }
                if ((services.get(i).getStartTime().equals(services.get(j).getStartTime()) && services.get(i).getFinishTime().isAfter(services.get(j).getFinishTime()))
                        || services.get(i).getStartTime().isBefore(services.get(j).getStartTime()) && services.get(i).getFinishTime().equals(services.get(j).getFinishTime()) ||
                        services.get(i).getStartTime().isBefore(services.get(j).getStartTime()) && services.get(i).getFinishTime().isAfter(services.get(j).getFinishTime())) {
                    services.remove(i);
                    i--;
                    break;
                }
            }
        }
        return services;
    }
}
