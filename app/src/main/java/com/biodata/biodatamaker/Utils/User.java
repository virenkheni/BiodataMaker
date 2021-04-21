package com.biodata.biodatamaker.Utils;

public class User {
    public String name;
    public String email;
    public String path;
    public String version;
    public String active;
    public String al;
    public String al_desc;
    public String al_link;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }



    public User() {
    }

    public User(String name, String path) {
        this.name = name;

        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }




    public String lost, match, netrr, points, team, win, sort;

    public String getLost() {
        return lost;
    }

    public void setLost(String lost) {
        this.lost = lost;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getNetrr() {
        return netrr;
    }

    public void setNetrr(String netrr) {
        this.netrr = netrr;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }


    public String getAl() {
        return al;
    }

    public void setAl(String al) {
        this.al = al;
    }

    public String getAl_desc() {
        return al_desc;
    }

    public void setAl_desc(String al_desc) {
        this.al_desc = al_desc;
    }

    public String getAl_link() {
        return al_link;
    }

    public void setAl_link(String al_link) {
        this.al_link = al_link;
    }
}