package in.mrasif.app.digiads.models;

public class VideoModel {
    private int id;
    private String name;
    private long duration;
    private String type;
    private RenderDetails render_details;
    private String file;

    public VideoModel() {
        this.render_details=new RenderDetails();
    }

    public VideoModel(int id, String name, long duration, String type, RenderDetails render_details, String file) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.type = type;
        this.render_details = render_details;
        this.file = file;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RenderDetails getRender_details() {
        return render_details;
    }

    public void setRender_details(RenderDetails render_details) {
        this.render_details = render_details;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "VideoModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", type='" + type + '\'' +
                ", render_details=" + render_details +
                ", file='" + file + '\'' +
                '}';
    }

    public class RenderDetails {
        private String path;

        public RenderDetails() {
        }

        public RenderDetails(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return "RenderDetails{" +
                    "path='" + path + '\'' +
                    '}';
        }
    }
}
