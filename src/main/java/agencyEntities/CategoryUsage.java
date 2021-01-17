package agencyEntities;

public class CategoryUsage {
    private String category;
    private String usage;
    
    public CategoryUsage(String category, String usage) {
        this.category = category;
        this.usage = usage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
