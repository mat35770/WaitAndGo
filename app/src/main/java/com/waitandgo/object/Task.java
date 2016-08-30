package com.waitandgo.object;

/**
 * Created by Mathieu on 30/08/2016.
 */

public class Task {

    private long id;
    private String title;
    private String category;
    private String shareWith;
    private Task taskPrerequisite;

    /**
     * Builder of an object task
     * @param id Object id
     * @param title Title of the task
     * @param category Category of the task
     * @param shareWith Person to share the task
     * @param taskPrerequisite Task prerequisite
     */
    public Task(long id, String title, String category, String shareWith, Task taskPrerequisite){
        this.id=id;
        this.title=title;
        this.category=category;
        this.shareWith=shareWith;
        this.taskPrerequisite=taskPrerequisite;
    }

}
