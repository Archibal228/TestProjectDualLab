package enums;

public enum ServiceName {
    POSH("Posh"),
    GROTTY("Grotty");

    private String name;

    ServiceName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
