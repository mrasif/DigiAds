package in.mrasif.app.digiads.models;

public class SetupData {
    private int id;
    private String name;
    private String time_zone;
    private Template template;

    public SetupData() {
        this.template=new Template();
    }

    public SetupData(int id, String name, String time_zone, Template template) {
        this.id = id;
        this.name = name;
        this.time_zone = time_zone;
        this.template = template;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return "SetupData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time_zone='" + time_zone + '\'' +
                ", template=" + template +
                '}';
    }
}
