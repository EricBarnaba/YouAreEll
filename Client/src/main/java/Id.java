public class Id {
    private String userid;
    private String name;
    private String github;

    public Id(String userid, String name, String github){
        this.userid=userid;
        this.name=name;
        this.github=github;
    }

    public Id(String name, String github){
        this.userid="-";
        this.name=name;
        this.github=github;
    }

    public String getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public String getGithub() {
        return github;
    }

    @Override
    public String toString(){
        return "User ID: " + this.userid + "Name: " + this.name +  "GitHub: " + this.github;
    }




}
