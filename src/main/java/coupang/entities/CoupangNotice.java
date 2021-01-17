package coupang.entities;

public class CoupangNotice {
    private String noticeCategoryName;
    private String noticeCategoryDetailName;
    private String content;
    
    public CoupangNotice(String noticeCategoryName, String noticeCategoryDetailName, String content) {
        this.noticeCategoryName = noticeCategoryName;
        this.noticeCategoryDetailName = noticeCategoryDetailName;
        this.content = content;
    }
    
    public String getNoticeCategoryName() {
        return noticeCategoryName;
    }
    
    public void setNoticeCategoryName(String noticeCategoryName) {
        this.noticeCategoryName = noticeCategoryName;
    }
    
    public String getNoticeCategoryDetailName() {
        return noticeCategoryDetailName;
    }
    
    public void setNoticeCategoryDetailName(String noticeCategoryDetailName) {
        this.noticeCategoryDetailName = noticeCategoryDetailName;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
}
