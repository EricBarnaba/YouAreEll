public class Id {
    String userid;
    String name;
    String githubid;

    public Id(String userid, String name, String githubid){
        this.userid=userid;
        this.name=name;
        this.githubid=githubid;
    }

    public Id(String name, String githubid){
        this.userid="-";
        this.name=name;
        this.githubid=githubid;
    }




}
