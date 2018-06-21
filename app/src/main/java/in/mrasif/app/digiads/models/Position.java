package in.mrasif.app.digiads.models;

public class Position {
    private int id;
    private String style;
    private String top;
    private String left;
    private String bottom;
    private String right;
    private String field_contents_path;
    private Field field;

    public Position() {
        this.field=new Field();
    }

    public Position(int id, String style, String top, String left, String bottom, String right, String field_contents_path, Field field) {
        this.id = id;
        this.style = style;
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.field_contents_path = field_contents_path;
        this.field = field;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getField_contents_path() {
        return field_contents_path;
    }

    public void setField_contents_path(String field_contents_path) {
        this.field_contents_path = field_contents_path;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", style='" + style + '\'' +
                ", top='" + top + '\'' +
                ", left='" + left + '\'' +
                ", bottom='" + bottom + '\'' +
                ", right='" + right + '\'' +
                ", field_contents_path='" + field_contents_path + '\'' +
                ", field=" + field +
                '}';
    }

    public class Field{
        private int id;
        private String name;
        private Config config;

        public Field() {
            this.config=new Config();
        }

        public Field(int id, String name, Config config) {
            this.id = id;
            this.name = name;
            this.config = config;
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

        public Config getConfig() {
            return config;
        }

        public void setConfig(Config config) {
            this.config = config;
        }

        @Override
        public String toString() {
            return "Field{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", config=" + config +
                    '}';
        }
    }

    public class Config{
        private boolean screens_clear_last_content;
        private String entry_transition;
        private String exit_transition;

        public Config() {
        }

        public Config(boolean screens_clear_last_content, String entry_transition, String exit_transition) {
            this.screens_clear_last_content = screens_clear_last_content;
            this.entry_transition = entry_transition;
            this.exit_transition = exit_transition;
        }

        public boolean isScreens_clear_last_content() {
            return screens_clear_last_content;
        }

        public void setScreens_clear_last_content(boolean screens_clear_last_content) {
            this.screens_clear_last_content = screens_clear_last_content;
        }

        public String getEntry_transition() {
            return entry_transition;
        }

        public void setEntry_transition(String entry_transition) {
            this.entry_transition = entry_transition;
        }

        public String getExit_transition() {
            return exit_transition;
        }

        public void setExit_transition(String exit_transition) {
            this.exit_transition = exit_transition;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "screens_clear_last_content=" + screens_clear_last_content +
                    ", entry_transition='" + entry_transition + '\'' +
                    ", exit_transition='" + exit_transition + '\'' +
                    '}';
        }
    }
}
