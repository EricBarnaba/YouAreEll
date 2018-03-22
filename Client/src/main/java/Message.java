public class Message {
    String sequence;
    String timestamp;
    String fromid;
    String toid;
    String message;

    public Message (){}


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

    public String getSequence() {
        return sequence;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getFromid() {
        return fromid;
    }

    public String getToid() {
        return toid;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString(){
        return String.format("TimeStamp: %-30s From: %-30s To: %-30s Message: %s ",this.timestamp, this.fromid, this.toid, this.message);
    }


}
