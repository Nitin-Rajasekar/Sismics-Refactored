package com.sismics.music.core.dao.dbi.criteria;

/**
 * Playlist criteria.
 *
 * @author jtremeaux
 */
public class PlaylistCriteria {
    /**
     * Playlist ID.
     */
    private String id;
    
    /**
     * Returns the default playlist.
     */
    private Boolean defaultPlaylist;
    
    /**
     * Name (like).
     */
    private String nameLike;

    private String user_filter;

    /**
     * User ID.
     */
    private String userId;

    public String getId() {
        return this.id;
    }

    public PlaylistCriteria setId(String id) {
        this.id = id;
        return this;
    }

    public Boolean getDefaultPlaylist() {
        return this.defaultPlaylist;
    }

    public PlaylistCriteria setDefaultPlaylist(Boolean defaultPlaylist) {
        this.defaultPlaylist = defaultPlaylist;
        return this;
    }

    public String getNameLike() {
        return nameLike;
    }

    public PlaylistCriteria setNameLike(String nameLike) {
        this.nameLike = nameLike;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public PlaylistCriteria setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUser_filter() {
        return user_filter;
    }

    public PlaylistCriteria setUser_filter(String user_filter) {
        this.user_filter = user_filter;
        return this;
    }
}
