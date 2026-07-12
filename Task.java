public class Task {
    private int id;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;

    public Task(int id, String description, String status, String createdAt, String updatedAt){
        this.id=id;
        this.description=description;
        this.status=status;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
    }

    public int getId(){ return this.id; }
    public String getDescription(){ return this.description; }
    public String getStatus(){ return this.status; }
    public String getCreatedAt(){ return this.createdAt; }
    public String getUpdatedAt(){ return this.updatedAt; }

    public void setStatus(String status){ this.status=status; }
    public void setDescription(String description){ this.description=description; }
    public void setUpdatedAt(String updatedAt){ this.updatedAt=updatedAt; }
}