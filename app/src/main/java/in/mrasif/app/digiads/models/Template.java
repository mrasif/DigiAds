package in.mrasif.app.digiads.models;

import java.util.ArrayList;
import java.util.List;

public class Template {
    private int id;
    private String name;
    private String path;
    private String css_path;
    private List<Position> positions;

    public Template() {
        this.positions=new ArrayList<>();
    }

    public Template(int id, String name, String path, String css_path, List<Position> positions) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.css_path = css_path;
        this.positions = positions;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCss_path() {
        return css_path;
    }

    public void setCss_path(String css_path) {
        this.css_path = css_path;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", css_path='" + css_path + '\'' +
                ", positions=" + positions +
                '}';
    }
}
