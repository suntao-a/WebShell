package com.sulan.webshell.db;

import android.graphics.Bitmap;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.sulan.webshell.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by huangsx on 2016/11/24.
 */
@Table(name = "Pages")
public class FavoritePage extends Model {

    @Column(name = "username", uniqueGroups = {"group"}, onUniqueConflicts = {Column.ConflictAction.FAIL})
    private String username;

    @Column(name = "title")
    private String title;

    @Column(name = "icon")
    private Bitmap icon;

    @Column(name = "url", uniqueGroups = {"group"}, onUniqueConflicts = {Column.ConflictAction.FAIL})
    private String url;

    @Column(name = "create_time")
    private Date createTime;

    public FavoritePage() {
        super();
        username = Data.login.getUserName();

    }

    public String getTitle() {
        return title;
    }

    public FavoritePage setTitle(String title) {
        this.title = title;
        return this;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public FavoritePage setIcon(Bitmap icon) {
        this.icon = icon;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public FavoritePage setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public FavoritePage setUrl(String url) {
        this.url = url;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public FavoritePage setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public static List<FavoritePage> findAll() {
        return new Select().from(FavoritePage.class).where("username=?", Data.login.getUserName()).execute();
    }

    public static Long add(String url, String title, Bitmap icon) {
        FavoritePage page = new FavoritePage()
                .setCreateTime(new Date())
                .setIcon(icon)
                .setTitle(title)
                .setUrl(url)
                .setUsername(Data.login.getUserName());

        return page.save();
    }

    public static List<FavoritePage> delete(String url) {
        return new Delete().from(FavoritePage.class).where("username=? and url=?", Data.login.getUserName(), url).execute();
    }

    public static FavoritePage find(String url) {
        return new Select().from(FavoritePage.class).where("username=? and url=?", Data.login.getUserName(), url).executeSingle();
    }
}
