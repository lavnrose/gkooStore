package translator;

public class ConfigGlossary {
    String projectId;
    String glossaryId;
    String sourceLanguage;
    String targetLanguage;
    String inputUrl;
    
    public ConfigGlossary(String proString, String glossaryId, String sourceLanguage, String targetLanguage, String inputUrl) {
        this.projectId = proString;
        this.glossaryId = glossaryId;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.inputUrl = inputUrl;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getGlossaryId() {
        return glossaryId;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public String getInputUrl() {
        return inputUrl;
    }
}
