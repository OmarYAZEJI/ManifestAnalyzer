package omaryazeji.app.manifestanalyzer.Model;

public class Application {
    public String Name;
    public String Dir;

    public Application(String name,String dir) {
        Name = name;
        Dir = dir;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return Name;
    }
}


