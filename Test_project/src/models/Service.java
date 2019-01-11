package models;

import enums.ServiceName;

import java.io.*;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {

    private ServiceName company;
    private LocalTime startTime;
    private LocalTime finishTime;

    public ServiceName getCompany() {
        return company;
    }

    public void setCompany(ServiceName company) {
        this.company = company;
    }

    public Service(ServiceName company, LocalTime starttime, LocalTime finishtime) {
        this.company = company;
        this.startTime = starttime;
        this.finishTime = finishtime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(LocalTime finishTime) {
        this.finishTime = finishTime;
    }

    public LocalTime getStartTime() {

        return startTime;
    }

    public LocalTime getFinishTime() {
        return finishTime;
    }

    @Override
    public String toString() {
        return company + " " + startTime + " " + finishTime;
    }


}
