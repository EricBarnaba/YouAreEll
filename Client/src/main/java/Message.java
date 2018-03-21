public class Message {
    String sequence;
    String timestamp;
    String fromid;
    String toid;
    String message;

    public Message(String fromid, String message){
        this.sequence = "-";
        this.timestamp="2018-03-21T01:00:00.0Z";
        this.fromid = fromid;
        this.toid = "";
        this.message = message;
    }

    public Message(String fromid, String toid, String message){
        this.sequence = "-";
        this.timestamp="2018-03-21T01:00:00.0Z";
        this.fromid = fromid;
        this.toid = toid;
        this.message = message;
    }

    public Message(String sequence, String timestamp, String fromid, String toid, String message){
        this.sequence = sequence;
        this.timestamp=timestamp;
        this.fromid = fromid;
        this.toid = toid;
        this.message = message;
    }


}
