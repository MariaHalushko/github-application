package com.example.testapp.listRepositories;

public class RepositoryItem {
    private String repositoryTitle;
    private String language;
    private int starsCount;
    private int forkCount;

    public String getRepositoryTitle() {
        return repositoryTitle;
    }

    public String getLanguage() {
        return language;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public int getForkCount() {
        return forkCount;
    }

    public void setRepositoryTitle(String repositoryTitle) {
        this.repositoryTitle = repositoryTitle;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }

    public void setForkCount(int forkCount) {
        this.forkCount = forkCount;
    }

    @Override
    public String toString() {
        return "RepositoryItem{" +
                "repositoryTitle='" + repositoryTitle + '\'' +
                ", language='" + language + '\'' +
                ", starsCount=" + starsCount +
                ", forkCount=" + forkCount +
                '}';
    }
}
