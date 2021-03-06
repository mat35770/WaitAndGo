package com.waitandgo.model;

/**
 * Created by Mathieu on 30/08/2016.
 */

public class Task {

    private long id;
    private String title;
    private String category;
    private String shareWith;
    private String taskPrerequisite;
    private String description;
    private String updateStatus;
    private long idExternDB;

    /**
     * Builder of an object task
     * @param title Title of the task
     * @param category Category of the task
     * @param shareWith Person to share the task
     * @param taskPrerequisite Task prerequisite
     */
    public Task(String title, String category, String shareWith, String taskPrerequisite,
                String description, String updateStatus){
        this.title=title;
        this.category=category;
        this.shareWith=shareWith;
        this.taskPrerequisite=taskPrerequisite;
        this.description=description;
        this.updateStatus=updateStatus;
        this.idExternDB=0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShareWith() {
        return shareWith;
    }

    public void setShareWith(String shareWith) {
        this.shareWith = shareWith;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskPrerequisite() {
        return taskPrerequisite;
    }

    public void setTaskPrerequisite(String taskPrerequisite) {
        this.taskPrerequisite = taskPrerequisite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdateStatus() { return updateStatus; }
    public void setUpdateStatus(String updateStatus) {this.updateStatus = updateStatus; }

    public long getIdExternDB() {return idExternDB; }
    public void setIdExternDB(long id) {this.idExternDB = id; }

    public String toString(){
        return this.getTitle();
    }

}
